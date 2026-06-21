package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.SubmissionAssignmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho bài nộp (Submission) của sinh viên cho một {@link Assignment}.
 *
 * <p>Mỗi sinh viên chỉ được nộp một bài cho mỗi bài tập
 * (ràng buộc UNIQUE trên cặp {@code assignment_id, student_id}).</p>
 *
 * <p>Giảng viên chấm điểm sẽ được ghi nhận qua {@link #gradedBy} và {@link #gradedAt}.</p>
 *
 * @author auto-generated
 * @see Assignment
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "assignment_submissions",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_SUBMISSION_ASSIGNMENT_STUDENT",
                columnNames = {"assignment_id", "student_id"}
        ))
public class AssignmentSubmission {

    /** Khóa chính, tự tăng. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id", nullable = false, updatable = false)
    private Long submissionId;

    /** Bài tập mà bài nộp này thuộc về. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_SUBMISSION_ASSIGNMENT"))
    private Assignment assignment;

    /** Sinh viên nộp bài. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_SUBMISSION_STUDENT"))
    private User student;

    /** Nội dung bài nộp (văn bản). */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /** Đường dẫn file đính kèm. */
    @Column(name = "file_url", length = 500)
    private String fileUrl;

    /** Thời gian nộp bài. */
    @Column(name = "submitted_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime submittedAt;

    /** Trạng thái bài nộp. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private SubmissionAssignmentStatus status = SubmissionAssignmentStatus.SUBMITTED;

    /** Điểm được chấm. */
    @Column(name = "score")
    private Double score;

    /** Nhận xét của giảng viên. */
    @Column(name = "teacher_comment", columnDefinition = "TEXT")
    private String teacherComment;

    /** Giảng viên chấm điểm. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "graded_by",
            foreignKey = @ForeignKey(name = "FK_SUBMISSION_GRADER"))
    private User gradedBy;

    /** Thời điểm chấm điểm. */
    @Column(name = "graded_at")
    private LocalDateTime gradedAt;
}
