package com.example.be.web.repository;

import com.example.be.web.doman.entity.AssignmentSubmission;
import com.example.be.web.doman.model.SubmissionAssignmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository thao tác dữ liệu {@link AssignmentSubmission}.
 *
 * @author auto-generated
 */
@Repository
public interface AssignmentSubmissionRepository
        extends JpaRepository<AssignmentSubmission, Long>, JpaSpecificationExecutor<AssignmentSubmission> {

    /**
     * Tìm bài nộp của sinh viên cho bài tập cụ thể.
     *
     * @param assignmentId mã bài tập
     * @param studentId    mã sinh viên
     * @return bài nộp nếu tồn tại
     */
    Optional<AssignmentSubmission> findByAssignment_AssignmentIdAndStudent_Id(Long assignmentId, Long studentId);

    /**
     * Kiểm tra sinh viên đã nộp bài chưa.
     *
     * @param assignmentId mã bài tập
     * @param studentId    mã sinh viên
     * @return {@code true} nếu đã nộp
     */
    boolean existsByAssignment_AssignmentIdAndStudent_Id(Long assignmentId, Long studentId);

    /**
     * Phân trang bài nộp theo bài tập.
     *
     * @param assignmentId mã bài tập
     * @param pageable     thông tin phân trang
     * @return trang kết quả
     */
    Page<AssignmentSubmission> findByAssignment_AssignmentId(Long assignmentId, Pageable pageable);

    /**
     * Phân trang bài nộp theo sinh viên.
     *
     * @param studentId mã sinh viên
     * @param pageable  thông tin phân trang
     * @return trang kết quả
     */
    Page<AssignmentSubmission> findByStudent_Id(Long studentId, Pageable pageable);

    /**
     * Đếm số bài nộp cho một bài tập.
     *
     * @param assignmentId mã bài tập
     * @return số lượng bài nộp
     */
    long countByAssignment_AssignmentId(Long assignmentId);

    /**
     * Đếm số bài nộp theo trạng thái.
     *
     * @param assignmentId mã bài tập
     * @param status       trạng thái
     * @return số lượng
     */
    long countByAssignment_AssignmentIdAndStatus(Long assignmentId, SubmissionAssignmentStatus status);
}
