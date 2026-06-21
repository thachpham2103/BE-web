package com.example.be.web.doman.dto.request.attendance;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO nhận dữ liệu tạo / cập nhật chính sách điểm danh.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body chính sách điểm danh")
public class AttendancePolicyRequestDto {

    @NotNull(message = "Mã lớp học không được để trống")
    @Schema(description = "ID lớp học", example = "1")
    private Long classId;

    @NotNull(message = "Ngưỡng cảnh báo không được để trống")
    @Min(value = 1, message = "Ngưỡng cảnh báo phải >= 1")
    @Schema(description = "Số buổi vắng để cảnh báo", example = "3")
    private Integer warningThreshold;

    @NotNull(message = "Ngưỡng cấm thi không được để trống")
    @Min(value = 1, message = "Ngưỡng cấm thi phải >= 1")
    @Schema(description = "Số buổi vắng để cấm thi", example = "5")
    private Integer banThreshold;

    @NotNull(message = "Số phút cho phép trễ không được để trống")
    @Min(value = 0, message = "Số phút cho phép trễ phải >= 0")
    @Schema(description = "Số phút cho phép đến trễ", example = "15")
    private Integer allowLateMinutes;
}
