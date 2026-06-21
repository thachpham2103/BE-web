package com.example.be.web.doman.dto.request.attendance;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO nhận dữ liệu tạo đơn giải trình điểm danh.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body giải trình điểm danh")
public class AttendanceAppealRequestDto {

    @NotNull(message = "Mã buổi điểm danh không được để trống")
    @Schema(description = "ID buổi điểm danh", example = "1")
    private Long sessionId;

    @NotNull(message = "Mã bản ghi điểm danh không được để trống")
    @Schema(description = "ID bản ghi điểm danh", example = "1")
    private Long recordId;

    @NotBlank(message = "Lý do giải trình không được để trống")
    @Schema(description = "Lý do giải trình", example = "Em bị ốm nên không thể đến lớp")
    private String reason;

    @Schema(description = "URL ảnh minh chứng", example = "https://storage.example.com/proof.jpg")
    private String proofImageUrl;
}
