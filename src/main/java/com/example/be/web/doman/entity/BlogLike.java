package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho lượt thích bài viết ({@code BlogLike}).
 *
 * <p>Ràng buộc UNIQUE trên (post_id, user_id) — mỗi user chỉ like 1 lần.</p>
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
@Table(name = "blog_likes",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_BLOG_LIKE_POST_USER",
                columnNames = {"post_id", "user_id"}
        ))
public class BlogLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", nullable = false, updatable = false)
    private Long likeId;

    /** Bài viết được like. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_LIKE_POST"))
    private BlogPost post;

    /** Người like. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_LIKE_USER"))
    private User user;

    /** Thời điểm like. */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
