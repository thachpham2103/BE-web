package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "attendance_policies",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_POLICY_CLASS",
                columnNames = {"class_id"}
        ))
public class AttendancePolicy extends DateAuditing {

    /** Khóa chính, tự tăng. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id", nullable = false, updatable = false)
    private Long policyId;

    /** Lớp học áp dụng chính sách (1-1). */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "FK_POLICY_CLASS"))
    private ClassRoom classRoom;

    /**
     * Ngưỡng số buổi vắng để cảnh báo.
     * Ví dụ: vắng 3 buổi → cảnh báo lần 1.
     */
    @Column(name = "warning_threshold", nullable = false)
    @Builder.Default
    private Integer warningThreshold = 3;

    /**
     * Ngưỡng số buổi vắng để cấm thi / đình chỉ.
     * Ví dụ: vắng 5 buổi → cấm thi.
     */
    @Column(name = "ban_threshold", nullable = false)
    @Builder.Default
    private Integer banThreshold = 5;

    /**
     * Số phút cho phép đến trễ mà vẫn tính có mặt.
     * Ví dụ: 15 phút.
     */
    @Column(name = "allow_late_minutes", nullable = false)
    @Builder.Default
    private Integer allowLateMinutes = 15;
}
