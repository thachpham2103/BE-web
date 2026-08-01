package com.example.be.web.controller;

import com.example.be.web.base.RestData;
import com.example.be.web.base.VsResponseUtil;
import com.example.be.web.doman.dto.request.attendance.AttendanceAppealRequestDto;
import com.example.be.web.doman.dto.request.attendance.AttendancePolicyRequestDto;
import com.example.be.web.doman.dto.request.attendance.ReviewAppealRequestDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceAppealResponseDto;
import com.example.be.web.doman.dto.response.attendance.AttendancePolicyResponseDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceWarningResponseDto;
import com.example.be.web.doman.model.AppealStatus;
import com.example.be.web.service.AttendanceAdvancedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attendance-advanced")
@RequiredArgsConstructor
@Tag(name = "Attendance Advanced", description = "API điểm danh, cảnh báo, giải trình")
public class AttendanceAdvancedController {

    private final AttendanceAdvancedService service;

    // ======================== POLICY ========================

    /**
     * Tạo chính sách điểm danh cho lớp.
     */
    @PostMapping("/policies")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Tạo điểm danh", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> createPolicy(
            @Valid @RequestBody AttendancePolicyRequestDto requestDto) {
        AttendancePolicyResponseDto result = service.createPolicy(requestDto);
        return VsResponseUtil.success(HttpStatus.CREATED, result);
    }

    /**
     * Cập nhật chính sách điểm danh.
     */
    @PutMapping("/policies/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Cập nhật điểm danh", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> updatePolicy(
            @PathVariable Long id,
            @Valid @RequestBody AttendancePolicyRequestDto requestDto) {
        AttendancePolicyResponseDto result = service.updatePolicy(id, requestDto);
        return VsResponseUtil.success(result);
    }

    /**
     * Lấy chính sách theo ID.
     */
    @GetMapping("/policies/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Lấy danh sách theo ID", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> getPolicyById(@PathVariable Long id) {
        return VsResponseUtil.success(service.getPolicyById(id));
    }

    /**
     * Lấy chính sách theo lớp học.
     */
    @GetMapping("/policies/class/{classId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy policy theo lớp", description = "ALL")
    public ResponseEntity<RestData<?>> getPolicyByClass(@PathVariable Long classId) {
        return VsResponseUtil.success(service.getPolicyByClassId(classId));
    }

    /**
     * Xóa chính sách điểm danh.
     */
    @DeleteMapping("/policies/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Xóa Policy điểm danh", description = "ADMIN")
    public ResponseEntity<RestData<?>> deletePolicy(@PathVariable Long id) {
        service.deletePolicy(id);
        return VsResponseUtil.success("Đã xóa chính sách điểm danh");
    }

    // ======================== WARNING ========================

    /**
     * Lấy cảnh báo theo lớp (phân trang).
     */
    @GetMapping("/warnings/class/{classId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Lấy cảnh báo theo lớp", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> getWarningsByClass(
            @PathVariable Long classId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<AttendanceWarningResponseDto> result = service.getWarningsByClass(classId, pageable);
        return VsResponseUtil.success(result);
    }

    /**
     * Lấy cảnh báo theo sinh viên (phân trang).
     */
    @GetMapping("/warnings/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Lấy cảnh báo theo sinh viên", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> getWarningsByStudent(
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<AttendanceWarningResponseDto> result = service.getWarningsByStudent(studentId, pageable);
        return VsResponseUtil.success(result);
    }

    /**
     * Lấy cảnh báo của sinh viên hiện tại.
     */
    @GetMapping("/warnings/me")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Sinh viên xem cảnh báo của mình")
    public ResponseEntity<RestData<?>> getMyWarnings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return VsResponseUtil.success(service.getMyWarnings(pageable));
    }

    /**
     * Lấy chi tiết cảnh báo.
     */
    @GetMapping("/warnings/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy chi tiết cảnh báo", description = "ALL")
    public ResponseEntity<RestData<?>> getWarningById(@PathVariable Long id) {
        return VsResponseUtil.success(service.getWarningById(id));
    }

    @PostMapping("/warnings")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Tạo cảnh báo điểm danh", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> createWarning(
            @Valid @RequestBody com.example.be.web.doman.dto.request.attendance.AttendanceWarningRequestDto requestDto) {
        return VsResponseUtil.success(HttpStatus.CREATED, service.createWarning(requestDto));
    }

    @PutMapping("/warnings/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Cập nhật cảnh báo", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> updateWarning(
            @PathVariable Long id,
            @Valid @RequestBody com.example.be.web.doman.dto.request.attendance.AttendanceWarningRequestDto requestDto) {
        return VsResponseUtil.success(service.updateWarning(id, requestDto));
    }

    @PutMapping("/warnings/{id}/acknowledge")
    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "Xác nhận đã đọc cảnh báo", description = "USER")
    public ResponseEntity<RestData<?>> acknowledgeWarning(@PathVariable Long id) {
        return VsResponseUtil.success(service.acknowledgeWarning(id));
    }

    @DeleteMapping("/warnings/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Xóa cảnh báo", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> deleteWarning(@PathVariable Long id) {
        service.deleteWarning(id);
        return VsResponseUtil.success("Đã xóa cảnh báo");
    }

    // ======================== APPEAL ========================

    /**
     * Sinh viên tạo giải trình điểm danh.
     */
    @PostMapping("/appeals")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Tạo giải trình điểm danh", description = "Sinh viên tạo giải trình")
    public ResponseEntity<RestData<?>> createAppeal(
            @Valid @RequestBody AttendanceAppealRequestDto requestDto) {
        AttendanceAppealResponseDto result = service.createAppeal(requestDto);
        return VsResponseUtil.success(HttpStatus.CREATED, result);
    }

    /**
     * Lấy chi tiết giải trình.
     */
    @GetMapping("/appeals/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy chi tiết giải trình", description = "ALL")
    public ResponseEntity<RestData<?>> getAppealById(@PathVariable Long id) {
        return VsResponseUtil.success(service.getAppealById(id));
    }

    /**
     * Lấy giải trình của sinh viên hiện tại.
     */
    @GetMapping("/appeals/me")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Sinh viên xem giải trình")
    public ResponseEntity<RestData<?>> getMyAppeals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return VsResponseUtil.success(service.getMyAppeals(pageable));
    }

    /**
     * Lấy giải trình theo trạng thái (cho giảng viên duyệt).
     */
    @GetMapping("/appeals/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Lấy giải trình theo trạng thái", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> getAppealsByStatus(
            @PathVariable AppealStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return VsResponseUtil.success(service.getAppealsByStatus(status, pageable));
    }

    /**
     * Giảng viên xét duyệt giải trình.
     */
    @PutMapping("/appeals/{id}/review")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Xét duyệt giải trình", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> reviewAppeal(
            @PathVariable Long id,
            @Valid @RequestBody ReviewAppealRequestDto requestDto) {
        AttendanceAppealResponseDto result = service.reviewAppeal(id, requestDto);
        return VsResponseUtil.success(result);
    }

    @PutMapping("/appeals/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Cập nhật giải trình", description = "Sinh viên cập nhật khi PENDING")
    public ResponseEntity<RestData<?>> updateAppeal(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceAppealRequestDto requestDto) {
        AttendanceAppealResponseDto result = service.updateAppeal(id, requestDto);
        return VsResponseUtil.success(result);
    }

    @DeleteMapping("/appeals/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Xóa giải trình", description = "Sinh viên xóa khi PENDING")
    public ResponseEntity<RestData<?>> deleteAppeal(@PathVariable Long id) {
        service.deleteAppeal(id);
        return VsResponseUtil.success("Đã xóa giải trình thành công");
    }

    @GetMapping("/appeals/search")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Tìm kiếm giải trình", description = "Admin/Giảng viên tìm kiếm")
    public ResponseEntity<RestData<?>> searchAppeals(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) AppealStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return VsResponseUtil.success(service.searchAppeals(keyword, status, pageable));
    }
}
