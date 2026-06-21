package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import com.example.be.web.doman.model.AppealStatus;
import com.example.be.web.doman.model.WarningLevel;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity đại diện cho cảnh báo nghỉ học ({@code AttendanceWarning}).
 *
 * <p>Hệ thống tự động tạo cảnh báo khi sinh viên nghỉ vượt ngưỡng
 * được cấu hình trong {@link AttendancePolicy}.</p>
 *
 * @author auto-generated
 * @see AttendancePolicy
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attendance_warnings")
public class AttendanceWarning extends DateAuditing {

    /** Khóa chính, tự tăng. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warning_id", nullable = false, updatable = false)
    private Long warningId;

    /** Sinh viên bị cảnh báo. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_WARNING_STUDENT"))
    private User student;

    /** Lớp học liên quan. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_WARNING_CLASS"))
    private ClassRoom classRoom;

    /** Số buổi vắng tại thời điểm cảnh báo. */
    @Column(name = "absent_count", nullable = false)
    private Integer absentCount;

    /** Tổng số buổi học đã diễn ra. */
    @Column(name = "total_session", nullable = false)
    private Integer totalSession;

    /** Tỷ lệ vắng (%). */
    @Column(name = "absent_rate", nullable = false)
    private Double absentRate;

    /** Mức cảnh báo. */
    @Enumerated(EnumType.STRING)
    @Column(name = "warning_level", length = 20, nullable = false)
    private WarningLevel warningLevel;

    /** Nội dung cảnh báo. */
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    /** Trạng thái xử lý cảnh báo. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private AppealStatus status = AppealStatus.PENDING;
}
