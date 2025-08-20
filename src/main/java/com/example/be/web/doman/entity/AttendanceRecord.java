package com.example.be.web.doman.entity;

import com.example.be.web.doman.model.RecordStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="attendance_record")

public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @Column(name="checkin_time", nullable = false)
    private LocalDateTime checkinTime;

    @Column(name="note", nullable = false)
    private String note;

    @Column(name="result_face", nullable = false)
    private boolean resultFace;

    @Column(name = "gpsLatitude", nullable = false)
    private double gpsLatitude;

    @Column(name = "gpsLongitude", nullable = false)
    private double gpsLongitude;

    @Column(name="status", nullable = false)
    private RecordStatus recordStatus;

    @ManyToOne
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(name="FK_USER_ID"))
    private User user;

    @ManyToOne
    @JoinColumn(name="attendanceSession_id", foreignKey = @ForeignKey(name = "FK_ATTENDANCESESSION_ID"))
    private AttendanceSession attendanceSession;


}
