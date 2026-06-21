package com.example.be.web.doman.dto.response.assignment;

import com.example.be.web.doman.model.AssignmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO trả về thông tin bài tập.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response chứa thông tin bài tập")
public class AssignmentResponseDto {

    @Schema(description = "ID bài tập")
    private Long assignmentId;

    @Schema(description = "ID lớp học")
    private Long classId;

    @Schema(description = "Tên lớp học")
    private String className;

    @Schema(description = "Tiêu đề bài tập")
    private String title;

    @Schema(description = "Mô tả chi tiết")
    private String description;

    @Schema(description = "Hạn nộp bài")
    private LocalDateTime deadline;

    @Schema(description = "Điểm tối đa")
    private Double maxScore;

    @Schema(description = "Cho phép nộp trễ")
    private Boolean allowLateSubmit;

    @Schema(description = "ID người tạo")
    private Long createdById;

    @Schema(description = "Tên người tạo")
    private String createdByName;

    @Schema(description = "Trạng thái bài tập")
    private AssignmentStatus status;

    @Schema(description = "Số bài nộp")
    private Long submissionCount;

    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;

    @Schema(description = "Ngày cập nhật")
    private LocalDateTime updatedAt;
}
