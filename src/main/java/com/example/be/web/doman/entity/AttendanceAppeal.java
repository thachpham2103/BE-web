package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import com.example.be.web.doman.model.AppealStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho đơn giải trình điểm danh ({@code AttendanceAppeal}).
 *
 * <p>Sinh viên tạo đơn giải trình khi bị đánh vắng sai.
 * Mỗi sinh viên chỉ được tạo một đơn giải trình cho mỗi bản ghi điểm danh
 * (UNIQUE trên cặp {@code student_id, record_id}).</p>
 *
 * @author auto-generated
 * @see AttendanceRecord
 * @see AttendanceSession
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attendance_appeals",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_APPEAL_STUDENT_RECORD",
                columnNames = {"student_id", "record_id"}
        ))
public class AttendanceAppeal extends DateAuditing {

    /** Khóa chính, tự tăng. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appeal_id", nullable = false, updatable = false)
    private Long appealId;

    /** Sinh viên tạo giải trình. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_APPEAL_STUDENT"))
    private User student;

    /** Buổi điểm danh liên quan. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_APPEAL_SESSION"))
    private AttendanceSession attendanceSession;

    /** Bản ghi điểm danh cần giải trình. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_APPEAL_RECORD"))
    private AttendanceRecord attendanceRecord;

    /** Lý do giải trình. */
    @Column(name = "reason", columnDefinition = "TEXT", nullable = false)
    private String reason;

    /** Ảnh minh chứng. */
    @Column(name = "proof_image_url", length = 500)
    private String proofImageUrl;

    /** Trạng thái giải trình. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private AppealStatus status = AppealStatus.PENDING;

    /** Ghi chú của giảng viên khi xét duyệt. */
    @Column(name = "teacher_note", columnDefinition = "TEXT")
    private String teacherNote;

    /** Người xét duyệt giải trình. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by",
            foreignKey = @ForeignKey(name = "FK_APPEAL_REVIEWER"))
    private User reviewedBy;

    /** Thời điểm xét duyệt. */
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;
}
