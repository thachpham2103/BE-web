package com.example.be.web.doman.dto.request.attendance;

import com.example.be.web.doman.model.AppealStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO nhận dữ liệu xét duyệt giải trình.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body xét duyệt giải trình")
public class ReviewAppealRequestDto {

    @NotNull(message = "Trạng thái duyệt không được để trống")
    @Schema(description = "Trạng thái duyệt (APPROVED / REJECTED)", example = "APPROVED")
    private AppealStatus status;

    @Schema(description = "Ghi chú của giảng viên", example = "Đã xác minh, chấp nhận giải trình")
    private String teacherNote;
}
