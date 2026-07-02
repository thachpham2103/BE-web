package com.example.be.web.doman.dto.response.task;

import com.example.be.web.doman.model.TaskProgressStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin tiến độ nhiệm vụ")
public class StudentTaskProgressResponseDto {

    @Schema(description = "ID tiến độ")
    private Long progressId;
    @Schema(description = "ID sinh viên")
    private Long studentId;
    @Schema(description = "Tên sinh viên")
    private String studentName;
    @Schema(description = "ID nhiệm vụ")
    private Long taskId;
    @Schema(description = "Tên nhiệm vụ")
    private String taskTitle;
    @Schema(description = "Trạng thái tiến độ")
    private TaskProgressStatus status;
    @Schema(description = "Thời điểm hoàn thành")
    private LocalDateTime completedAt;
    @Schema(description = "Ghi chú")
    private String note;
}
