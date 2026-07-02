package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "documents")

public class  Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id")
    private Long docId;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "uploader_id", nullable = false)
    private User uploader;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassRoom classRoom;

    /** Môn học liên quan. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id",
            foreignKey = @ForeignKey(name = "FK_DOCUMENT_SUBJECT"))
    private Subject subject;

    /** Số lượt yêu thích. */
    @Column(name = "favorite_count", nullable = false)
    @Builder.Default
    private Integer favoriteCount = 0;

    /** Số lượt xem. */
    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Integer viewCount = 0;

    /** Số lượt tải. */
    @Column(name = "download_count", nullable = false)
    @Builder.Default
    private Integer downloadCount = 0;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteDocument> favoriteDocuments = new ArrayList<>();

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentNote> documentNotes = new ArrayList<>();


}
