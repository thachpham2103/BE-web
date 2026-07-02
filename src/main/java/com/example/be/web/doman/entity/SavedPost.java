package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho bài viết đã lưu ({@code SavedPost}).
 *
 * <p>Ràng buộc UNIQUE trên (user_id, post_id) — mỗi user chỉ lưu bài 1 lần.</p>
 *
 * @author auto-generated
 * @see BlogPost
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "saved_posts",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_SAVED_POST_USER_POST",
                columnNames = {"user_id", "post_id"}
        ))
public class SavedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saved_post_id", nullable = false, updatable = false)
    private Long savedPostId;

    /** Người lưu. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_SAVED_POST_USER"))
    private User user;

    /** Bài viết được lưu. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_SAVED_POST_POST"))
    private BlogPost post;

    /** Thời điểm lưu. */
    @Column(name = "saved_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime savedAt;
}
