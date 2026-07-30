package com.example.be.web.service;

import com.example.be.web.doman.dto.request.attendance.AttendanceAppealRequestDto;
import com.example.be.web.doman.dto.request.attendance.AttendancePolicyRequestDto;
import com.example.be.web.doman.dto.request.attendance.ReviewAppealRequestDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceAppealResponseDto;
import com.example.be.web.doman.dto.response.attendance.AttendancePolicyResponseDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceWarningResponseDto;
import com.example.be.web.doman.model.AppealStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface quản lý chính sách điểm danh, cảnh báo nghỉ học và giải trình.
 *
 * @author auto-generated
 */
public interface AttendanceAdvancedService {

    // ======================== POLICY ========================

    /**
     * Tạo chính sách điểm danh cho lớp.
     *
     * @param requestDto dữ liệu chính sách
     * @return chính sách vừa tạo
     */
    AttendancePolicyResponseDto createPolicy(AttendancePolicyRequestDto requestDto);

    /**
     * Cập nhật chính sách điểm danh.
     *
     * @param policyId   mã chính sách
     * @param requestDto dữ liệu cập nhật
     * @return chính sách đã cập nhật
     */
    AttendancePolicyResponseDto updatePolicy(Long policyId, AttendancePolicyRequestDto requestDto);

    /**
     * Lấy chính sách theo ID.
     *
     * @param policyId mã chính sách
     * @return thông tin chính sách
     */
    AttendancePolicyResponseDto getPolicyById(Long policyId);

    /**
     * Lấy chính sách theo lớp học.
     *
     * @param classId mã lớp
     * @return thông tin chính sách
     */
    AttendancePolicyResponseDto getPolicyByClassId(Long classId);

    /**
     * Xóa chính sách.
     *
     * @param policyId mã chính sách
     */
    void deletePolicy(Long policyId);

    // ======================== WARNING ========================

    /**
     * Lấy cảnh báo theo lớp (phân trang).
     *
     * @param classId  mã lớp
     * @param pageable phân trang
     * @return trang cảnh báo
     */
    Page<AttendanceWarningResponseDto> getWarningsByClass(Long classId, Pageable pageable);

    /**
     * Lấy cảnh báo theo sinh viên (phân trang).
     *
     * @param studentId mã sinh viên
     * @param pageable  phân trang
     * @return trang cảnh báo
     */
    Page<AttendanceWarningResponseDto> getWarningsByStudent(Long studentId, Pageable pageable);

    /**
     * Lấy cảnh báo của sinh viên hiện tại.
     *
     * @param pageable phân trang
     * @return trang cảnh báo
     */
    Page<AttendanceWarningResponseDto> getMyWarnings(Pageable pageable);

    /**
     * Lấy chi tiết cảnh báo.
     *
     * @param warningId mã cảnh báo
     * @return thông tin cảnh báo
     */
    AttendanceWarningResponseDto getWarningById(Long warningId);

    AttendanceWarningResponseDto createWarning(com.example.be.web.doman.dto.request.attendance.AttendanceWarningRequestDto requestDto);

    AttendanceWarningResponseDto updateWarning(Long warningId, com.example.be.web.doman.dto.request.attendance.AttendanceWarningRequestDto requestDto);

    void deleteWarning(Long warningId);

    // ======================== APPEAL ========================

    /**
     * Sinh viên tạo giải trình.
     *
     * @param requestDto dữ liệu giải trình
     * @return giải trình vừa tạo
     */
    AttendanceAppealResponseDto createAppeal(AttendanceAppealRequestDto requestDto);

    /**
     * Lấy chi tiết giải trình.
     *
     * @param appealId mã giải trình
     * @return thông tin giải trình
     */
    AttendanceAppealResponseDto getAppealById(Long appealId);

    /**
     * Lấy giải trình của sinh viên hiện tại.
     *
     * @param pageable phân trang
     * @return trang giải trình
     */
    Page<AttendanceAppealResponseDto> getMyAppeals(Pageable pageable);

    /**
     * Lấy giải trình theo trạng thái (cho giảng viên duyệt).
     *
     * @param status   trạng thái lọc
     * @param pageable phân trang
     * @return trang giải trình
     */
    Page<AttendanceAppealResponseDto> getAppealsByStatus(AppealStatus status, Pageable pageable);

    /**
     * Cập nhật giải trình (chỉ khi PENDING).
     */
    AttendanceAppealResponseDto updateAppeal(Long appealId, AttendanceAppealRequestDto requestDto);

    /**
     * Hủy/xóa giải trình (chỉ khi PENDING).
     */
    void deleteAppeal(Long appealId);

    /**
     * Tìm kiếm giải trình chung (Admin/Giảng viên).
     */
    Page<AttendanceAppealResponseDto> searchAppeals(String keyword, AppealStatus status, Pageable pageable);

    /**
     * Giảng viên xét duyệt giải trình.
     *
     * @param appealId   mã giải trình
     * @param requestDto dữ liệu duyệt
     * @return giải trình đã duyệt
     */
    AttendanceAppealResponseDto reviewAppeal(Long appealId, ReviewAppealRequestDto requestDto);
}
