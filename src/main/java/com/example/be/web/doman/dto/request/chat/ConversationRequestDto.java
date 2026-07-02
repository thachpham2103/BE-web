package com.example.be.web.doman.dto.request.chat;

import com.example.be.web.doman.model.ConversationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request body tạo cuộc hội thoại")
public class ConversationRequestDto {

    @Schema(description = "Tên nhóm (nullable cho chat riêng)", example = "Nhóm lớp CNTT01")
    private String name;

    @NotNull(message = "Loại cuộc hội thoại không được để trống")
    @Schema(description = "Loại cuộc hội thoại", example = "GROUP")
    private ConversationType conversationType;

    @Schema(description = "ID lớp học (dùng cho loại CLASS)")
    private Long classId;

    @Schema(description = "Danh sách ID thành viên")
    private List<Long> memberIds;
}
