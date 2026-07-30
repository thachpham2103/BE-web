package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.notification.NotificationTemplateRequestDto;
import com.example.be.web.doman.dto.response.notification.NotificationResponseDto;
import com.example.be.web.doman.dto.response.notification.NotificationTemplateResponseDto;
import com.example.be.web.doman.entity.Notification;
import com.example.be.web.doman.entity.NotificationTemplate;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.mapper.NotificationMapper;
import com.example.be.web.doman.mapper.NotificationTemplateMapper;
import com.example.be.web.exception.extended.BadRequestException;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.NotificationRecipientRepository;
import com.example.be.web.repository.NotificationRepository;
import com.example.be.web.repository.NotificationTemplateRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.repository.ClassRegistrationRepository;
import com.example.be.web.service.NotificationService;
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
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationRecipientRepository notificationRecipientRepository;
    private final NotificationTemplateRepository notificationTemplateRepository;
    private final UserRepository userRepository;
    private final ClassRegistrationRepository classRegistrationRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationTemplateMapper notificationTemplateMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponseDto> getMyNotifications(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));
        return notificationRepository.findByUser_Id(user.getId(), pageable)
                .map(notificationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));
        return notificationRepository.countByUser_IdAndIsReadFalse(user.getId());
    }

    @Override
    public void markAsRead(Long notifId) {
        Notification notif = notificationRepository.findById(notifId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Notification.NOTIFICATION_NOT_FOUND));
        notif.setRead(true);
        notificationRepository.save(notif);

        notificationRecipientRepository.findByNotification_NotifIdAndUser_Id(notifId, notif.getUser().getId())
                .ifPresent(r -> {
                    r.setIsRead(true);
                    r.setReadAt(LocalDateTime.now());
                    notificationRecipientRepository.save(r);
                });
    }

    @Override
    public void deleteNotification(Long notifId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        Notification notif = notificationRepository.findById(notifId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Notification.NOTIFICATION_NOT_FOUND));

        if (!notif.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Bạn không có quyền xóa thông báo này");
        }

        notificationRepository.delete(notif);
        notificationRecipientRepository.findByNotification_NotifIdAndUser_Id(notifId, user.getId())
                .ifPresent(notificationRecipientRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponseDto> searchMyNotifications(String username, String keyword, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        org.springframework.data.jpa.domain.Specification<Notification> spec = org.springframework.data.jpa.domain.Specification.where(
                (root, query, cb) -> cb.equal(root.get("user").get("id"), user.getId())
        );

        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = "%" + keyword.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("title")), kw),
                    cb.like(cb.lower(root.get("body")), kw)
            ));
        }

        return notificationRepository.findAll(spec, pageable).map(notificationMapper::toResponse);
    }

    @Override
    public NotificationTemplateResponseDto createTemplate(NotificationTemplateRequestDto dto) {
        NotificationTemplate template = NotificationTemplate.builder()
                .name(dto.getName())
                .titleTemplate(dto.getTitleTemplate())
                .bodyTemplate(dto.getBodyTemplate())
                .type(dto.getType())
                .build();
        notificationTemplateRepository.save(template);
        return notificationTemplateMapper.toResponse(template);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationTemplateResponseDto> getTemplates() {
        return notificationTemplateRepository.findByActiveTrue()
                .stream().map(notificationTemplateMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationTemplateResponseDto updateTemplate(Long templateId, NotificationTemplateRequestDto dto) {
        NotificationTemplate template = notificationTemplateRepository.findById(templateId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NotificationTemplate.TEMPLATE_NOT_FOUND));
        template.setName(dto.getName());
        template.setTitleTemplate(dto.getTitleTemplate());
        template.setBodyTemplate(dto.getBodyTemplate());
        template.setType(dto.getType());
        notificationTemplateRepository.save(template);
        return notificationTemplateMapper.toResponse(template);
    }

    @Override
    public void deactivateTemplate(Long templateId) {
        NotificationTemplate template = notificationTemplateRepository.findById(templateId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NotificationTemplate.TEMPLATE_NOT_FOUND));
        template.setActive(false);
        notificationTemplateRepository.save(template);
    }

    @Override
    public void sendNotification(com.example.be.web.doman.dto.request.notification.NotificationSendRequestDto dto, String senderUsername) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));

        java.util.List<User> targets = new java.util.ArrayList<>();
        if (dto.getStudentId() != null && !dto.getStudentId().isEmpty()) {
            User student = userRepository.findByUsername(dto.getStudentId())
                    .orElseThrow(() -> new NotFoundException("Student not found"));
            targets.add(student);
        } else if (dto.getClassId() != null) {
            Page<com.example.be.web.doman.entity.ClassRegistration> registrations = 
                    classRegistrationRepository.findByClassEntity_ClassIdAndStatus(
                            Pageable.unpaged(), dto.getClassId(), com.example.be.web.doman.model.RegistrationStatus.ACCEPTED);
            for (com.example.be.web.doman.entity.ClassRegistration reg : registrations.getContent()) {
                targets.add(reg.getStudent());
            }
        } else {
            // Broadcast to all users
            targets.addAll(userRepository.findByRole_Name("ROLE_USER"));
        }

        if (targets.isEmpty()) {
            throw new BadRequestException("No recipients found for the notification");
        }

        for (User target : targets) {
            Notification notification = Notification.builder()
                    .title(dto.getTitle())
                    .body(dto.getContent())
                    .type(dto.getType())
                    .user(target)
                    .createdBy(sender)
                    .build();
            
            notification = notificationRepository.save(notification);

            com.example.be.web.doman.entity.NotificationRecipient recipient = com.example.be.web.doman.entity.NotificationRecipient.builder()
                    .notification(notification)
                    .user(target)
                    .build();
            notificationRecipientRepository.save(recipient);
        }
    }

    @Override
    public Page<NotificationResponseDto> getSentNotifications(String username, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findDistinctSentNotifications(username, pageable);
        return notifications.map(notificationMapper::toResponse);
    }

    @Override
    @Transactional
    public void updateSentNotification(Long notifId, com.example.be.web.doman.dto.request.notification.NotificationSendRequestDto dto, String username) {
        Notification existing = notificationRepository.findById(notifId)
                .orElseThrow(() -> new NotFoundException("Notification not found"));
        
        if (!existing.getCreatedBy().getUsername().equals(username)) {
            throw new BadRequestException("You can only edit your own sent notifications");
        }
        
        notificationRepository.updateSentNotifications(existing.getTitle(), existing.getBody(), dto.getTitle(), dto.getContent(), username);
    }

    @Override
    @Transactional
    public void deleteSentNotification(Long notifId, String username) {
        Notification existing = notificationRepository.findById(notifId)
                .orElseThrow(() -> new NotFoundException("Notification not found"));
                
        if (!existing.getCreatedBy().getUsername().equals(username)) {
            throw new BadRequestException("You can only delete your own sent notifications");
        }
        
        notificationRepository.deleteSentNotifications(existing.getTitle(), existing.getBody(), username);
    }
}
