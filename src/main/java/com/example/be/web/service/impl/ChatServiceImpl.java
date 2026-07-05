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

        return conversationRepository.findByMemberUserId(user.getId())
                .stream().map(c -> {
                    ConversationResponseDto dto = conversationMapper.toResponse(c);
                    var members = conversationMemberRepository
                            .findByConversation_ConvoIdAndLeftAtIsNull(c.getConvoId());
                    dto.setMemberCount(members.size());
                    dto.setMembers(members.stream()
                            .map(conversationMemberMapper::toResponse)
                            .collect(Collectors.toList()));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ConversationResponseDto getConversation(Long convoId) {
        Conversation convo = conversationRepository.findById(convoId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Conversation.CONVERSATION_NOT_FOUND));
        ConversationResponseDto dto = conversationMapper.toResponse(convo);
        var members = conversationMemberRepository
                .findByConversation_ConvoIdAndLeftAtIsNull(convoId);
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

        Message message = Message.builder()
                .conversation(convo)
                .sender(sender)
                .content(dto.getContent())
                .messageType(dto.getMessageType() != null ? dto.getMessageType() : MessageType.TEXT)
                .build();
        messageRepository.save(message);

        convo.setLastMessageAt(LocalDateTime.now());
        conversationRepository.save(convo);

        return messageMapper.toResponse(message);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageResponseDto> getMessages(Long convoId, Pageable pageable) {
        return messageRepository.findByConversation_ConvoIdAndDeletedFalse(convoId, pageable)
                .map(messageMapper::toResponse);
    }

    @Override
    public void deleteMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Message.MESSAGE_NOT_FOUND));
        message.setDeleted(true);
        messageRepository.save(message);
    }

    @Override
    public void markAsRead(Long messageId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        if (!messageReadStatusRepository.existsByMessage_MessageIdAndUser_Id(messageId, user.getId())) {
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Message.MESSAGE_NOT_FOUND));

            MessageReadStatus readStatus = MessageReadStatus.builder()
                    .message(message)
                    .user(user)
                    .readAt(LocalDateTime.now())
                    .build();
            messageReadStatusRepository.save(readStatus);
        }
    }

    @Override
    public void togglePin(Long convoId, Long messageId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        var existing = pinnedMessageRepository
                .findByConversation_ConvoIdAndMessage_MessageId(convoId, messageId);
        if (existing.isPresent()) {
            pinnedMessageRepository.delete(existing.get());
        } else {
            Conversation convo = conversationRepository.findById(convoId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Conversation.CONVERSATION_NOT_FOUND));
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Message.MESSAGE_NOT_FOUND));

            PinnedMessage pin = PinnedMessage.builder()
                    .conversation(convo)
                    .message(message)
                    .pinnedBy(user)
                    .build();
            pinnedMessageRepository.save(pin);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageResponseDto> getPinnedMessages(Long convoId) {
        return pinnedMessageRepository.findByConversation_ConvoId(convoId)
                .stream().map(p -> messageMapper.toResponse(p.getMessage()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConversationResponseDto> getPublicGroups(String username) {
        // Assume ConversationType.GROUP is considered public
        return conversationRepository.findAll().stream()
                .filter(c -> c.getConversationType() == com.example.be.web.doman.model.ConversationType.GROUP)
                .map(conversationMapper::toResponse)
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
            Notification notification = Notification.builder()
                    .title("Yêu cầu tham gia nhóm")
                    .body("Sinh viên " + user.getFullName() + " muốn tham gia nhóm " + convo.getName())
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
        // Teacher sees all pending requests
        return conversationJoinRequestRepository.findByStatus(com.example.be.web.doman.model.JoinRequestStatus.PENDING)
                .stream()
                .map(com.example.be.web.doman.dto.response.chat.ConversationJoinRequestResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void approveJoinRequest(Long requestId, String username) {
        ConversationJoinRequest request = conversationJoinRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request not found"));
        
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
                .orElseThrow(() -> new NotFoundException("Request not found"));
        
        request.setStatus(com.example.be.web.doman.model.JoinRequestStatus.REJECTED);
        conversationJoinRequestRepository.save(request);
    }
}
