package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="attendance")

public class AttendanceSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @Column(name="title", nullable = false,length = 35)
    private String title;

    @Column(name="start_time",nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name="update_at", nullable = false)
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    //kinh độ
    @Column(name="location_latitude",nullable = false)
    private double locationLatitude;

    //vĩ độ
    @Column(name="location_longatitude", nullable = false)
    private double locationLongatitude;

    @Column(name="radius_meters", nullable = false)
    private int radiusMeters;

    @ManyToOne
    @JoinColumn(name="class_id",foreignKey = @ForeignKey(name = "FK_CLASS_ID"))
    private ClassRoom classRoom;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attendanceSession")
    @JsonIgnore
    private Set<AttendanceRecord> attendanceRecords= new HashSet<>();


}
