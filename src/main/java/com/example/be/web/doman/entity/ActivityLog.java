package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.ActivityAction;
import com.example.be.web.doman.model.TargetType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho nhật ký hoạt động ({@code ActivityLog}).
 *
 * <p>targetType/targetId là liên kết logic, không FK cứng.
 * Dùng để ghi lại mọi hoạt động quan trọng trong hệ thống.</p>
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "activity_logs")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false, updatable = false)
    private Long logId;

    /** Người thực hiện hành động. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_LOG_ACTOR"))
    private User actor;

    /** Loại hành động. */
    @Enumerated(EnumType.STRING)
    @Column(name = "action", length = 20, nullable = false)
    private ActivityAction action;

    /** Loại đối tượng liên kết (liên kết logic). */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", length = 30)
    private TargetType targetType;

    /** ID đối tượng liên kết (liên kết logic). */
    @Column(name = "target_id")
    private Long targetId;

    /** Mô tả chi tiết. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Địa chỉ IP. */
    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    /** Thời điểm ghi log. */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
