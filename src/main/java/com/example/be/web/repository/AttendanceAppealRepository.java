package com.example.be.web.repository;

import com.example.be.web.doman.entity.AttendanceAppeal;
import com.example.be.web.doman.model.AppealStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository thao tác dữ liệu {@link AttendanceAppeal}.
 *
 * @author auto-generated
 */
@Repository
public interface AttendanceAppealRepository
        extends JpaRepository<AttendanceAppeal, Long>, JpaSpecificationExecutor<AttendanceAppeal> {

    /** Tìm giải trình theo sinh viên và bản ghi điểm danh. */
    Optional<AttendanceAppeal> findByStudent_IdAndAttendanceRecord_RecordId(Long studentId, Long recordId);

    /** Kiểm tra đã giải trình cho record chưa. */
    boolean existsByStudent_IdAndAttendanceRecord_RecordId(Long studentId, Long recordId);

    /** Phân trang giải trình theo trạng thái. */
    Page<AttendanceAppeal> findByStatus(AppealStatus status, Pageable pageable);

    /** Phân trang giải trình theo sinh viên. */
    Page<AttendanceAppeal> findByStudent_Id(Long studentId, Pageable pageable);

    /** Phân trang giải trình theo buổi điểm danh. */
    Page<AttendanceAppeal> findByAttendanceSession_SessionId(Long sessionId, Pageable pageable);

    /** Đếm giải trình chờ duyệt. */
    long countByStatus(AppealStatus status);
}
