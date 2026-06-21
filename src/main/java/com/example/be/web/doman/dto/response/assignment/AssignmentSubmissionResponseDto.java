package com.example.be.web.doman.dto.response.assignment;

import com.example.be.web.doman.model.SubmissionAssignmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO trả về thông tin bài nộp.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response chứa thông tin bài nộp")
public class AssignmentSubmissionResponseDto {

    @Schema(description = "ID bài nộp")
    private Long submissionId;

    @Schema(description = "ID bài tập")
    private Long assignmentId;

    @Schema(description = "Tiêu đề bài tập")
    private String assignmentTitle;

    @Schema(description = "ID sinh viên")
    private Long studentId;

    @Schema(description = "Tên sinh viên")
    private String studentName;

    @Schema(description = "Nội dung bài nộp")
    private String content;

    @Schema(description = "URL file đính kèm")
    private String fileUrl;

    @Schema(description = "Thời gian nộp bài")
    private LocalDateTime submittedAt;

    @Schema(description = "Trạng thái bài nộp")
    private SubmissionAssignmentStatus status;

    @Schema(description = "Điểm")
    private Double score;

    @Schema(description = "Nhận xét giảng viên")
    private String teacherComment;

    @Schema(description = "ID người chấm")
    private Long gradedById;

    @Schema(description = "Tên người chấm")
    private String gradedByName;

    @Schema(description = "Thời gian chấm")
    private LocalDateTime gradedAt;
}
