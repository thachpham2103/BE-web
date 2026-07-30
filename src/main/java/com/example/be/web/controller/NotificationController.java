package com.example.be.web.controller;

import com.example.be.web.base.RestData;
import com.example.be.web.base.VsResponseUtil;
import com.example.be.web.doman.dto.request.notification.NotificationTemplateRequestDto;
import com.example.be.web.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "API quản lý thông báo")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy danh sách thông báo của tôi")
    public ResponseEntity<RestData<?>> getMyNotifications(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        return VsResponseUtil.success(notificationService.getMyNotifications(userDetails.getUsername(), pageable));
    }

    @GetMapping("/unread-count")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Đếm thông báo chưa đọc")
    public ResponseEntity<RestData<?>> getUnreadCount(
            @AuthenticationPrincipal UserDetails userDetails) {
        return VsResponseUtil.success(notificationService.getUnreadCount(userDetails.getUsername()));
    }

    @PutMapping("/{notifId}/read")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Đánh dấu thông báo đã đọc")
    public ResponseEntity<RestData<?>> markAsRead(@PathVariable Long notifId) {
        notificationService.markAsRead(notifId);
        return VsResponseUtil.success("Đã đánh dấu đọc");
    }

    @DeleteMapping("/{notifId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Xóa thông báo cá nhân")
    public ResponseEntity<RestData<?>> deleteNotification(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long notifId) {
        notificationService.deleteNotification(notifId, userDetails.getUsername());
        return VsResponseUtil.success("Đã xóa thông báo");
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Tìm kiếm thông báo của tôi")
    public ResponseEntity<RestData<?>> searchMyNotifications(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        return VsResponseUtil.success(notificationService.searchMyNotifications(userDetails.getUsername(), keyword, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')") // Or based on roles that can send. Usually TEACHER or ADMIN. Since LEADER might be teacher.
    @Operation(summary = "Gửi thông báo")
    public ResponseEntity<RestData<?>> sendNotification(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody com.example.be.web.doman.dto.request.notification.NotificationSendRequestDto dto) {
        notificationService.sendNotification(dto, userDetails.getUsername());
        return VsResponseUtil.success(HttpStatus.CREATED, "Đã gửi thông báo");
    }

    @GetMapping("/sent")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Lấy danh sách thông báo đã gửi")
    public ResponseEntity<RestData<?>> getSentNotifications(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return VsResponseUtil.success(notificationService.getSentNotifications(userDetails.getUsername(), pageable));
    }

    @PutMapping("/sent/{notifId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Cập nhật thông báo đã gửi")
    public ResponseEntity<RestData<?>> updateSentNotification(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long notifId,
            @Valid @RequestBody com.example.be.web.doman.dto.request.notification.NotificationSendRequestDto dto) {
        notificationService.updateSentNotification(notifId, dto, userDetails.getUsername());
        return VsResponseUtil.success("Đã cập nhật thông báo");
    }

    @DeleteMapping("/sent/{notifId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Xóa thông báo đã gửi")
    public ResponseEntity<RestData<?>> deleteSentNotification(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long notifId) {
        notificationService.deleteSentNotification(notifId, userDetails.getUsername());
        return VsResponseUtil.success("Đã xóa thông báo");
    }

    @PostMapping("/templates")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Tạo template thông báo")
    public ResponseEntity<RestData<?>> createTemplate(
            @Valid @RequestBody NotificationTemplateRequestDto dto) {
        return VsResponseUtil.success(HttpStatus.CREATED, notificationService.createTemplate(dto));
    }

    @GetMapping("/templates")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Lấy danh sách template thông báo")
    public ResponseEntity<RestData<?>> getTemplates() {
        return VsResponseUtil.success(notificationService.getTemplates());
    }

    @PutMapping("/templates/{templateId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Cập nhật template thông báo")
    public ResponseEntity<RestData<?>> updateTemplate(
            @PathVariable Long templateId,
            @Valid @RequestBody NotificationTemplateRequestDto dto) {
        return VsResponseUtil.success(notificationService.updateTemplate(templateId, dto));
    }

    @DeleteMapping("/templates/{templateId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Vô hiệu hóa template")
    public ResponseEntity<RestData<?>> deactivateTemplate(@PathVariable Long templateId) {
        notificationService.deactivateTemplate(templateId);
        return VsResponseUtil.success("Đã vô hiệu hóa template");
    }
}
