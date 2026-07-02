package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho trạng thái đọc tin nhắn ({@code MessageReadStatus}).
 *
 * <p>Ràng buộc UNIQUE trên (message_id, user_id).</p>
 *
 * @author auto-generated
 * @see Message
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message_read_status",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_READ_STATUS_MESSAGE_USER",
                columnNames = {"message_id", "user_id"}
        ))
public class MessageReadStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "read_status_id", nullable = false, updatable = false)
    private Long readStatusId;

    /** Tin nhắn. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_READ_STATUS_MESSAGE"))
    private Message message;

    /** Người đọc. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_READ_STATUS_USER"))
    private User user;

    /** Thời điểm đọc. */
    @Column(name = "read_at", nullable = false)
    private LocalDateTime readAt;
}
