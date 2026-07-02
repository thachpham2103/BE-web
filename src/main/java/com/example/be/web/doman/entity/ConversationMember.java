package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.ConversationMemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho thành viên của cuộc hội thoại.
 *
 * <p>Composite PK trên (convo_id, user_id).
 * Hỗ trợ phân quyền nhóm và trạng thái đọc.</p>
 *
 * @author auto-generated
 */
@Entity
@Table(name = "conversation_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationMember {

    @EmbeddedId
    private ConversationMemberId id;

    @ManyToOne
    @MapsId("convoId")
    @JoinColumn(name = "convo_id")
    private Conversation conversation;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    /** Vai trò trong cuộc hội thoại. */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20)
    @Builder.Default
    private ConversationMemberRole role = ConversationMemberRole.MEMBER;

    @Column(name = "joined_at", nullable = false)
    @Builder.Default
    private LocalDateTime joinedAt = LocalDateTime.now();

    /** Thời điểm rời nhóm (nullable = vẫn còn trong nhóm). */
    @Column(name = "left_at")
    private LocalDateTime leftAt;

    /** Thời điểm đọc tin nhắn cuối cùng. */
    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt;

    /** Tắt thông báo cho cuộc hội thoại này. */
    @Column(name = "muted", nullable = false)
    @Builder.Default
    private Boolean muted = false;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConversationMemberId implements Serializable {

        @Column(name = "convo_id")
        private Long convoId;

        @Column(name = "user_id")
        private Long userId;
    }
}
