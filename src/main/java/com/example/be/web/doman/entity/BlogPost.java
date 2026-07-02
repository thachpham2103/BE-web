package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import com.example.be.web.doman.model.BlogPostStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho bài viết blog ({@code BlogPost}).
 *
 * <p>Mỗi bài viết thuộc về một {@link User} (tác giả).
 * Hỗ trợ soft-delete thông qua trạng thái {@link BlogPostStatus#DELETED}.</p>
 *
 * @author auto-generated
 * @see BlogComment
 * @see BlogLike
 * @see BlogPostTag
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "blog_posts")
public class BlogPost extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_post_id", nullable = false, updatable = false)
    private Long blogPostId;

    /** Tác giả bài viết. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_BLOG_POST_AUTHOR"))
    private User author;

    /** Tiêu đề bài viết. */
    @Column(name = "title", nullable = false, length = 300)
    private String title;

    /** Nội dung bài viết. */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /** Ảnh thumbnail. */
    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    /** Trạng thái bài viết. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private BlogPostStatus status = BlogPostStatus.DRAFT;

    /** Số lượt xem. */
    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Integer viewCount = 0;

    /** Danh sách bình luận. */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<BlogComment> comments = new ArrayList<>();

    /** Danh sách lượt thích. */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<BlogLike> likes = new ArrayList<>();

    /** Danh sách tag. */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<BlogPostTag> tags = new ArrayList<>();

    /** Danh sách bài đã lưu. */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<SavedPost> savedPosts = new ArrayList<>();
}
