package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.chat.ConversationRequestDto;
import com.example.be.web.doman.dto.request.chat.MessageRequestDto;
import com.example.be.web.doman.dto.response.chat.ConversationResponseDto;
import com.example.be.web.doman.dto.response.chat.MessageResponseDto;
import com.example.be.web.doman.entity.*;
import com.example.be.web.doman.mapper.ConversationMapper;
import com.example.be.web.doman.mapper.ConversationMemberMapper;
import com.example.be.web.doman.mapper.MessageMapper;
import com.example.be.web.doman.model.ConversationMemberRole;
import com.example.be.web.doman.model.MessageType;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.*;
import com.example.be.web.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;
    private final ConversationMemberRepository conversationMemberRepository;
    private final MessageRepository messageRepository;
    private final MessageReadStatusRepository messageReadStatusRepository;
    private final PinnedMessageRepository pinnedMessageRepository;
    private final ConversationJoinRequestRepository conversationJoinRequestRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationRecipientRepository notificationRecipientRepository;
    private final UserRepository userRepository;
    private final ConversationMapper conversationMapper;
    private final ConversationMemberMapper conversationMemberMapper;
    private final MessageMapper messageMapper;
    private final ClassRepository classRepository;

    @Override
    public ConversationResponseDto createConversation(ConversationRequestDto dto, String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        Conversation convo = Conversation.builder()
                .name(dto.getName())
                .conversationType(dto.getConversationType())
                .createdBy(creator)
                .build();

        if (dto.getClassId() != null) {
            convo.setClassRoom(ClassRoom.builder().classId(dto.getClassId()).build());
        }

        conversationRepository.save(convo);

        // Thêm creator làm ADMIN
        ConversationMember creatorMember = ConversationMember.builder()
                .id(new ConversationMember.ConversationMemberId(convo.getConvoId(), creator.getId()))
                .conversation(convo)
                .user(creator)
                .role(ConversationMemberRole.ADMIN)
                .build();
        conversationMemberRepository.save(creatorMember);

        // Thêm các thành viên khác
        if (dto.getMemberIds() != null) {
            for (Long memberId : dto.getMemberIds()) {
                if (!memberId.equals(creator.getId())) {
                    User member = userRepository.findById(memberId)
                            .orElseThrow(() -> new NotFoundException("Không tìm thấy user ID: " + memberId));
                    ConversationMember cm = ConversationMember.builder()
                            .id(new ConversationMember.ConversationMemberId(convo.getConvoId(), memberId))
                            .conversation(convo)
                            .user(member)
                            .role(ConversationMemberRole.MEMBER)
                            .build();
                    conversationMemberRepository.save(cm);
                }
            }
        }
        return conversationMapper.toResponse(convo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConversationResponseDto> getMyConversations(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        List<Conversation> convos;
        if (user.getRole() != null && com.example.be.web.constant.RoleConstant.ADMIN.equals(user.getRole().getName())) {
            convos = conversationRepository.findAll();
        } else {
            convos = conversationRepository.findByMemberUserId(user.getId());
        }

        return convos.stream()
                .map(c -> {
                    ConversationResponseDto dto = conversationMapper.toResponse(c);
                    var members = conversationMemberRepository
                            .findByConversation_ConvoIdAndLeftAtIsNull(c.getConvoId());
                    dto.setMemberCount(members.size());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ConversationResponseDto getConversation(Long convoId) {
        Conversation convo = conversationRepository.findById(convoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Conversation.CONVERSATION_NOT_FOUND));

        ConversationResponseDto dto = conversationMapper.toResponse(convo);
        var members = conversationMemberRepository
                .findByConversation_ConvoIdAndLeftAtIsNull(convo.getConvoId());
        dto.setMemberCount(members.size());
        dto.setMembers(members.stream()
                .map(conversationMemberMapper::toResponse)
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public MessageResponseDto sendMessage(MessageRequestDto dto, String username) {
        User sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        Conversation convo = conversationRepository.findById(dto.getConvoId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Conversation.CONVERSATION_NOT_FOUND));

        Message msg = Message.builder()
                .conversation(convo)
                .sender(sender)
                .content(dto.getContent())
                .messageType(dto.getMessageType() != null ? dto.getMessageType() : MessageType.TEXT)
                .sentAt(LocalDateTime.now())
                .deleted(false)
                .build();

        messageRepository.save(msg);

        // Cập nhật lastMessageAt cho Conversation
        convo.setLastMessageAt(LocalDateTime.now());
        conversationRepository.save(convo);

        // Đánh dấu đã đọc cho chính người gửi
        if (!messageReadStatusRepository.existsByMessage_MessageIdAndUser_Id(msg.getMessageId(), sender.getId())) {
            MessageReadStatus readStatus = MessageReadStatus.builder()
                    .message(msg)
                    .user(sender)
                    .readAt(LocalDateTime.now())
                    .build();
            messageReadStatusRepository.save(readStatus);
        }

        return messageMapper.toResponse(msg);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageResponseDto> getMessages(Long convoId, Pageable pageable) {
        if (!conversationRepository.existsById(convoId)) {
            throw new NotFoundException(ErrorMessage.Conversation.CONVERSATION_NOT_FOUND);
        }
        return messageRepository.findByConversation_ConvoIdAndDeletedFalse(convoId, pageable)
                .map(messageMapper::toResponse);
    }

    @Override
    public void deleteMessage(Long messageId) {
        Message msg = messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Message.MESSAGE_NOT_FOUND));
        msg.setDeleted(true);
        messageRepository.save(msg);
    }

    @Override
    public void markAsRead(Long messageId, String username) {
        Message msg = messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Message.MESSAGE_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        if (!messageReadStatusRepository.existsByMessage_MessageIdAndUser_Id(messageId, user.getId())) {
            MessageReadStatus readStatus = MessageReadStatus.builder()
                    .message(msg)
                    .user(user)
                    .readAt(LocalDateTime.now())
                    .build();
            messageReadStatusRepository.save(readStatus);
        }
    }

    @Override
    public void togglePin(Long convoId, Long messageId, String username) {
        Conversation convo = conversationRepository.findById(convoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Conversation.CONVERSATION_NOT_FOUND));

        Message msg = messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Message.MESSAGE_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        var existing = pinnedMessageRepository.findByConversation_ConvoIdAndMessage_MessageId(convoId, messageId);
        if (existing.isPresent()) {
            pinnedMessageRepository.delete(existing.get());
        } else {
            PinnedMessage pin = PinnedMessage.builder()
                    .conversation(convo)
                    .message(msg)
                    .pinnedBy(user)
                    .build();
            pinnedMessageRepository.save(pin);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageResponseDto> getPinnedMessages(Long convoId) {
        if (!conversationRepository.existsById(convoId)) {
            throw new NotFoundException(ErrorMessage.Conversation.CONVERSATION_NOT_FOUND);
        }
        return pinnedMessageRepository.findByConversation_ConvoId(convoId)
                .stream()
                .map(pin -> messageMapper.toResponse(pin.getMessage()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ConversationResponseDto> getPublicGroups(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));
        
        // Auto-sync missing ClassRooms to Conversations
        try {
            List<ClassRoom> allClasses = classRepository.findAll();
            List<Conversation> allConvos = conversationRepository.findAll();
            for (ClassRoom cr : allClasses) {
                boolean exists = allConvos.stream().anyMatch(c -> c.getClassRoom() != null && c.getClassRoom().getClassId().equals(cr.getClassId()));
                if (!exists) {
                    Conversation convo = Conversation.builder()
                            .name(cr.getTitle() != null ? cr.getTitle() : "Lớp #" + cr.getClassId())
                            .conversationType(com.example.be.web.doman.model.ConversationType.CLASS)
                            .classRoom(cr)
                            .createdBy(cr.getTeacher() != null ? cr.getTeacher() : user)
                            .build();
                    conversationRepository.save(convo);
                    User adminUser = cr.getTeacher() != null ? cr.getTeacher() : user;
                    ConversationMember creatorMember = ConversationMember.builder()
                            .id(new ConversationMember.ConversationMemberId(convo.getConvoId(), adminUser.getId()))
                            .conversation(convo)
                            .user(adminUser)
                            .role(ConversationMemberRole.ADMIN)
                            .build();
                    conversationMemberRepository.save(creatorMember);
                }
            }
        } catch (Exception e) {
            // ignore sync errors
        }

        List<Long> myConvoIds = conversationRepository.findByMemberUserId(user.getId())
                .stream().map(Conversation::getConvoId).collect(Collectors.toList());

        return conversationRepository.findAll().stream()
                .filter(c -> c.getConversationType() != com.example.be.web.doman.model.ConversationType.PRIVATE)
                .filter(c -> !myConvoIds.contains(c.getConvoId()))
                .map(c -> {
                    ConversationResponseDto dto = conversationMapper.toResponse(c);
                    var members = conversationMemberRepository
                            .findByConversation_ConvoIdAndLeftAtIsNull(c.getConvoId());
                    dto.setMemberCount(members.size());
                    boolean requested = conversationJoinRequestRepository.findByConversation_ConvoIdAndUser_IdAndStatus(
                            c.getConvoId(), user.getId(), com.example.be.web.doman.model.JoinRequestStatus.PENDING).isPresent();
                    dto.setJoinRequested(requested);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void requestJoinGroup(Long convoId, String username) {
        Conversation convo = conversationRepository.findById(convoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Conversation.CONVERSATION_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        // Check if already requested
        if (conversationJoinRequestRepository.findByConversation_ConvoIdAndUser_IdAndStatus(
                convoId, user.getId(), com.example.be.web.doman.model.JoinRequestStatus.PENDING).isPresent()) {
            return;
        }

        ConversationJoinRequest request = ConversationJoinRequest.builder()
                .conversation(convo)
                .user(user)
                .status(com.example.be.web.doman.model.JoinRequestStatus.PENDING)
                .build();
        conversationJoinRequestRepository.save(request);

        // Send notification to the creator of the conversation
        if (convo.getCreatedBy() != null) {
            String senderName = user.getFullName() != null ? user.getFullName() : user.getUsername();
            Notification notification = Notification.builder()
                    .title("Yêu cầu tham gia nhóm")
                    .body("Sinh viên " + senderName + " muốn tham gia nhóm " + convo.getName())
                    .type(com.example.be.web.doman.model.NotificationType.SYSTEM)
                    .user(convo.getCreatedBy())
                    .createdBy(user)
                    .build();
            notification = notificationRepository.save(notification);

            com.example.be.web.doman.entity.NotificationRecipient recipient = com.example.be.web.doman.entity.NotificationRecipient.builder()
                    .notification(notification)
                    .user(convo.getCreatedBy())
                    .build();
            notificationRecipientRepository.save(recipient);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<com.example.be.web.doman.dto.response.chat.ConversationJoinRequestResponseDto> getPendingJoinRequests(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));
        String roleName = user.getRole() != null && user.getRole().getName() != null ? user.getRole().getName().toUpperCase() : "";
        if (roleName.contains("ADMIN") || roleName.contains("LEADER") || roleName.contains("TEACHER") || roleName.contains("GIANG") || roleName.contains("GIAO")) {
            return conversationJoinRequestRepository.findByStatus(com.example.be.web.doman.model.JoinRequestStatus.PENDING)
                    .stream()
                    .map(com.example.be.web.doman.dto.response.chat.ConversationJoinRequestResponseDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            return conversationJoinRequestRepository.findByStatus(com.example.be.web.doman.model.JoinRequestStatus.PENDING)
                    .stream()
                    .filter(req -> req.getConversation().getCreatedBy() != null && req.getConversation().getCreatedBy().getId().equals(user.getId()))
                    .map(com.example.be.web.doman.dto.response.chat.ConversationJoinRequestResponseDto::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void approveJoinRequest(Long requestId, String username) {
        ConversationJoinRequest request = conversationJoinRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.REQUEST_NOT_FOUND));
        
        request.setStatus(com.example.be.web.doman.model.JoinRequestStatus.APPROVED);
        conversationJoinRequestRepository.save(request);

        // Add member
        ConversationMember member = ConversationMember.builder()
                .id(new ConversationMember.ConversationMemberId(request.getConversation().getConvoId(), request.getUser().getId()))
                .conversation(request.getConversation())
                .user(request.getUser())
                .role(com.example.be.web.doman.model.ConversationMemberRole.MEMBER)
                .build();
        conversationMemberRepository.save(member);
    }

    @Override
    public void rejectJoinRequest(Long requestId, String username) {
        ConversationJoinRequest request = conversationJoinRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.REQUEST_NOT_FOUND));
        
        request.setStatus(com.example.be.web.doman.model.JoinRequestStatus.REJECTED);
        conversationJoinRequestRepository.save(request);
    }
}
