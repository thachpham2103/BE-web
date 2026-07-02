package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.ConversationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho cuộc hội thoại ({@code Conversation}).
 *
 * <p>Hỗ trợ chat nhóm, chat riêng, chat lớp học.
 * Quản lý thành viên qua {@link ConversationMember}.</p>
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "convo_id")
    private Long convoId;

    /** Tên nhóm chat (nullable cho chat riêng). */
    @Column(name = "name", length = 200)
    private String name;

    /** Loại cuộc hội thoại. */
    @Enumerated(EnumType.STRING)
    @Column(name = "conversation_type", length = 20)
    @Builder.Default
    private ConversationType conversationType = ConversationType.PRIVATE;

    /** Lớp học liên kết (nullable, chỉ dùng cho loại CLASS). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id",
            foreignKey = @ForeignKey(name = "FK_CONVO_CLASS"))
    private ClassRoom classRoom;

    /** Thời điểm tin nhắn cuối cùng. */
    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;

    /** Người tạo cuộc hội thoại. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by",
            foreignKey = @ForeignKey(name = "FK_CONVO_CREATOR"))
    private User createdBy;

    @Column(name = "create_at", nullable = false)
    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now();

    /** Danh sách thành viên. */
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<ConversationMember> members = new ArrayList<>();

    /** Danh sách tin nhắn. */
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<Message> messages = new ArrayList<>();
}
