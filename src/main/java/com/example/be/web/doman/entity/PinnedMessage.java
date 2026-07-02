package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho tin nhắn được ghim ({@code PinnedMessage}).
 *
 * <p>Ràng buộc UNIQUE trên (convo_id, message_id).</p>
 *
 * @author auto-generated
 * @see Conversation
 * @see Message
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pinned_messages",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_PINNED_CONVO_MESSAGE",
                columnNames = {"convo_id", "message_id"}
        ))
public class PinnedMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pinned_message_id", nullable = false, updatable = false)
    private Long pinnedMessageId;

    /** Cuộc hội thoại. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convo_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PINNED_CONVERSATION"))
    private Conversation conversation;

    /** Tin nhắn được ghim. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PINNED_MESSAGE"))
    private Message message;

    /** Người ghim. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pinned_by", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PINNED_USER"))
    private User pinnedBy;

    /** Thời điểm ghim. */
    @Column(name = "pinned_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime pinnedAt;
}
