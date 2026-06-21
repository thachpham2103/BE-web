package com.example.be.web.repository;

import com.example.be.web.doman.entity.AttendancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository thao tác dữ liệu {@link AttendancePolicy}.
 *
 * @author auto-generated
 */
@Repository
public interface AttendancePolicyRepository extends JpaRepository<AttendancePolicy, Long> {

    /**
     * Tìm chính sách điểm danh theo lớp học.
     *
     * @param classId mã lớp
     * @return chính sách nếu tồn tại
     */
    Optional<AttendancePolicy> findByClassRoom_ClassId(Long classId);

    /**
     * Kiểm tra lớp đã có chính sách chưa.
     *
     * @param classId mã lớp
     * @return true nếu đã có
     */
    boolean existsByClassRoom_ClassId(Long classId);
}
