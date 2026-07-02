package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import com.example.be.web.doman.model.TaskProgressStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho tiến độ hoàn thành nhiệm vụ của sinh viên ({@code StudentTaskProgress}).
 *
 * <p>Ràng buộc UNIQUE trên (student_id, task_id).</p>
 *
 * @author auto-generated
 * @see LearningTask
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "student_task_progress",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_PROGRESS_STUDENT_TASK",
                columnNames = {"student_id", "task_id"}
        ))
public class StudentTaskProgress extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id", nullable = false, updatable = false)
    private Long progressId;

    /** Sinh viên. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PROGRESS_STUDENT"))
    private User student;

    /** Nhiệm vụ. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PROGRESS_TASK"))
    private LearningTask learningTask;

    /** Trạng thái tiến độ. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private TaskProgressStatus status = TaskProgressStatus.NOT_STARTED;

    /** Thời điểm hoàn thành. */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /** Ghi chú. */
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
}
