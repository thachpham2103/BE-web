package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho tin nhắn ({@code Message}).
 *
 * <p>Mỗi tin nhắn thuộc một {@link Conversation} và do một {@link User} gửi.</p>
 *
 * @author auto-generated
 * @see Conversation
 * @see MessageAttachment
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false, updatable = false)
    private Long messageId;

    /** Cuộc hội thoại chứa tin nhắn. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convo_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_MESSAGE_CONVERSATION"))
    private Conversation conversation;

    /** Người gửi. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_MESSAGE_SENDER"))
    private User sender;

    /** Nội dung tin nhắn. */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /** Loại tin nhắn. */
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", length = 20, nullable = false)
    @Builder.Default
    private MessageType messageType = MessageType.TEXT;

    /** Thời gian gửi. */
    @Column(name = "sent_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime sentAt;

    /** Thời gian chỉnh sửa (nullable = chưa chỉnh sửa). */
    @Column(name = "edited_at")
    private LocalDateTime editedAt;

    /** Đã xóa (soft-delete). */
    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    /** Danh sách file đính kèm. */
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<MessageAttachment> attachments = new ArrayList<>();

    /** Danh sách trạng thái đọc. */
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<MessageReadStatus> readStatuses = new ArrayList<>();
}
