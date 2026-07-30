package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.attendance.AttendanceAppealRequestDto;
import com.example.be.web.doman.dto.request.attendance.AttendancePolicyRequestDto;
import com.example.be.web.doman.dto.request.attendance.ReviewAppealRequestDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceAppealResponseDto;
import com.example.be.web.doman.dto.response.attendance.AttendancePolicyResponseDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceWarningResponseDto;
import com.example.be.web.doman.entity.*;
import com.example.be.web.doman.mapper.AttendanceAppealMapper;
import com.example.be.web.doman.mapper.AttendancePolicyMapper;
import com.example.be.web.doman.mapper.AttendanceWarningMapper;
import com.example.be.web.doman.model.AppealStatus;
import com.example.be.web.exception.extended.BadRequestException;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.*;
import com.example.be.web.security.UserPrincipal;
import com.example.be.web.service.AttendanceAdvancedService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Triển khai {@link AttendanceAdvancedService}.
 *
 * <p>Xử lý business logic cho chính sách điểm danh, cảnh báo nghỉ học và giải trình.</p>
 *
 * @author auto-generated
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AttendanceAdvancedServiceImpl implements AttendanceAdvancedService {

    private final AttendancePolicyRepository policyRepository;
    private final AttendanceWarningRepository warningRepository;
    private final AttendanceAppealRepository appealRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final AttendanceSessionRepository sessionRepository;
    private final AttendanceRecordRepository recordRepository;
    private final AttendancePolicyMapper policyMapper;
    private final AttendanceWarningMapper warningMapper;
    private final AttendanceAppealMapper appealMapper;

    // ======================== POLICY ========================

    /** {@inheritDoc} */
    @Override
    public AttendancePolicyResponseDto createPolicy(AttendancePolicyRequestDto requestDto) {
        log.info("Tạo chính sách điểm danh cho lớp: {}", requestDto.getClassId());

        // Kiểm tra lớp đã có chính sách chưa
        if (policyRepository.existsByClassRoom_ClassId(requestDto.getClassId())) {
            throw new BadRequestException(ErrorMessage.AttendanceAdvanced.POLICY_ALREADY_EXISTS);
        }

        ClassRoom classRoom = classRepository.findById(requestDto.getClassId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.ClassRoom.CLASS_NOT_FOUND,
                        new String[]{requestDto.getClassId().toString()}));

        // Validate: banThreshold phải > warningThreshold
        if (requestDto.getBanThreshold() <= requestDto.getWarningThreshold()) {
            throw new BadRequestException(ErrorMessage.AttendanceAdvanced.BAN_MUST_GREATER_THAN_WARNING);
        }

        AttendancePolicy policy = policyMapper.toEntity(requestDto);
        policy.setClassRoom(classRoom);

        AttendancePolicy saved = policyRepository.save(policy);
        log.info("Đã tạo chính sách ID: {}", saved.getPolicyId());

        return policyMapper.toResponse(saved);
    }

    /** {@inheritDoc} */
    @Override
    public AttendancePolicyResponseDto updatePolicy(Long policyId, AttendancePolicyRequestDto requestDto) {
        log.info("Cập nhật chính sách ID: {}", policyId);

        AttendancePolicy existing = policyRepository.findById(policyId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceAdvanced.POLICY_NOT_FOUND,
                        new String[]{policyId.toString()}));

        if (requestDto.getBanThreshold() <= requestDto.getWarningThreshold()) {
            throw new BadRequestException(ErrorMessage.AttendanceAdvanced.BAN_MUST_GREATER_THAN_WARNING);
        }

        policyMapper.updateEntityFromDto(requestDto, existing);
        AttendancePolicy updated = policyRepository.save(existing);

        return policyMapper.toResponse(updated);
    }

    /** {@inheritDoc} */
    @Override
    public AttendancePolicyResponseDto getPolicyById(Long policyId) {
        AttendancePolicy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceAdvanced.POLICY_NOT_FOUND,
                        new String[]{policyId.toString()}));
        return policyMapper.toResponse(policy);
    }

    /** {@inheritDoc} */
    @Override
    public AttendancePolicyResponseDto getPolicyByClassId(Long classId) {
        AttendancePolicy policy = policyRepository.findByClassRoom_ClassId(classId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceAdvanced.POLICY_NOT_FOUND,
                        new String[]{classId.toString()}));
        return policyMapper.toResponse(policy);
    }

    /** {@inheritDoc} */
    @Override
    public void deletePolicy(Long policyId) {
        AttendancePolicy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceAdvanced.POLICY_NOT_FOUND,
                        new String[]{policyId.toString()}));
        policyRepository.delete(policy);
        log.info("Đã xóa chính sách ID: {}", policyId);
    }

    // ======================== WARNING ========================

    /** {@inheritDoc} */
    @Override
    public Page<AttendanceWarningResponseDto> getWarningsByClass(Long classId, Pageable pageable) {
        return warningRepository.findByClassRoom_ClassId(classId, pageable)
                .map(warningMapper::toResponse);
    }

    /** {@inheritDoc} */
    @Override
    public Page<AttendanceWarningResponseDto> getWarningsByStudent(Long studentId, Pageable pageable) {
        return warningRepository.findByStudent_Id(studentId, pageable)
                .map(warningMapper::toResponse);
    }

    /** {@inheritDoc} */
    @Override
    public Page<AttendanceWarningResponseDto> getMyWarnings(Pageable pageable) {
        User currentUser = getCurrentUser();
        return warningRepository.findByStudent_Id(currentUser.getId(), pageable)
                .map(warningMapper::toResponse);
    }

    /** {@inheritDoc} */
    @Override
    public AttendanceWarningResponseDto getWarningById(Long warningId) {
        AttendanceWarning warning = warningRepository.findById(warningId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceAdvanced.WARNING_NOT_FOUND,
                        new String[]{warningId.toString()}));
        return warningMapper.toResponse(warning);
    }

    @Override
    @Transactional
    public AttendanceWarningResponseDto createWarning(com.example.be.web.doman.dto.request.attendance.AttendanceWarningRequestDto requestDto) {
        User student = userRepository.findById(requestDto.getStudentId())
                .orElseThrow(() -> new NotFoundException("Student not found"));
        ClassRoom classRoom = classRepository.findById(requestDto.getClassId())
                .orElseThrow(() -> new NotFoundException("Class not found"));

        List<AttendanceRecord> userRecords = recordRepository.findByUser_Id(requestDto.getStudentId()).stream()
                .filter(r -> r.getAttendanceSession().getClassRoom().getClassId().equals(requestDto.getClassId()))
                .toList();

        int totalSession = userRecords.size();
        int absentCount = (int) userRecords.stream()
                .filter(r -> r.getRecordStatus() == com.example.be.web.doman.model.RecordStatus.ABSENT)
                .count();
        double absentRate = totalSession == 0 ? 0 : ((double) absentCount / totalSession) * 100;

        AttendanceWarning warning = AttendanceWarning.builder()
                .student(student)
                .classRoom(classRoom)
                .absentCount(absentCount)
                .totalSession(totalSession)
                .absentRate(absentRate)
                .warningLevel(requestDto.getWarningLevel())
                .message(requestDto.getMessage())
                .status(AppealStatus.PENDING)
                .build();

        warning = warningRepository.save(warning);
        return warningMapper.toResponse(warning);
    }

    @Override
    @Transactional
    public AttendanceWarningResponseDto updateWarning(Long warningId, com.example.be.web.doman.dto.request.attendance.AttendanceWarningRequestDto requestDto) {
        AttendanceWarning warning = warningRepository.findById(warningId)
                .orElseThrow(() -> new NotFoundException("Warning not found"));

        warning.setWarningLevel(requestDto.getWarningLevel());
        warning.setMessage(requestDto.getMessage());
        warning = warningRepository.save(warning);
        return warningMapper.toResponse(warning);
    }

    @Override
    @Transactional
    public void deleteWarning(Long warningId) {
        AttendanceWarning warning = warningRepository.findById(warningId)
                .orElseThrow(() -> new NotFoundException("Warning not found"));
        warningRepository.delete(warning);
    }

    // ======================== APPEAL ========================

    /**
     * {@inheritDoc}
     *
     * <p>Kiểm tra: chưa giải trình cho record này, session và record phải tồn tại.</p>
     */
    @Override
    public AttendanceAppealResponseDto createAppeal(AttendanceAppealRequestDto requestDto) {
        User currentUser = getCurrentUser();
        log.info("Sinh viên {} tạo giải trình cho record {}", currentUser.getId(), requestDto.getRecordId());

        // Kiểm tra đã giải trình chưa
        if (appealRepository.existsByStudent_IdAndAttendanceRecord_RecordId(
                currentUser.getId(), requestDto.getRecordId())) {
            throw new BadRequestException(ErrorMessage.AttendanceAdvanced.APPEAL_ALREADY_EXISTS);
        }

        AttendanceSession session = sessionRepository.findById(requestDto.getSessionId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceSession.SESSION_NOT_FOUND,
                        new String[]{requestDto.getSessionId().toString()}));

        AttendanceRecord record = recordRepository.findById(requestDto.getRecordId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceRecord.RECORD_NOT_FOUND,
                        new String[]{requestDto.getRecordId().toString()}));

        AttendanceAppeal appeal = AttendanceAppeal.builder()
                .student(currentUser)
                .attendanceSession(session)
                .attendanceRecord(record)
                .reason(requestDto.getReason())
                .proofImageUrl(requestDto.getProofImageUrl())
                .status(AppealStatus.PENDING)
                .build();

        AttendanceAppeal saved = appealRepository.save(appeal);
        log.info("Đã tạo giải trình ID: {}", saved.getAppealId());

        return appealMapper.toResponse(saved);
    }

    /** {@inheritDoc} */
    @Override
    public AttendanceAppealResponseDto getAppealById(Long appealId) {
        AttendanceAppeal appeal = appealRepository.findById(appealId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceAdvanced.APPEAL_NOT_FOUND,
                        new String[]{appealId.toString()}));
        return appealMapper.toResponse(appeal);
    }

    /** {@inheritDoc} */
    @Override
    public Page<AttendanceAppealResponseDto> getMyAppeals(Pageable pageable) {
        User currentUser = getCurrentUser();
        return appealRepository.findByStudent_Id(currentUser.getId(), pageable)
                .map(appealMapper::toResponse);
    }

    /** {@inheritDoc} */
    @Override
    public Page<AttendanceAppealResponseDto> getAppealsByStatus(AppealStatus status, Pageable pageable) {
        return appealRepository.findByStatus(status, pageable)
                .map(appealMapper::toResponse);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Ghi nhận người duyệt và thời gian duyệt. Chỉ duyệt giải trình đang PENDING.</p>
     */
    @Override
    public AttendanceAppealResponseDto reviewAppeal(Long appealId, ReviewAppealRequestDto requestDto) {
        User currentUser = getCurrentUser();
        AttendanceAppeal appeal = appealRepository.findById(appealId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceAdvanced.APPEAL_NOT_FOUND,
                        new String[]{appealId.toString()}));

        if (appeal.getStatus() != AppealStatus.PENDING) {
            throw new BadRequestException(ErrorMessage.AttendanceAdvanced.APPEAL_ALREADY_REVIEWED);
        }

        appeal.setStatus(requestDto.getStatus());
        appeal.setTeacherNote(requestDto.getTeacherNote());
        appeal.setReviewedBy(currentUser);
        appeal.setReviewedAt(LocalDateTime.now());

        AttendanceAppeal reviewed = appealRepository.save(appeal);
        log.info("Đã duyệt giải trình ID: {} → {}", appealId, requestDto.getStatus());

        return appealMapper.toResponse(reviewed);
    }

    @Override
    public AttendanceAppealResponseDto updateAppeal(Long appealId, AttendanceAppealRequestDto requestDto) {
        User currentUser = getCurrentUser();

        AttendanceAppeal appeal = appealRepository.findById(appealId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceAdvanced.APPEAL_NOT_FOUND,
                        new String[]{appealId.toString()}));

        if (!appeal.getStudent().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Bạn không có quyền sửa giải trình này");
        }

        if (appeal.getStatus() != AppealStatus.PENDING) {
            throw new BadRequestException("Chỉ có thể sửa giải trình khi đang chờ duyệt");
        }

        appeal.setReason(requestDto.getReason());

        if (requestDto.getProofImageUrl() != null) {
            appeal.setProofImageUrl(requestDto.getProofImageUrl());
        }

        AttendanceAppeal updated = appealRepository.save(appeal);
        log.info("Sinh viên {} đã cập nhật giải trình ID: {}", currentUser.getUsername(), appealId);

        return appealMapper.toResponse(updated);
    }

    @Override
    public void deleteAppeal(Long appealId) {
        User currentUser = getCurrentUser();
        AttendanceAppeal appeal = appealRepository.findById(appealId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceAdvanced.APPEAL_NOT_FOUND,
                        new String[]{appealId.toString()}));

        if (!appeal.getStudent().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Bạn không có quyền xóa giải trình này");
        }

        if (appeal.getStatus() != AppealStatus.PENDING) {
            throw new BadRequestException("Chỉ có thể xóa giải trình khi đang chờ duyệt");
        }

        appealRepository.delete(appeal);
        log.info("Sinh viên {} đã xóa giải trình ID: {}", currentUser.getUsername(), appealId);
    }

    @Override
    public Page<AttendanceAppealResponseDto> searchAppeals(String keyword, AppealStatus status, Pageable pageable) {
        org.springframework.data.jpa.domain.Specification<AttendanceAppeal> spec = org.springframework.data.jpa.domain.Specification.where(null);

        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = "%" + keyword.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("reason")), kw),
                    cb.like(cb.lower(root.get("student").get("fullName")), kw),
                    cb.like(cb.lower(root.get("student").get("studentId")), kw)
            ));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        Page<AttendanceAppeal> pages = appealRepository.findAll(spec, pageable);
        return pages.map(appealMapper::toResponse);
    }

    // ======================== PRIVATE HELPERS ========================

    /**
     * Lấy User hiện tại từ SecurityContext.
     *
     * @return User đang đăng nhập
     */
    private User getCurrentUser() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userRepository.findById(principal.getId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.User.USER_NOT_FOUND_ID,
                        new String[]{principal.getId().toString()}));
    }
}
