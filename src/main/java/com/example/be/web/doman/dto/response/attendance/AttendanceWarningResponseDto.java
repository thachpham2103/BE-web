package com.example.be.web.doman.dto.response.attendance;

import com.example.be.web.doman.model.AppealStatus;
import com.example.be.web.doman.model.WarningLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO trả về thông tin cảnh báo nghỉ học.
 *
 * @author auto-generated
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response cảnh báo nghỉ học")
public class AttendanceWarningResponseDto {

    @Schema(description = "ID cảnh báo")
    private Long warningId;

    @Schema(description = "ID sinh viên")
    private Long studentId;

    @Schema(description = "Tên sinh viên")
    private String studentName;

    @Schema(description = "ID lớp học")
    private Long classId;

    @Schema(description = "Tên lớp học")
    private String className;

    @Schema(description = "Số buổi vắng")
    private Integer absentCount;

    @Schema(description = "Tổng số buổi")
    private Integer totalSession;

    @Schema(description = "Tỷ lệ vắng (%)")
    private Double absentRate;

    @Schema(description = "Mức cảnh báo")
    private WarningLevel warningLevel;

    @Schema(description = "Nội dung cảnh báo")
    private String message;

    @Schema(description = "Trạng thái")
    private AppealStatus status;

    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;
}
