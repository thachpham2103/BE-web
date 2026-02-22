package com.example.be.web.repository;

import com.example.be.web.doman.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByAttendanceSession_SessionId(Long sessionId);
    List<AttendanceRecord> findByUser_Id(Long userId);
}

