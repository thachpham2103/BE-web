package com.example.be.web.doman.dto.response.attendance;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AttendanceRecordResponseDto {

    private Long recordId;
    private LocalDateTime checkinTime;
    private String note;
    private Boolean resultFace;
    private Double gpsLatitude;
    private Double gpsLongitude;
    private String recordStatus;

    // thông tin người dùng
    private Long userId;
    private String userName;

    // thông tin phiên điểm danh
    private Long attendanceSessionId;
    private String attendanceSessionTitle;

    // thông tin lớp học
    private Long classId;
    private String className;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
