package com.example.be.web.controller;

import com.example.be.web.doman.dto.response.attendance.AttendanceRecordResponseDto;
import com.example.be.web.doman.entity.AttendanceRecord;
import com.example.be.web.service.AttendanceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;


import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceRecordController {

    private final AttendanceRecordService attendanceRecordService;

//    @PreAuthorize("hasAnyRole('USER')")
    // 1. Điểm danh (check-in)
    @PostMapping(
            value = "/checkin",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )

    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "API record by user", description = "User")
    public ResponseEntity<AttendanceRecordResponseDto> checkIn(
            @RequestParam("sessionId") Long sessionId,
            @RequestParam("faceImage") MultipartFile faceImage,
            @RequestParam("gpsLat") double gpsLat,
            @RequestParam("gpsLng") double gpsLng) {

        AttendanceRecordResponseDto record =
                attendanceRecordService.checkIn(sessionId, faceImage, gpsLat, gpsLng);

        return ResponseEntity.ok(record);
    }

    // 2. Lấy record theo ID
    @PreAuthorize("hasAnyRole('ADMIN', 'LEADER')")
    @GetMapping("/{id}")
    @Operation(summary = "API get record by id", description = "Admin / Leader")
    public ResponseEntity<AttendanceRecordResponseDto> getRecordById(@PathVariable Long id) {
        AttendanceRecordResponseDto record = attendanceRecordService.getRecordById(id);
        return ResponseEntity.ok(record);
    }

    // 3. Lấy danh sách record theo session
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/session/{sessionId}")
    @Operation(summary = "API get record by session", description = "Admin / Leader")
    public ResponseEntity<List<AttendanceRecordResponseDto>> getRecordsBySession(@PathVariable Long sessionId) {
        List<AttendanceRecordResponseDto> records = attendanceRecordService.getRecordsBySession(sessionId);
        return ResponseEntity.ok(records);
    }

    // 4. Lấy danh sách record theo user
    @PreAuthorize("hasAnyRole('TEACHER')")
    @GetMapping("/user/{userId}")
    @Operation(summary = "API get records by userId", description = "Teacher/Admin xem danh sách record của một user cụ thể")
    public ResponseEntity<List<AttendanceRecordResponseDto>> getRecordsByUser(@PathVariable Long userId) {
        List<AttendanceRecordResponseDto> records = attendanceRecordService.getRecordsByUser(userId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "API get records of current user", description = "User xem danh sách record của chính mình")
    public ResponseEntity<List<AttendanceRecordResponseDto>> getMyRecords() {
        List<AttendanceRecordResponseDto> records = attendanceRecordService.getMyRecords();
        return ResponseEntity.ok(records);
    }

}
