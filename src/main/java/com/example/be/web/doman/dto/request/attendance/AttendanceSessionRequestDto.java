package com.example.be.web.doman.dto.request.attendance;

import com.example.be.web.constant.ErrorMessage;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * DTO cho yêu cầu tạo phiên điểm danh.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AttendanceSessionRequestDto {

    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    private String title;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private LocalDateTime startTime;

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private LocalDateTime endTime;

//    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
//    private Double  locationLatitude;
//
//    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
//    private Double  locationLongitude ;

//    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
//    @Positive(message = "Radius phải lớn hơn 0")
//    private Double radiusMeters;

    // Kiểm tra tính hợp lệ của khoảng thời gian
    @AssertTrue(message = "Start time must be before end time")
    public boolean isValidTimeRange() {
        return startTime != null && endTime != null && startTime.isBefore(endTime);
    }

    @NotNull(message = ErrorMessage.NOT_BLANK_FIELD)
    private Long locationId;

//    @NotNull(message = "CreatedByUserId không được null")
//    private Long createdByUserId;

}

