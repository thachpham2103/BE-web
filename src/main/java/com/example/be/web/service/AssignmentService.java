package com.example.be.web.service;

import com.example.be.web.doman.dto.request.assignment.AssignmentRequestDto;
import com.example.be.web.doman.dto.request.assignment.AssignmentSubmissionRequestDto;
import com.example.be.web.doman.dto.request.assignment.GradeSubmissionRequestDto;
import com.example.be.web.doman.dto.response.assignment.AssignmentResponseDto;
import com.example.be.web.doman.dto.response.assignment.AssignmentSubmissionResponseDto;
import com.example.be.web.doman.model.AssignmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface quản lý bài tập ({@code Assignment}) và bài nộp ({@code AssignmentSubmission}).
 *
 * <p>Cung cấp các thao tác CRUD, tìm kiếm, phân trang, chấm điểm
 * và soft-delete cho module Assignment.</p>
 *
 * @author auto-generated
 */
public interface AssignmentService {

    // ======================== ASSIGNMENT ========================

    /**
     * Tạo mới bài tập.
     *
     * @param requestDto dữ liệu bài tập
     * @return bài tập vừa tạo
     */
    AssignmentResponseDto createAssignment(AssignmentRequestDto requestDto);

    /**
     * Cập nhật bài tập.
     *
     * @param assignmentId mã bài tập
     * @param requestDto   dữ liệu cập nhật
     * @return bài tập đã cập nhật
     */
    AssignmentResponseDto updateAssignment(Long assignmentId, AssignmentRequestDto requestDto);

    /**
     * Xóa mềm bài tập (chuyển status sang DELETED).
     *
     * @param assignmentId mã bài tập
     */
    void deleteAssignment(Long assignmentId);

    /**
     * Lấy chi tiết bài tập theo ID.
     *
     * @param assignmentId mã bài tập
     * @return thông tin bài tập
     */
    AssignmentResponseDto getAssignmentById(Long assignmentId);

    /**
     * Tìm kiếm bài tập với các tiêu chí động và phân trang.
     *
     * @param keyword  từ khóa tìm theo tiêu đề
     * @param classId  lọc theo lớp học (nullable)
     * @param status   lọc theo trạng thái (nullable)
     * @param pageable thông tin phân trang
     * @return trang kết quả
     */
    Page<AssignmentResponseDto> searchAssignments(String keyword, Long classId,
                                                   AssignmentStatus status, Pageable pageable);

    /**
     * Lấy danh sách bài tập theo lớp học (phân trang).
     *
     * @param classId  mã lớp học
     * @param pageable thông tin phân trang
     * @return trang kết quả
     */
    Page<AssignmentResponseDto> getAssignmentsByClassId(Long classId, Pageable pageable);

    // ======================== SUBMISSION ========================

    /**
     * Sinh viên nộp bài.
     *
     * @param requestDto dữ liệu bài nộp
     * @return bài nộp vừa tạo
     */
    AssignmentSubmissionResponseDto submitAssignment(AssignmentSubmissionRequestDto requestDto);

    /**
     * Cập nhật bài nộp (chỉ sinh viên sở hữu).
     *
     * @param submissionId mã bài nộp
     * @param requestDto   dữ liệu cập nhật
     * @return bài nộp đã cập nhật
     */
    AssignmentSubmissionResponseDto updateSubmission(Long submissionId, AssignmentSubmissionRequestDto requestDto);

    /**
     * Xóa bài nộp.
     *
     * @param submissionId mã bài nộp
     */
    void deleteSubmission(Long submissionId);

    /**
     * Lấy chi tiết bài nộp.
     *
     * @param submissionId mã bài nộp
     * @return thông tin bài nộp
     */
    AssignmentSubmissionResponseDto getSubmissionById(Long submissionId);

    /**
     * Lấy danh sách bài nộp theo bài tập (phân trang).
     *
     * @param assignmentId mã bài tập
     * @param pageable     thông tin phân trang
     * @return trang kết quả
     */
    Page<AssignmentSubmissionResponseDto> getSubmissionsByAssignment(Long assignmentId, Pageable pageable);

    /**
     * Lấy danh sách bài nộp của sinh viên hiện tại (phân trang).
     *
     * @param pageable thông tin phân trang
     * @return trang kết quả
     */
    Page<AssignmentSubmissionResponseDto> getMySubmissions(Pageable pageable);

    /**
     * Giảng viên chấm điểm bài nộp.
     *
     * @param submissionId mã bài nộp
     * @param requestDto   dữ liệu chấm điểm
     * @return bài nộp đã chấm
     */
    AssignmentSubmissionResponseDto gradeSubmission(Long submissionId, GradeSubmissionRequestDto requestDto);
}
