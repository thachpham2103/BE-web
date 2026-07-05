package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import com.example.be.web.doman.model.AssignmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho bài tập (Assignment) trong một lớp học.
 *
 * <p>Mỗi bài tập thuộc về một {@link ClassRoom} và được tạo bởi một {@link User} (giảng viên).
 * Sinh viên nộp bài qua {@link AssignmentSubmission}.</p>
 *
 * <p>Hỗ trợ soft-delete thông qua trạng thái {@link AssignmentStatus#DELETED}.</p>
 *
 * @author auto-generated
 * @see ClassRoom
 * @see AssignmentSubmission
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "assignments")
public class Assignment extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id", nullable = false, updatable = false)
    private Long assignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_ASSIGNMENT_CLASS"))
    private ClassRoom classRoom;


    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "max_score")
    private Double maxScore;

    @Column(name = "allow_late_submit", nullable = false)
    @Builder.Default
    private Boolean allowLateSubmit = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false,
            foreignKey = @ForeignKey(name = "FK_ASSIGNMENT_CREATOR"))
    private User createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private AssignmentStatus status = AssignmentStatus.DRAFT;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<AssignmentSubmission> submissions = new ArrayList<>();
    
}
