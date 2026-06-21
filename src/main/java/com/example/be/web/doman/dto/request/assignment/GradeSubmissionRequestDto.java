package com.example.be.web.doman.dto.request.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO nhận dữ liệu yêu cầu chấm điểm bài nộp.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body để chấm điểm bài nộp")
public class GradeSubmissionRequestDto {

    /** Điểm chấm. */
    @NotNull(message = "Điểm không được để trống")
    @DecimalMin(value = "0.0", message = "Điểm phải >= 0")
    @Schema(description = "Điểm chấm", example = "8.5")
    private Double score;

    /** Nhận xét của giảng viên. */
    @Schema(description = "Nhận xét của giảng viên", example = "Bài làm tốt, cần cải thiện phần 3")
    private String teacherComment;
}
