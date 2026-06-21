package com.example.be.web.repository;

import com.example.be.web.doman.entity.Assignment;
import com.example.be.web.doman.model.AssignmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository thao tác dữ liệu {@link Assignment}.
 *
 * <p>Kế thừa {@link JpaSpecificationExecutor} để hỗ trợ tìm kiếm động
 * thông qua {@code Specification}.</p>
 *
 * @author auto-generated
 */
@Repository
public interface AssignmentRepository
        extends JpaRepository<Assignment, Long>, JpaSpecificationExecutor<Assignment> {

    /**
     * Tìm bài tập theo ID và trạng thái khác DELETED (soft-delete filter).
     *
     * @param id     mã bài tập
     * @param status trạng thái cần loại trừ
     * @return bài tập nếu tồn tại
     */
    Optional<Assignment> findByAssignmentIdAndStatusNot(Long id, AssignmentStatus status);

    /**
     * Phân trang bài tập theo lớp học, loại trừ trạng thái DELETED.
     *
     * @param classId  mã lớp học
     * @param status   trạng thái cần loại trừ
     * @param pageable thông tin phân trang
     * @return trang kết quả
     */
    Page<Assignment> findByClassRoom_ClassIdAndStatusNot(Long classId, AssignmentStatus status, Pageable pageable);

    /**
     * Đếm số bài tập trong một lớp (không tính đã xóa).
     *
     * @param classId mã lớp học
     * @param status  trạng thái cần loại trừ
     * @return số lượng bài tập
     */
    long countByClassRoom_ClassIdAndStatusNot(Long classId, AssignmentStatus status);

    /**
     * Lấy tất cả bài tập theo lớp.
     *
     * @param classId mã lớp
     * @param status  trạng thái loại trừ
     * @return danh sách bài tập
     */
    List<Assignment> findByClassRoom_ClassIdAndStatusNot(Long classId, AssignmentStatus status);
}
