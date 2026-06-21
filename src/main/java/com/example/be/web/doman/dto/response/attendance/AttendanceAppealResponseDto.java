package com.example.be.web.doman.dto.response.attendance;

import com.example.be.web.doman.model.AppealStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO trả về thông tin giải trình điểm danh.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response giải trình điểm danh")
public class AttendanceAppealResponseDto {

    @Schema(description = "ID giải trình")
    private Long appealId;

    @Schema(description = "ID sinh viên")
    private Long studentId;

    @Schema(description = "Tên sinh viên")
    private String studentName;

    @Schema(description = "ID buổi điểm danh")
    private Long sessionId;

    @Schema(description = "Tiêu đề buổi điểm danh")
    private String sessionTitle;

    @Schema(description = "ID bản ghi điểm danh")
    private Long recordId;

    @Schema(description = "Lý do giải trình")
    private String reason;

    @Schema(description = "URL ảnh minh chứng")
    private String proofImageUrl;

    @Schema(description = "Trạng thái giải trình")
    private AppealStatus status;

    @Schema(description = "Ghi chú giảng viên")
    private String teacherNote;

    @Schema(description = "ID người duyệt")
    private Long reviewedById;

    @Schema(description = "Tên người duyệt")
    private String reviewedByName;

    @Schema(description = "Thời gian duyệt")
    private LocalDateTime reviewedAt;

    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;
}
