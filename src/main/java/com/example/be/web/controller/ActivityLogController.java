package com.example.be.web.controller;

import com.example.be.web.base.RestData;
import com.example.be.web.base.VsResponseUtil;
import com.example.be.web.doman.model.ActivityAction;
import com.example.be.web.doman.model.TargetType;
import com.example.be.web.service.ActivityLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activity-logs")
@RequiredArgsConstructor
@Tag(name = "Activity Log", description = "API nhật ký hoạt động hệ thống")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Lấy tất cả nhật ký hoạt động")
    public ResponseEntity<RestData<?>> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return VsResponseUtil.success(activityLogService.getAllLogs(pageable));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Lấy nhật ký theo người dùng")
    public ResponseEntity<RestData<?>> getLogsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return VsResponseUtil.success(activityLogService.getLogsByUser(userId, pageable));
    }

    @GetMapping("/action/{action}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Lấy nhật ký theo loại hành động")
    public ResponseEntity<RestData<?>> getLogsByAction(
            @PathVariable ActivityAction action,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return VsResponseUtil.success(activityLogService.getLogsByAction(action, pageable));
    }

    @GetMapping("/target")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Lấy nhật ký theo đối tượng")
    public ResponseEntity<RestData<?>> getLogsByTarget(
            @RequestParam TargetType targetType,
            @RequestParam Long targetId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return VsResponseUtil.success(activityLogService.getLogsByTarget(targetType, targetId, pageable));
    }
}
