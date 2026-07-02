package com.example.be.web.service;

import com.example.be.web.doman.dto.request.notification.NotificationTemplateRequestDto;
import com.example.be.web.doman.dto.response.notification.NotificationResponseDto;
import com.example.be.web.doman.dto.response.notification.NotificationTemplateResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    Page<NotificationResponseDto> getMyNotifications(String username, Pageable pageable);
    long getUnreadCount(String username);
    void markAsRead(Long notifId);

    NotificationTemplateResponseDto createTemplate(NotificationTemplateRequestDto dto);
    List<NotificationTemplateResponseDto> getTemplates();
    NotificationTemplateResponseDto updateTemplate(Long templateId, NotificationTemplateRequestDto dto);
    void deactivateTemplate(Long templateId);
}
