package com.example.be.web.service.impl;

import com.example.be.web.doman.dto.request.notification.NotificationTemplateRequestDto;
import com.example.be.web.doman.dto.response.notification.NotificationResponseDto;
import com.example.be.web.doman.dto.response.notification.NotificationTemplateResponseDto;
import com.example.be.web.doman.entity.Notification;
import com.example.be.web.doman.entity.NotificationTemplate;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.mapper.NotificationMapper;
import com.example.be.web.doman.mapper.NotificationTemplateMapper;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.NotificationRecipientRepository;
import com.example.be.web.repository.NotificationRepository;
import com.example.be.web.repository.NotificationTemplateRepository;
import com.example.be.web.repository.UserRepository;
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
    private final NotificationMapper notificationMapper;
    private final NotificationTemplateMapper notificationTemplateMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponseDto> getMyNotifications(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
        return notificationRepository.findByUser_Id(user.getId(), pageable)
                .map(notificationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
        return notificationRepository.countByUser_IdAndIsReadFalse(user.getId());
    }

    @Override
    public void markAsRead(Long notifId) {
        Notification notif = notificationRepository.findById(notifId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông báo"));
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
                .orElseThrow(() -> new NotFoundException("Không tìm thấy template"));
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
                .orElseThrow(() -> new NotFoundException("Không tìm thấy template"));
        template.setActive(false);
        notificationTemplateRepository.save(template);
    }
}
