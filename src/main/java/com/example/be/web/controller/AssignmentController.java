package com.example.be.web.controller;

import com.example.be.web.base.RestData;
import com.example.be.web.base.VsResponseUtil;
import com.example.be.web.doman.dto.request.assignment.AssignmentRequestDto;
import com.example.be.web.doman.dto.request.assignment.AssignmentSubmissionRequestDto;
import com.example.be.web.doman.dto.request.assignment.GradeSubmissionRequestDto;
import com.example.be.web.doman.dto.response.assignment.AssignmentResponseDto;
import com.example.be.web.doman.dto.response.assignment.AssignmentSubmissionResponseDto;
import com.example.be.web.doman.model.AssignmentStatus;
import com.example.be.web.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

/**
 * REST Controller quản lý bài tập ({@code Assignment}) và bài nộp ({@code AssignmentSubmission}).
 *
 * <p>Base path: {@code /api/v1/assignments}</p>
 *
 * <p>Phân quyền:
 * <ul>
 *     <li>ADMIN, LEADER: toàn quyền CRUD bài tập, xem & chấm bài nộp.</li>
 *     <li>USER (sinh viên): xem bài tập, nộp bài, xem bài nộp của mình.</li>
 * </ul>
 * </p>
 *
 * @author auto-generated
 */
@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
@Tag(name = "Assignment", description = "API quản lý bài tập và bài nộp")
public class AssignmentController {

    private final AssignmentService assignmentService;

    // ======================== ASSIGNMENT CRUD ========================

    /**
     * Tạo mới bài tập.
     *
     * @param requestDto dữ liệu bài tập
     * @return bài tập vừa tạo
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Tạo bài tập mới", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> createAssignment(
            @Valid @RequestBody AssignmentRequestDto requestDto) {
        AssignmentResponseDto result = assignmentService.createAssignment(requestDto);
        return VsResponseUtil.success(HttpStatus.CREATED, result);
    }

    /**
     * Cập nhật bài tập.
     *
     * @param id         mã bài tập
     * @param requestDto dữ liệu cập nhật
     * @return bài tập đã cập nhật
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Cập nhật bài tập", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentRequestDto requestDto) {
        AssignmentResponseDto result = assignmentService.updateAssignment(id, requestDto);
        return VsResponseUtil.success(result);
    }

    /**
     * Xóa mềm bài tập.
     *
     * @param id mã bài tập
     * @return 200 OK
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Xóa mềm bài tập", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return VsResponseUtil.success("Đã xóa bài tập thành công");
    }

    /**
     * Lấy chi tiết bài tập theo ID.
     *
     * @param id mã bài tập
     * @return thông tin bài tập
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy chi tiết bài tập theo ID", description = "ADMIN / LEADER / USER")
    public ResponseEntity<RestData<?>> getAssignmentById(@PathVariable Long id) {
        AssignmentResponseDto result = assignmentService.getAssignmentById(id);
        return VsResponseUtil.success(result);
    }

    /**
     * Tìm kiếm bài tập với các tiêu chí động và phân trang.
     *
     * @param keyword từ khóa tiêu đề (tùy chọn)
     * @param classId lọc theo lớp (tùy chọn)
     * @param status  lọc theo trạng thái (tùy chọn)
     * @param page    số trang (mặc định 0)
     * @param size    kích thước trang (mặc định 10)
     * @param sortBy  trường sắp xếp (mặc định createDate)
     * @param sortDir hướng sắp xếp: asc/desc (mặc định desc)
     * @return trang kết quả
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Tìm kiếm bài tập", description = "Tìm kiếm động với phân trang")
    public ResponseEntity<RestData<?>> searchAssignments(
            @Parameter(description = "Từ khóa tìm theo tiêu đề")
            @RequestParam(required = false) String keyword,
            @Parameter(description = "Lọc theo ID lớp học")
            @RequestParam(required = false) Long classId,
            @Parameter(description = "Lọc theo trạng thái")
            @RequestParam(required = false) AssignmentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AssignmentResponseDto> result = assignmentService.searchAssignments(keyword, classId, status, pageable);
        return VsResponseUtil.success(result);
    }

    /**
     * Lấy danh sách bài tập theo lớp học (phân trang).
     *
     * @param classId mã lớp học
     * @param page    số trang
     * @param size    kích thước trang
     * @return trang bài tập
     */
    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy bài tập theo lớp học", description = "ADMIN / LEADER / USER")
    public ResponseEntity<RestData<?>> getAssignmentsByClass(
            @PathVariable Long classId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<AssignmentResponseDto> result = assignmentService.getAssignmentsByClassId(classId, pageable);
        return VsResponseUtil.success(result);
    }

    // ======================== SUBMISSION ========================

    /**
     * Sinh viên nộp bài.
     *
     * @param requestDto dữ liệu bài nộp
     * @return bài nộp vừa tạo
     */
    @PostMapping("/submissions")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Nộp bài tập", description = "Sinh viên nộp bài")
    public ResponseEntity<RestData<?>> submitAssignment(
            @Valid @RequestBody AssignmentSubmissionRequestDto requestDto) {
        AssignmentSubmissionResponseDto result = assignmentService.submitAssignment(requestDto);
        return VsResponseUtil.success(HttpStatus.CREATED, result);
    }

    /**
     * Cập nhật bài nộp (chỉ chủ sở hữu, chưa chấm).
     *
     * @param submissionId mã bài nộp
     * @param requestDto   dữ liệu cập nhật
     * @return bài nộp đã cập nhật
     */
    @PutMapping("/submissions/{submissionId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Cập nhật bài nộp", description = "Chỉ chủ sở hữu, chưa chấm điểm")
    public ResponseEntity<RestData<?>> updateSubmission(
            @PathVariable Long submissionId,
            @Valid @RequestBody AssignmentSubmissionRequestDto requestDto) {
        AssignmentSubmissionResponseDto result = assignmentService.updateSubmission(submissionId, requestDto);
        return VsResponseUtil.success(result);
    }

    /**
     * Xóa bài nộp.
     *
     * @param submissionId mã bài nộp
     * @return 200 OK
     */
    @DeleteMapping("/submissions/{submissionId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Xóa bài nộp", description = "Chỉ chủ sở hữu")
    public ResponseEntity<RestData<?>> deleteSubmission(@PathVariable Long submissionId) {
        assignmentService.deleteSubmission(submissionId);
        return VsResponseUtil.success("Đã xóa bài nộp thành công");
    }

    /**
     * Lấy chi tiết bài nộp.
     *
     * @param submissionId mã bài nộp
     * @return thông tin bài nộp
     */
    @GetMapping("/submissions/{submissionId}")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy chi tiết bài nộp", description = "ADMIN / LEADER / USER")
    public ResponseEntity<RestData<?>> getSubmissionById(@PathVariable Long submissionId) {
        AssignmentSubmissionResponseDto result = assignmentService.getSubmissionById(submissionId);
        return VsResponseUtil.success(result);
    }

    /**
     * Lấy danh sách bài nộp theo bài tập (phân trang).
     *
     * @param assignmentId mã bài tập
     * @param page         số trang
     * @param size         kích thước trang
     * @return trang bài nộp
     */
    @GetMapping("/{assignmentId}/submissions")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Lấy bài nộp theo bài tập", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> getSubmissionsByAssignment(
            @PathVariable Long assignmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("submittedAt").descending());
        Page<AssignmentSubmissionResponseDto> result =
                assignmentService.getSubmissionsByAssignment(assignmentId, pageable);
        return VsResponseUtil.success(result);
    }

    /**
     * Lấy danh sách bài nộp của sinh viên hiện tại.
     *
     * @param page số trang
     * @param size kích thước trang
     * @return trang bài nộp
     */
    @GetMapping("/submissions/me")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER','USER')")
    @Operation(summary = "Lấy bài nộp của tôi", description = "Sinh viên xem bài nộp của mình")
    public ResponseEntity<RestData<?>> getMySubmissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("submittedAt").descending());
        Page<AssignmentSubmissionResponseDto> result = assignmentService.getMySubmissions(pageable);
        return VsResponseUtil.success(result);
    }

    /**
     * Giảng viên chấm điểm bài nộp.
     *
     * @param submissionId mã bài nộp
     * @param requestDto   dữ liệu chấm điểm
     * @return bài nộp đã chấm
     */
    @PutMapping("/submissions/{submissionId}/grade")
    @PreAuthorize("hasAnyRole('ADMIN','LEADER')")
    @Operation(summary = "Chấm điểm bài nộp", description = "ADMIN / LEADER")
    public ResponseEntity<RestData<?>> gradeSubmission(
            @PathVariable Long submissionId,
            @Valid @RequestBody GradeSubmissionRequestDto requestDto) {
        AssignmentSubmissionResponseDto result = assignmentService.gradeSubmission(submissionId, requestDto);
        return VsResponseUtil.success(result);
    }
}
