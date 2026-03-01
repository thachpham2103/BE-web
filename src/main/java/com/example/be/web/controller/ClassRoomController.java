package com.example.be.web.controller;

import com.example.be.web.doman.dto.request.classRoom.ClassRoomRequestDto;
import com.example.be.web.doman.dto.response.classRoom.ClassRoomResponseDto;
import com.example.be.web.service.ClassRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassRoomController {

    private final ClassRoomService classRoomService;

    // Lấy danh sách classrooms (phân trang)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "API get all classrooms with pagination", description = "Admin / Leader")
    public Page<ClassRoomResponseDto> getAllClassRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return classRoomService.getAllClassRooms(pageable);
    }

    // Lấy chi tiết classroom theo id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','LEADER')")
    @Operation(summary = "API get classroom by id", description = "Admin / Leader")
    public ClassRoomResponseDto getClassRoomById(@PathVariable Long id) {
        return classRoomService.getClassRoomById(id);
    }

    // Tạo mới classroom
    @Tag(name="admin_leader")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "API create new classroom", description = "Admin / Leader")
    public ClassRoomResponseDto createClassRoom(@RequestBody ClassRoomRequestDto requestDto) {
        return classRoomService.createClassRoom(requestDto);
    }

    // Cập nhật classroom theo id
    @Tag(name="admin_leader")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "API update classroom by id", description = "Admin / Leader")
    public ClassRoomResponseDto updateClassRoom(@PathVariable Long id,
                                                @RequestBody ClassRoomRequestDto requestDto) {
        return classRoomService.updateClassRoom(id, requestDto);
    }

    // Xóa classroom theo id
    @Tag(name="admin_leader")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "API delete classroom by id", description = "Admin / Leader")
    public void deleteClassRoom(@PathVariable Long id) {
        classRoomService.deleteClassRoom(id);
    }
}
