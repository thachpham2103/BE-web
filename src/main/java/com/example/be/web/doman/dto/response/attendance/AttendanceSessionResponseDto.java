package com.example.be.web.doman.dto.response.attendance;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO phản hồi thông tin phiên điểm danh.
 */
@Data
public class AttendanceSessionResponseDto {
    private Long sessionId;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
//    private double locationLatitude;
//    private double locationLongitude ;
//    private double radiusMeters;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Long createdByUserId ;
    private Long locationId;
}
