package com.example.be.web.controller;

import com.example.be.web.base.RestData;
import com.example.be.web.base.VsResponseUtil;
import com.example.be.web.doman.dto.request.task.LearningTaskRequestDto;
import com.example.be.web.doman.model.TaskProgressStatus;
import com.example.be.web.service.LearningTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Learning Task", description = "API quản lý nhiệm vụ học tập")
public class LearningTaskController {

    private final LearningTaskService learningTaskService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Tạo nhiệm vụ học tập")
    public ResponseEntity<RestData<?>> createTask(
            @Valid @RequestBody LearningTaskRequestDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return VsResponseUtil.success(HttpStatus.CREATED, learningTaskService.createTask(dto, userDetails.getUsername()));
    }

    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy danh sách nhiệm vụ theo lớp")
    public ResponseEntity<RestData<?>> getTasksByClass(
            @PathVariable Long classId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return VsResponseUtil.success(learningTaskService.getTasksByClass(classId, pageable));
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy chi tiết nhiệm vụ")
    public ResponseEntity<RestData<?>> getTask(@PathVariable Long taskId) {
        return VsResponseUtil.success(learningTaskService.getTask(taskId));
    }

    @PutMapping("/{taskId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Hủy nhiệm vụ")
    public ResponseEntity<RestData<?>> cancelTask(@PathVariable Long taskId) {
        learningTaskService.cancelTask(taskId);
        return VsResponseUtil.success("Đã hủy nhiệm vụ");
    }

    @GetMapping("/{taskId}/progress")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Lấy tiến độ của tất cả sinh viên cho nhiệm vụ")
    public ResponseEntity<RestData<?>> getProgressByTask(@PathVariable Long taskId) {
        return VsResponseUtil.success(learningTaskService.getProgressByTask(taskId));
    }

    @PutMapping("/{taskId}/progress")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Cập nhật tiến độ nhiệm vụ")
    public ResponseEntity<RestData<?>> updateProgress(
            @PathVariable Long taskId,
            @RequestParam TaskProgressStatus status,
            @RequestParam(required = false) String note,
            @AuthenticationPrincipal UserDetails userDetails) {
        return VsResponseUtil.success(learningTaskService.updateProgress(taskId, status, note, userDetails.getUsername()));
    }

    @GetMapping("/my-progress")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy tiến độ của tôi")
    public ResponseEntity<RestData<?>> getMyProgress(
            @AuthenticationPrincipal UserDetails userDetails) {
        return VsResponseUtil.success(learningTaskService.getMyProgress(userDetails.getUsername()));
    }
}
