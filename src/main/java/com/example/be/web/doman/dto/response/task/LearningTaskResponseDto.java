package com.example.be.web.doman.dto.response.task;

import com.example.be.web.doman.model.TargetType;
import com.example.be.web.doman.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin nhiệm vụ học tập")
public class LearningTaskResponseDto {

    @Schema(description = "ID nhiệm vụ")
    private Long taskId;
    @Schema(description = "ID lớp học")
    private Long classId;
    @Schema(description = "Tên lớp học")
    private String className;
    @Schema(description = "ID người tạo")
    private Long createdById;
    @Schema(description = "Tên người tạo")
    private String createdByName;
    @Schema(description = "Tiêu đề")
    private String title;
    @Schema(description = "Mô tả")
    private String description;
    @Schema(description = "Loại đối tượng liên kết")
    private TargetType targetType;
    @Schema(description = "ID đối tượng liên kết")
    private Long targetId;
    @Schema(description = "Hạn hoàn thành")
    private LocalDateTime deadline;
    @Schema(description = "Trạng thái")
    private TaskStatus status;
    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;
}
