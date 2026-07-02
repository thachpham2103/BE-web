package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import com.example.be.web.doman.model.TargetType;
import com.example.be.web.doman.model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho nhiệm vụ học tập ({@code LearningTask}).
 *
 * <p>targetType/targetId là liên kết logic tới Document/Assignment/Contest/Payment/ClassSession,
 * không dùng FK cứng.</p>
 *
 * @author auto-generated
 * @see StudentTaskProgress
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "learning_tasks")
public class LearningTask extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false, updatable = false)
    private Long taskId;

    /** Lớp học chứa nhiệm vụ. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_TASK_CLASS"))
    private ClassRoom classRoom;

    /** Người tạo nhiệm vụ. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false,
            foreignKey = @ForeignKey(name = "FK_TASK_CREATOR"))
    private User createdBy;

    /** Tiêu đề nhiệm vụ. */
    @Column(name = "title", nullable = false, length = 300)
    private String title;

    /** Mô tả nhiệm vụ. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Loại đối tượng liên kết (liên kết logic). */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", length = 30)
    private TargetType targetType;

    /** ID đối tượng liên kết (liên kết logic). */
    @Column(name = "target_id")
    private Long targetId;

    /** Hạn hoàn thành. */
    @Column(name = "deadline")
    private LocalDateTime deadline;

    /** Trạng thái nhiệm vụ. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private TaskStatus status = TaskStatus.ACTIVE;

    /** Danh sách tiến độ sinh viên. */
    @OneToMany(mappedBy = "learningTask", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<StudentTaskProgress> progresses = new ArrayList<>();
}
