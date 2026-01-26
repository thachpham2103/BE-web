package com.example.be.web.controller;

import com.example.be.web.doman.entity.AttendanceRecord;
import com.example.be.web.service.AttendanceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceRecordController {

    private final AttendanceRecordService attendanceRecordService;

    @PreAuthorize("hasAnyRole('USER')")
    // 1. Điểm danh (check-in)
    @PostMapping("/checkin")
    @Operation(summary = "API record by user", description = "User")
    public ResponseEntity<AttendanceRecord> checkIn(
            @RequestParam Long sessionId,
            @RequestParam Long userId,
            @RequestParam MultipartFile faceImage,
            @RequestParam double gpsLat,
            @RequestParam double gpsLng) {

        AttendanceRecord record = attendanceRecordService.checkIn(sessionId, userId, faceImage, gpsLat, gpsLng);
        return ResponseEntity.ok(record);
    }

    // 2. Lấy record theo ID
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/{id}")
    @Operation(summary = "API get record by id", description = "Admin / Leader")
    public ResponseEntity<AttendanceRecord> getRecordById(@PathVariable Long id) {
        AttendanceRecord record = attendanceRecordService.getRecordById(id);
        return ResponseEntity.ok(record);
    }

    // 3. Lấy danh sách record theo session
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/session/{sessionId}")
    @Operation(summary = "API get record by id", description = "Admin / Leader")
    public ResponseEntity<List<AttendanceRecord>> getRecordsBySession(@PathVariable Long sessionId) {
        List<AttendanceRecord> records = attendanceRecordService.getRecordsBySession(sessionId);
        return ResponseEntity.ok(records);
    }

    // 4. Lấy danh sách record theo user
    @PreAuthorize("hasAnyRole('USER','TEACHER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AttendanceRecord>> getRecordsByUser(@PathVariable Long userId) {
        List<AttendanceRecord> records = attendanceRecordService.getRecordsByUser(userId);
        return ResponseEntity.ok(records);
    }
}
