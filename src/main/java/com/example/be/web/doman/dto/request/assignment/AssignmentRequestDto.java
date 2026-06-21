package com.example.be.web.doman.dto.request.assignment;

import com.example.be.web.doman.model.AssignmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO nhận dữ liệu yêu cầu tạo / cập nhật bài tập.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body để tạo hoặc cập nhật bài tập")
public class AssignmentRequestDto {

    /** Mã lớp học chứa bài tập. */
    @NotNull(message = "Mã lớp học không được để trống")
    @Schema(description = "ID lớp học", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long classId;

    /** Tiêu đề bài tập. */
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 300, message = "Tiêu đề tối đa 300 ký tự")
    @Schema(description = "Tiêu đề bài tập", example = "Bài tập chương 1")
    private String title;

    /** Mô tả chi tiết. */
    @Schema(description = "Mô tả chi tiết bài tập", example = "Làm bài tập từ trang 10 đến trang 20")
    private String description;

    /** Hạn nộp bài. */
    @Schema(description = "Hạn nộp bài", example = "2026-07-01T23:59:00")
    private LocalDateTime deadline;

    /** Điểm tối đa. */
    @Positive(message = "Điểm tối đa phải lớn hơn 0")
    @Schema(description = "Điểm tối đa", example = "10.0")
    private Double maxScore;

    /** Cho phép nộp trễ. */
    @Schema(description = "Cho phép nộp trễ sau deadline", example = "false")
    private Boolean allowLateSubmit;

    /** Trạng thái bài tập. */
    @Schema(description = "Trạng thái bài tập", example = "DRAFT")
    private AssignmentStatus status;
}
