package com.example.be.web.doman.dto.response.attendance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO trả về thông tin chính sách điểm danh.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response chính sách điểm danh")
public class AttendancePolicyResponseDto {

    @Schema(description = "ID chính sách")
    private Long policyId;

    @Schema(description = "ID lớp học")
    private Long classId;

    @Schema(description = "Tên lớp học")
    private String className;

    @Schema(description = "Ngưỡng cảnh báo (số buổi vắng)")
    private Integer warningThreshold;

    @Schema(description = "Ngưỡng cấm thi (số buổi vắng)")
    private Integer banThreshold;

    @Schema(description = "Số phút cho phép trễ")
    private Integer allowLateMinutes;

    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;

    @Schema(description = "Ngày cập nhật")
    private LocalDateTime updatedAt;
}
