package com.example.be.web.doman.dto.request.assignment;

import com.example.be.web.doman.model.SubmissionAssignmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO nhận dữ liệu yêu cầu nộp bài / cập nhật bài nộp.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body để nộp bài hoặc cập nhật bài nộp")
public class AssignmentSubmissionRequestDto {

    /** Mã bài tập. */
    @NotNull(message = "Mã bài tập không được để trống")
    @Schema(description = "ID bài tập", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long assignmentId;

    /** Nội dung bài nộp. */
    @Schema(description = "Nội dung bài nộp (văn bản)", example = "Bài làm của em...")
    private String content;

    /** Đường dẫn file đính kèm. */
    @Schema(description = "URL file đính kèm", example = "https://storage.example.com/files/baitap1.pdf")
    private String fileUrl;
}
