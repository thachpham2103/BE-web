package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho người nhận thông báo ({@code NotificationRecipient}).
 *
 * <p>Ràng buộc UNIQUE trên (notif_id, user_id).
 * Thay thế kiểu notification có user_id trực tiếp.</p>
 *
 * @author auto-generated
 * @see Notification
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notification_recipients",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_NOTIF_RECIPIENT",
                columnNames = {"notif_id", "user_id"}
        ))
public class NotificationRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipient_id", nullable = false, updatable = false)
    private Long recipientId;

    /** Thông báo. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notif_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RECIPIENT_NOTIFICATION"))
    private Notification notification;

    /** Người nhận. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RECIPIENT_USER"))
    private User user;

    /** Đã đọc. */
    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private Boolean isRead = false;

    /** Thời điểm đọc. */
    @Column(name = "read_at")
    private LocalDateTime readAt;
}
