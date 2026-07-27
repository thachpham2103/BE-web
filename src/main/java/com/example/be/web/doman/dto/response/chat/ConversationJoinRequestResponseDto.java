package com.example.be.web.doman.dto.response.chat;

import com.example.be.web.doman.entity.ConversationJoinRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationJoinRequestResponseDto {
    private Long id;
    private Long requestId; // duplicate of id for frontend compatibility
    private Long conversationId;
    private String conversationName;
    private Long userId;
    private String studentName; // FE reads this
    private String status;
    private String createdAt;

    public static ConversationJoinRequestResponseDto fromEntity(ConversationJoinRequest entity) {
        return ConversationJoinRequestResponseDto.builder()
                .id(entity.getId())
                .requestId(entity.getId())
                .conversationId(entity.getConversation().getConvoId())
                .conversationName(entity.getConversation().getName())
                .userId(entity.getUser().getId())
                .studentName(entity.getUser().getFullName() != null ? entity.getUser().getFullName() : entity.getUser().getUsername())
                .status(entity.getStatus().name())
                .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }
}
