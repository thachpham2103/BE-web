package com.example.be.web.doman.dto.response.chat;

import com.example.be.web.doman.model.ConversationMemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin thành viên cuộc hội thoại")
public class ConversationMemberResponseDto {

    @Schema(description = "ID người dùng")
    private Long userId;
    @Schema(description = "Tên người dùng")
    private String userName;
    @Schema(description = "Avatar")
    private String avatarUrl;
    @Schema(description = "Vai trò")
    private ConversationMemberRole role;
    @Schema(description = "Thời điểm tham gia")
    private LocalDateTime joinedAt;
    @Schema(description = "Tắt thông báo")
    private Boolean muted;
}
