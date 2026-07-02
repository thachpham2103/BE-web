package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho bình luận bài viết ({@code BlogComment}).
 *
 * <p>Hỗ trợ bình luận lồng nhau thông qua {@link #parent} (nullable).
 * Parent = null nghĩa là bình luận gốc.</p>
 *
 * @author auto-generated
 * @see BlogPost
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "blog_comments")
public class BlogComment extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, updatable = false)
    private Long commentId;

    /** Bài viết chứa bình luận. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_COMMENT_POST"))
    private BlogPost post;

    /** Người bình luận. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_COMMENT_USER"))
    private User user;

    /** Bình luận cha (nullable = bình luận gốc). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",
            foreignKey = @ForeignKey(name = "FK_COMMENT_PARENT"))
    private BlogComment parent;

    /** Nội dung bình luận. */
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    /** Danh sách bình luận con. */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<BlogComment> replies = new ArrayList<>();
}
