package com.example.be.web.repository;

import com.example.be.web.doman.entity.AttendanceWarning;
import com.example.be.web.doman.model.AppealStatus;
import com.example.be.web.doman.model.WarningLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository thao tác dữ liệu {@link AttendanceWarning}.
 *
 * @author auto-generated
 */
@Repository
public interface AttendanceWarningRepository
        extends JpaRepository<AttendanceWarning, Long>, JpaSpecificationExecutor<AttendanceWarning> {

    /** Phân trang cảnh báo theo lớp. */
    Page<AttendanceWarning> findByClassRoom_ClassId(Long classId, Pageable pageable);

    /** Phân trang cảnh báo theo sinh viên. */
    Page<AttendanceWarning> findByStudent_Id(Long studentId, Pageable pageable);

    /** Tìm cảnh báo theo sinh viên và lớp. */
    List<AttendanceWarning> findByStudent_IdAndClassRoom_ClassId(Long studentId, Long classId);

    /** Đếm cảnh báo theo mức trong lớp. */
    long countByClassRoom_ClassIdAndWarningLevel(Long classId, WarningLevel level);

    /** Đếm cảnh báo theo trạng thái trong lớp. */
    long countByClassRoom_ClassIdAndStatus(Long classId, AppealStatus status);
}
