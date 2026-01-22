package com.example.be.web.service;

import com.example.be.web.doman.entity.AttendanceRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttendanceRecordService {
    AttendanceRecord checkIn(Long sessionId, Long userId, MultipartFile faceImage, double gpsLat, double gpsLng);
//    AttendanceRecord checkIn(Long sessionId, Long userId, AttendanceRecord record);
    AttendanceRecord getRecordById(Long id);
    List<AttendanceRecord> getRecordsBySession(Long sessionId);
    List<AttendanceRecord> getRecordsByUser(Long userId);
}

