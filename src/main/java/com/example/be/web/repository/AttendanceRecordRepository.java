package com.example.be.web.repository;

import com.example.be.web.doman.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByAttendanceSession_SessionId(Long sessionId);
    List<AttendanceRecord> findByUser_Id(Long userId);
}

