package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho file đính kèm tin nhắn ({@code MessageAttachment}).
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
@Table(name = "message_attachments")
public class MessageAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id", nullable = false, updatable = false)
    private Long attachmentId;

    /** Tin nhắn chứa file. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_ATTACHMENT_MESSAGE"))
    private Message message;

    /** URL file. */
    @Column(name = "file_url", length = 500, nullable = false)
    private String fileUrl;

    /** Tên file. */
    @Column(name = "file_name", length = 255)
    private String fileName;

    /** Kích thước file (bytes). */
    @Column(name = "file_size")
    private Long fileSize;

    /** Loại MIME. */
    @Column(name = "mime_type", length = 100)
    private String mimeType;

    /** Thời điểm upload. */
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime uploadedAt;
}
