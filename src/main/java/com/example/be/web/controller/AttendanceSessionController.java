package com.example.be.web.controller;

import com.example.be.web.doman.dto.request.attendance.AttendanceSessionRequestDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceSessionResponseDto;
import com.example.be.web.doman.entity.AttendanceSession;
import com.example.be.web.service.AttendanceSessionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class AttendanceSessionController {

    private final AttendanceSessionService sessionService;

    // 1. Tạo buổi điểm danh
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    @Operation(summary = "API tạo buổi điểm danh", description = "Admin / Leader")
    public ResponseEntity<AttendanceSessionResponseDto> createSession(@RequestBody AttendanceSessionRequestDto requestDto) {
        AttendanceSessionResponseDto created = sessionService.createSession(requestDto);
        return ResponseEntity.ok(created);
    }

    // 2. Cập nhật buổi điểm danh
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{id}")
    @Operation(summary = "API cập nhật buổi điểm danh", description = "Admin / Leader")
    public ResponseEntity<AttendanceSessionResponseDto> updateSession(@PathVariable Long id,
                                                                      @RequestBody AttendanceSessionRequestDto requestDto) {
        AttendanceSessionResponseDto updated = sessionService.updateSession(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    // 3. Xóa buổi điểm danh
    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "API xóa buổi điểm danh theo id", description = "Admin / Leader")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

    // 4. Lấy buổi điểm danh theo ID
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceSessionResponseDto> getSessionById(@PathVariable Long id) {
        AttendanceSessionResponseDto session = sessionService.getSessionById(id);
        return ResponseEntity.ok(session);
    }

    // 5. Lấy tất cả buổi điểm danh
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping
    public ResponseEntity<List<AttendanceSessionResponseDto>> getAllSessions() {
        List<AttendanceSessionResponseDto> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }
}

