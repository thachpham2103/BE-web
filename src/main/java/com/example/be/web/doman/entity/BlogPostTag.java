package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity đại diện cho quan hệ N-N giữa bài viết và tag ({@code BlogPostTag}).
 *
 * <p>Ràng buộc UNIQUE trên (post_id, tag_id).</p>
 *
 * @author auto-generated
 * @see BlogPost
 * @see Tag
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "blog_post_tags",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_BLOG_POST_TAG",
                columnNames = {"post_id", "tag_id"}
        ))
public class BlogPostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_post_tag_id", nullable = false, updatable = false)
    private Long blogPostTagId;

    /** Bài viết. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_POST_TAG_POST"))
    private BlogPost post;

    /** Tag. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_POST_TAG_TAG"))
    private Tag tag;
}
