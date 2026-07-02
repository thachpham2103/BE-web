package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import com.example.be.web.doman.model.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entity đại diện cho template thông báo chuẩn ({@code NotificationTemplate}).
 *
 * <p>Dùng để sinh thông báo hàng loạt với format chuẩn.
 * Ví dụ: titleTemplate = "Bài tập mới: {title}".</p>
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "notification_templates",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_NOTIF_TEMPLATE_NAME",
                columnNames = {"name"}
        ))
public class NotificationTemplate extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id", nullable = false, updatable = false)
    private Long templateId;

    /** Tên template (duy nhất). */
    @Column(name = "name", length = 200, nullable = false, unique = true)
    private String name;

    /** Template tiêu đề. */
    @Column(name = "title_template", length = 300, nullable = false)
    private String titleTemplate;

    /** Template nội dung. */
    @Column(name = "body_template", columnDefinition = "TEXT", nullable = false)
    private String bodyTemplate;

    /** Loại thông báo. */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 30)
    private NotificationType type;

    /** Đang hoạt động. */
    @Column(name = "active", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
