package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.ClassStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "class")

public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;

    @Column(name = "description")
    private String description;

    @Column(name="title", nullable = false, length = 200)
    private String title;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classRoom")
    @JsonIgnore
    private Set<User> users= new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @Column(name = "max_students")
    private Integer maxStudents;

    @Column(name = "current_students")
    private Integer currentStudents;

    @Column(name = "tuition_fee", precision = 12, scale = 2)
    private BigDecimal tuitionFee;

    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private ClassStatus status;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ClassRegistration> registrations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classRoom")
    @JsonIgnore
    private Set<AttendanceSession> attendanceSessions= new HashSet<>();

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ClassSession> classSessions = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "class_locations",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Assignment> assignments = new ArrayList<>();

}
