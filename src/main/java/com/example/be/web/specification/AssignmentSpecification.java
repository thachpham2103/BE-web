package com.example.be.web.specification;

import com.example.be.web.doman.entity.Assignment;
import com.example.be.web.doman.model.AssignmentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * Specification factory cho tìm kiếm động {@link Assignment}.
 *
 * <p>Sử dụng kết hợp với {@code JpaSpecificationExecutor} để xây dựng
 * truy vấn linh hoạt dựa trên nhiều tiêu chí.</p>
 *
 * @author auto-generated
 */
public class AssignmentSpecification {

    private AssignmentSpecification() {
        // utility class – không cho phép khởi tạo
    }

    /**
     * Lọc bài tập không bị xóa (soft-delete filter).
     *
     * @return Specification loại bỏ bài tập đã xóa
     */
    public static Specification<Assignment> notDeleted() {
        return (root, query, cb) -> cb.notEqual(root.get("status"), AssignmentStatus.DELETED);
    }

    /**
     * Tìm theo tiêu đề (LIKE, không phân biệt hoa thường).
     *
     * @param title từ khóa tiêu đề
     * @return Specification tìm kiếm theo title
     */
    public static Specification<Assignment> titleContains(String title) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    /**
     * Lọc theo lớp học.
     *
     * @param classId mã lớp học
     * @return Specification lọc theo classRoom
     */
    public static Specification<Assignment> belongsToClass(Long classId) {
        return (root, query, cb) ->
                cb.equal(root.get("classRoom").get("classId"), classId);
    }

    /**
     * Lọc theo trạng thái.
     *
     * @param status trạng thái cần lọc
     * @return Specification lọc theo status
     */
    public static Specification<Assignment> hasStatus(AssignmentStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    /**
     * Lọc theo người tạo.
     *
     * @param userId mã người tạo
     * @return Specification lọc theo createdBy
     */
    public static Specification<Assignment> createdBy(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("createdBy").get("id"), userId);
    }

    /**
     * Lọc bài tập có deadline trước thời điểm cho trước.
     *
     * @param dateTime mốc thời gian
     * @return Specification lọc deadline trước dateTime
     */
    public static Specification<Assignment> deadlineBefore(LocalDateTime dateTime) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("deadline"), dateTime);
    }

    /**
     * Lọc bài tập có deadline sau thời điểm cho trước.
     *
     * @param dateTime mốc thời gian
     * @return Specification lọc deadline sau dateTime
     */
    public static Specification<Assignment> deadlineAfter(LocalDateTime dateTime) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("deadline"), dateTime);
    }
}
