package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "document_view_history",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "doc_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentViewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_id")
    private Long viewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", nullable = false)
    private Document document;

    @Column(name = "viewed_at")
    private LocalDateTime viewedAt;

    @Column(name = "view_count")
    private Integer viewCount;

    @PrePersist
    public void prePersist() {
        this.viewedAt = LocalDateTime.now();

        if (this.viewCount == null) {
            this.viewCount = 1;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.viewedAt = LocalDateTime.now();
    }
}