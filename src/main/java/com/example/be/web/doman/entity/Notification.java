package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.NotificationType;
import com.example.be.web.doman.model.TargetType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho thông báo ({@code Notification}).
 *
 * <p>Hỗ trợ template, phân loại type, liên kết logic qua targetType/targetId.
 * Người nhận được quản lý qua {@link NotificationRecipient}.</p>
 *
 * @author auto-generated
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notif_id")
    private Long notifId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    /** Giữ lại backward compatible – sẽ migrate dần sang NotificationRecipient. */
    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private boolean isRead = false;

    @Column(name = "create_at", nullable = false)
    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now();

    /** Người nhận trực tiếp (backward compatible). */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Template thông báo chuẩn (nullable). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id",
            foreignKey = @ForeignKey(name = "FK_NOTIF_TEMPLATE"))
    private NotificationTemplate template;

    /** Loại thông báo. */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 30)
    private NotificationType type;

    /** Loại đối tượng liên kết (liên kết logic, không FK cứng). */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", length = 30)
    private TargetType targetType;

    /** ID đối tượng liên kết (liên kết logic). */
    @Column(name = "target_id")
    private Long targetId;

    /** Người tạo thông báo. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by",
            foreignKey = @ForeignKey(name = "FK_NOTIF_CREATOR"))
    private User createdBy;

    /** Danh sách người nhận. */
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<NotificationRecipient> recipients = new ArrayList<>();
}
