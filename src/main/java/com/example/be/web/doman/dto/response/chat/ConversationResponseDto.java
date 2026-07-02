package com.example.be.web.doman.dto.response.chat;

import com.example.be.web.doman.model.ConversationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin cuộc hội thoại")
public class ConversationResponseDto {

    @Schema(description = "ID cuộc hội thoại")
    private Long convoId;
    @Schema(description = "Tên nhóm")
    private String name;
    @Schema(description = "Loại cuộc hội thoại")
    private ConversationType conversationType;
    @Schema(description = "ID lớp học")
    private Long classId;
    @Schema(description = "Thời điểm tin nhắn cuối")
    private LocalDateTime lastMessageAt;
    @Schema(description = "ID người tạo")
    private Long createdById;
    @Schema(description = "Tên người tạo")
    private String createdByName;
    @Schema(description = "Ngày tạo")
    private LocalDateTime createAt;
    @Schema(description = "Số thành viên")
    private Integer memberCount;
    @Schema(description = "Danh sách thành viên")
    private List<ConversationMemberResponseDto> members;
}
