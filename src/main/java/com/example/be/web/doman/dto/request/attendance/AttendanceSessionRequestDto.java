package com.example.be.web.doman.dto.request.attendance;

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

    @NotBlank(message = "Title không được để trống")
    private String title;

    @NotNull(message = "Start time không được null")
    private LocalDateTime startTime;

    @NotNull(message = "End time không được null")
    private LocalDateTime endTime;

    @NotNull(message = "Latitude không được null")
    private double locationLatitude;

    @NotNull(message = "Longitude không được null")
    private double locationLongitude ;

    @Positive(message = "Radius phải lớn hơn 0")
    private int radiusMeters;

    @NotNull(message = "CreatedByUserId không được null")
    private Long createdByUserId;

}

