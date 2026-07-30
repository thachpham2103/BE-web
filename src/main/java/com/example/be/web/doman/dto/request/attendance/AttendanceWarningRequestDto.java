package com.example.be.web.doman.dto.request.attendance;

import com.example.be.web.doman.model.WarningLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO nhận dữ liệu tạo cảnh báo.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body tạo cảnh báo điểm danh")
public class AttendanceWarningRequestDto {

    @NotNull(message = "ID sinh viên không được để trống")
    @Schema(description = "ID sinh viên", example = "1")
    private Long studentId;

    @NotNull(message = "ID lớp học không được để trống")
    @Schema(description = "ID lớp học", example = "1")
    private Long classId;

    @NotNull(message = "Mức độ cảnh báo không được để trống")
    @Schema(description = "Mức cảnh báo", example = "LOW")
    private WarningLevel warningLevel;

    @Schema(description = "Thông điệp cảnh báo", example = "Bạn đã nghỉ quá nhiều")
    private String message;
}
