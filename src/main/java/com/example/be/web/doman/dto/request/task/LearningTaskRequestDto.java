package com.example.be.web.doman.dto.request.task;

import com.example.be.web.doman.model.TargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request body tạo nhiệm vụ học tập")
public class LearningTaskRequestDto {

    @NotNull(message = "ID lớp học không được để trống")
    @Schema(description = "ID lớp học", example = "1")
    private Long classId;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Schema(description = "Tiêu đề nhiệm vụ", example = "Hoàn thành bài tập chương 1")
    private String title;

    @Schema(description = "Mô tả nhiệm vụ")
    private String description;

    @Schema(description = "Loại đối tượng liên kết")
    private TargetType targetType;

    @Schema(description = "ID đối tượng liên kết")
    private Long targetId;

    @Schema(description = "Hạn hoàn thành")
    private LocalDateTime deadline;
}
