package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.assignment.AssignmentRequestDto;
import com.example.be.web.doman.dto.request.assignment.AssignmentSubmissionRequestDto;
import com.example.be.web.doman.dto.request.assignment.GradeSubmissionRequestDto;
import com.example.be.web.doman.dto.response.assignment.AssignmentResponseDto;
import com.example.be.web.doman.dto.response.assignment.AssignmentSubmissionResponseDto;
import com.example.be.web.doman.entity.Assignment;
import com.example.be.web.doman.entity.AssignmentSubmission;
import com.example.be.web.doman.entity.ClassRoom;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.mapper.AssignmentMapper;
import com.example.be.web.doman.mapper.AssignmentSubmissionMapper;
import com.example.be.web.doman.model.AssignmentStatus;
import com.example.be.web.doman.model.SubmissionAssignmentStatus;
import com.example.be.web.exception.extended.BadRequestException;
import com.example.be.web.exception.extended.ForbiddenException;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.AssignmentRepository;
import com.example.be.web.repository.AssignmentSubmissionRepository;
import com.example.be.web.repository.ClassRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.security.UserPrincipal;
import com.example.be.web.service.AssignmentService;
import com.example.be.web.specification.AssignmentSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Triển khai {@link AssignmentService}.
 *
 * <p>Xử lý toàn bộ business logic cho module Assignment bao gồm:
 * CRUD bài tập, tìm kiếm động, phân trang, nộp bài và chấm điểm.</p>
 *
 * @author auto-generated
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final AssignmentMapper assignmentMapper;
    private final AssignmentSubmissionMapper submissionMapper;

    // ======================== ASSIGNMENT ========================

    /**
     * {@inheritDoc}
     *
     * <p>Lấy user hiện tại từ SecurityContext làm người tạo.
     * Kiểm tra lớp học tồn tại trước khi tạo.</p>
     */
    @Override
    public AssignmentResponseDto createAssignment(AssignmentRequestDto requestDto) {
        log.info("Tạo bài tập mới cho lớp: {}", requestDto.getClassId());

        User currentUser = getCurrentUser();
        ClassRoom classRoom = classRepository.findById(requestDto.getClassId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.ClassRoom.CLASS_NOT_FOUND,
                        new String[]{requestDto.getClassId().toString()}));

        Assignment assignment = assignmentMapper.toEntity(requestDto);
        assignment.setClassRoom(classRoom);
        assignment.setCreatedBy(currentUser);

        if (assignment.getStatus() == null) {
            assignment.setStatus(AssignmentStatus.DRAFT);
        }

        Assignment saved = assignmentRepository.save(assignment);
        log.info("Đã tạo bài tập ID: {}", saved.getAssignmentId());

        AssignmentResponseDto response = assignmentMapper.toResponse(saved);
        response.setSubmissionCount(0L);
        return response;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Chỉ cho phép cập nhật bài tập chưa bị xóa.
     * Nếu thay đổi classId thì kiểm tra lớp mới tồn tại.</p>
     */
    @Override
    public AssignmentResponseDto updateAssignment(Long assignmentId, AssignmentRequestDto requestDto) {
        log.info("Cập nhật bài tập ID: {}", assignmentId);

        Assignment existing = findActiveAssignment(assignmentId);

        // Nếu đổi lớp học
        if (requestDto.getClassId() != null
                && !requestDto.getClassId().equals(existing.getClassRoom().getClassId())) {
            ClassRoom newClass = classRepository.findById(requestDto.getClassId())
                    .orElseThrow(() -> new NotFoundException(
                            ErrorMessage.ClassRoom.CLASS_NOT_FOUND,
                            new String[]{requestDto.getClassId().toString()}));
            existing.setClassRoom(newClass);
        }

        assignmentMapper.updateEntityFromDto(requestDto, existing);
        Assignment updated = assignmentRepository.save(existing);

        log.info("Đã cập nhật bài tập ID: {}", updated.getAssignmentId());
        return buildAssignmentResponse(updated);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Thực hiện soft-delete: chuyển status sang {@link AssignmentStatus#DELETED}.</p>
     */
    @Override
    public void deleteAssignment(Long assignmentId) {
        log.info("Xóa mềm bài tập ID: {}", assignmentId);

        Assignment assignment = findActiveAssignment(assignmentId);
        assignment.setStatus(AssignmentStatus.DELETED);
        assignmentRepository.save(assignment);

        log.info("Đã xóa mềm bài tập ID: {}", assignmentId);
    }

    /** {@inheritDoc} */
    @Override
    public AssignmentResponseDto getAssignmentById(Long assignmentId) {
        Assignment assignment = findActiveAssignment(assignmentId);
        return buildAssignmentResponse(assignment);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Sử dụng {@link AssignmentSpecification} để xây dựng truy vấn động
     * dựa trên các tiêu chí: keyword, classId, status. Tự động loại trừ bài đã xóa.</p>
     */
    @Override
    public Page<AssignmentResponseDto> searchAssignments(String keyword, Long classId,
                                                          AssignmentStatus status, Pageable pageable) {
        log.info("Tìm kiếm bài tập – keyword: {}, classId: {}, status: {}", keyword, classId, status);

        Specification<Assignment> spec = AssignmentSpecification.notDeleted();

        if (keyword != null && !keyword.isBlank()) {
            spec = spec.and(AssignmentSpecification.titleContains(keyword));
        }
        if (classId != null) {
            spec = spec.and(AssignmentSpecification.belongsToClass(classId));
        }
        if (status != null) {
            spec = spec.and(AssignmentSpecification.hasStatus(status));
        }

        return assignmentRepository.findAll(spec, pageable)
                .map(this::buildAssignmentResponse);
    }

    /** {@inheritDoc} */
    @Override
    public Page<AssignmentResponseDto> getAssignmentsByClassId(Long classId, Pageable pageable) {
        log.info("Lấy bài tập theo lớp: {}", classId);

        // Kiểm tra lớp tồn tại
        classRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.ClassRoom.CLASS_NOT_FOUND,
                        new String[]{classId.toString()}));

        return assignmentRepository
                .findByClassRoom_ClassIdAndStatusNot(classId, AssignmentStatus.DELETED, pageable)
                .map(this::buildAssignmentResponse);
    }

    // ======================== SUBMISSION ========================

    /**
     * {@inheritDoc}
     *
     * <p>Kiểm tra: bài tập phải ở trạng thái PUBLISHED, sinh viên chưa nộp.
     * Nếu nộp sau deadline và không cho phép nộp trễ thì báo lỗi.
     * Nếu cho phép nộp trễ thì tự động gán status = LATE.</p>
     */
    @Override
    public AssignmentSubmissionResponseDto submitAssignment(AssignmentSubmissionRequestDto requestDto) {
        User currentUser = getCurrentUser();
        Assignment assignment = findActiveAssignment(requestDto.getAssignmentId());

        log.info("Sinh viên {} nộp bài cho assignment {}", currentUser.getId(), assignment.getAssignmentId());

        // Kiểm tra bài tập đã publish chưa
        if (assignment.getStatus() != AssignmentStatus.PUBLISHED) {
            throw new BadRequestException(ErrorMessage.Assignment.ASSIGNMENT_NOT_PUBLISHED);
        }

        // Kiểm tra đã nộp chưa
        if (submissionRepository.existsByAssignment_AssignmentIdAndStudent_Id(
                assignment.getAssignmentId(), currentUser.getId())) {
            throw new BadRequestException(ErrorMessage.Assignment.ALREADY_SUBMITTED);
        }

        // Kiểm tra deadline
        SubmissionAssignmentStatus submissionStatus = SubmissionAssignmentStatus.SUBMITTED;
        if (assignment.getDeadline() != null && LocalDateTime.now().isAfter(assignment.getDeadline())) {
            if (Boolean.FALSE.equals(assignment.getAllowLateSubmit())) {
                throw new BadRequestException(ErrorMessage.Assignment.DEADLINE_PASSED);
            }
            submissionStatus = SubmissionAssignmentStatus.LATE;
        }

        AssignmentSubmission submission = submissionMapper.toEntity(requestDto);
        submission.setAssignment(assignment);
        submission.setStudent(currentUser);
        submission.setStatus(submissionStatus);

        AssignmentSubmission saved = submissionRepository.save(submission);
        log.info("Đã lưu bài nộp ID: {}", saved.getSubmissionId());

        return submissionMapper.toResponse(saved);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Chỉ sinh viên sở hữu bài nộp và bài chưa được chấm mới được cập nhật.</p>
     */
    @Override
    public AssignmentSubmissionResponseDto updateSubmission(Long submissionId,
                                                            AssignmentSubmissionRequestDto requestDto) {
        User currentUser = getCurrentUser();
        AssignmentSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Assignment.SUBMISSION_NOT_FOUND,
                        new String[]{submissionId.toString()}));

        // Chỉ chủ sở hữu mới được sửa
        if (!submission.getStudent().getId().equals(currentUser.getId())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }

        // Đã chấm rồi thì không sửa được
        if (submission.getStatus() == SubmissionAssignmentStatus.GRADED) {
            throw new BadRequestException(ErrorMessage.Assignment.SUBMISSION_ALREADY_GRADED);
        }

        submission.setContent(requestDto.getContent());
        submission.setFileUrl(requestDto.getFileUrl());

        AssignmentSubmission updated = submissionRepository.save(submission);
        return submissionMapper.toResponse(updated);
    }

    /** {@inheritDoc} */
    @Override
    public void deleteSubmission(Long submissionId) {
        User currentUser = getCurrentUser();
        AssignmentSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Assignment.SUBMISSION_NOT_FOUND,
                        new String[]{submissionId.toString()}));

        if (!submission.getStudent().getId().equals(currentUser.getId())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }

        submissionRepository.delete(submission);
        log.info("Đã xóa bài nộp ID: {}", submissionId);
    }

    /** {@inheritDoc} */
    @Override
    public AssignmentSubmissionResponseDto getSubmissionById(Long submissionId) {
        AssignmentSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Assignment.SUBMISSION_NOT_FOUND,
                        new String[]{submissionId.toString()}));
        return submissionMapper.toResponse(submission);
    }

    /** {@inheritDoc} */
    @Override
    public Page<AssignmentSubmissionResponseDto> getSubmissionsByAssignment(Long assignmentId, Pageable pageable) {
        // Kiểm tra bài tập tồn tại
        findActiveAssignment(assignmentId);
        return submissionRepository.findByAssignment_AssignmentId(assignmentId, pageable)
                .map(submissionMapper::toResponse);
    }

    /** {@inheritDoc} */
    @Override
    public Page<AssignmentSubmissionResponseDto> getMySubmissions(Pageable pageable) {
        User currentUser = getCurrentUser();
        return submissionRepository.findByStudent_Id(currentUser.getId(), pageable)
                .map(submissionMapper::toResponse);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Ghi nhận người chấm và thời gian chấm. Chuyển status sang GRADED.</p>
     */
    @Override
    public AssignmentSubmissionResponseDto gradeSubmission(Long submissionId, GradeSubmissionRequestDto requestDto) {
        User currentUser = getCurrentUser();
        AssignmentSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Assignment.SUBMISSION_NOT_FOUND,
                        new String[]{submissionId.toString()}));

        // Validate điểm không vượt maxScore
        Assignment assignment = submission.getAssignment();
        if (assignment.getMaxScore() != null && requestDto.getScore() > assignment.getMaxScore()) {
            throw new BadRequestException(ErrorMessage.Assignment.SCORE_EXCEEDS_MAX);
        }

        submission.setScore(requestDto.getScore());
        submission.setTeacherComment(requestDto.getTeacherComment());
        submission.setGradedBy(currentUser);
        submission.setGradedAt(LocalDateTime.now());
        submission.setStatus(SubmissionAssignmentStatus.GRADED);

        AssignmentSubmission graded = submissionRepository.save(submission);
        log.info("Đã chấm điểm bài nộp ID: {} – điểm: {}", submissionId, requestDto.getScore());

        return submissionMapper.toResponse(graded);
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

    /**
     * Tìm bài tập chưa bị xóa. Ném {@link NotFoundException} nếu không tìm thấy.
     *
     * @param assignmentId mã bài tập
     * @return Assignment entity
     */
    private Assignment findActiveAssignment(Long assignmentId) {
        return assignmentRepository
                .findByAssignmentIdAndStatusNot(assignmentId, AssignmentStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Assignment.ASSIGNMENT_NOT_FOUND,
                        new String[]{assignmentId.toString()}));
    }

    /**
     * Build response DTO kèm submissionCount.
     *
     * @param assignment entity
     * @return response DTO đầy đủ
     */
    private AssignmentResponseDto buildAssignmentResponse(Assignment assignment) {
        AssignmentResponseDto dto = assignmentMapper.toResponse(assignment);
        dto.setSubmissionCount(submissionRepository.countByAssignment_AssignmentId(assignment.getAssignmentId()));
        return dto;
    }
}
