package com.example.be.web.controller;

import com.example.be.web.doman.dto.request.classRoom.ClassRoomRequestDto;
import com.example.be.web.doman.dto.response.attendance.SessionAttendanceStatsDto;
import com.example.be.web.doman.dto.response.classRoom.ClassRoomResponseDto;
import com.example.be.web.service.ClassRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassRoomController {

    private final ClassRoomService classRoomService;

    // Lấy danh sách classrooms (phân trang)
    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "API get all classrooms with pagination", description = "Admin / Leader")
    public Page<ClassRoomResponseDto> getAllClassRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return classRoomService.getAllClassRooms(pageable);
    }

    // Lấy chi tiết classroom theo id
    @Tag(name="classRoom")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','LEADER')")
    @Operation(summary = "API get classroom by id", description = "Admin / Leader")
    public ClassRoomResponseDto getClassRoomById(@PathVariable Long id) {
        return classRoomService.getClassRoomById(id);
    }

    // Tạo mới classroom
    @Tag(name="classRoom")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "API create new classroom", description = "Admin / Leader")
    public ClassRoomResponseDto createClassRoom(@RequestBody ClassRoomRequestDto requestDto) {
        return classRoomService.createClassRoom(requestDto);
    }

    // Cập nhật classroom theo id
    @Tag(name="classRoom")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "API update classroom by id", description = "Admin / Leader")
    public ClassRoomResponseDto updateClassRoom(@PathVariable Long id,
                                                @RequestBody ClassRoomRequestDto requestDto) {
        return classRoomService.updateClassRoom(id, requestDto);
    }

    // Xóa classroom theo id
    @Tag(name="classRoom")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "API delete classroom by id", description = "Admin / Leader")
    public void deleteClassRoom(@PathVariable Long id) {
        classRoomService.deleteClassRoom(id);
    }

    @Tag(name="classRoom")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @GetMapping("/{classId}/attendance/stats")
    @Operation(summary = "API thống kê điểm danh theo buổi của lớp học", description = "Admin / Leader")
    public ResponseEntity<List<SessionAttendanceStatsDto>> getAttendanceStatsBySession(@PathVariable Long classId) {
        List<SessionAttendanceStatsDto> stats = classRoomService.getAttendanceStatsBySession(classId);
        return ResponseEntity.ok(stats);
    }

    // Đếm tổng số sinh viên đã đăng ký trong lớp học
    @Tag(name="classRoom")
    @GetMapping("/{classId}/totalStudent")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "API lấy tổng số sinh viên của lớp học", description = "Admin / Leader")
    public ResponseEntity<Long> getTotalStudentsInClass(@PathVariable Long classId) {
        long total = classRoomService.countStudentsInClassRoom(classId);
        return ResponseEntity.ok(total);
    }

}
