package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.RegistrationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "class_registrations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"class_id", "student_id"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClassRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_id")
    private Long registrationId;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassRoom classEntity;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private RegistrationStatus status;

    @Column(name = "pending", nullable = false)
    private boolean pending;

    @PrePersist
    public void prePersist() {

        if (this.registeredAt == null) {
            this.registeredAt = LocalDateTime.now();
        }

        if (this.status == null) {
            this.status = RegistrationStatus.PENDING;
        }

        this.pending = this.status == RegistrationStatus.PENDING;
    }
}