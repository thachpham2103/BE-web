package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.entity.AttendanceRecord;
import com.example.be.web.doman.entity.AttendanceSession;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.model.RecordStatus;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.AttendanceRecordRepository;
import com.example.be.web.repository.AttendanceSessionRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.service.AttendanceRecordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AttendanceRecordServiceImpl implements AttendanceRecordService {

    private final AttendanceRecordRepository recordRepository;
    private final AttendanceSessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Override
    public AttendanceRecord checkIn(Long sessionId, Long userId, MultipartFile faceImage, double gpsLat, double gpsLng) {
        AttendanceSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // 1. Kiểm tra GPS
        double distance = calculateDistance(session.getLocationLatitude(), session.getLocationLongatitude(), gpsLat, gpsLng);
        if (distance > session.getRadiusMeters()) {
            throw new RuntimeException("Bạn đang ngoài phạm vi điểm danh");
        }

        // 2. Gọi AI model nhận diện khuôn mặt
//        boolean faceResult = aiFaceRecognitionService.verifyFace(user, faceImage);
        boolean faceResult = true; // giả sử luôn đúng để test *******

        // 3. Tạo record
        AttendanceRecord record = new AttendanceRecord();
        record.setAttendanceSession(session);
        record.setUser(user);
        record.setCheckinTime(LocalDateTime.now());
        record.setGpsLatitude(gpsLat);
        record.setGpsLongitude(gpsLng);
        record.setResultFace(faceResult);
        record.setRecordStatus(faceResult ? RecordStatus.PRESENT : RecordStatus.INVALID);

        return recordRepository.save(record);
    }


//    @Override
//    public AttendanceRecord checkIn(Long sessionId, Long userId, AttendanceRecord record) {
//        AttendanceSession session = sessionRepository.findById(sessionId)
//                .orElseThrow(() -> new NotFoundException(ErrorMessage.AttendanceSession.SESSION_NOT_FOUND, new String[]{sessionId.toString()}));
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND_ID, new String[]{userId.toString()}));
//
//        try {
//            record.setAttendanceSession(session);
//            record.setUser(user);
//            record.setCheckinTime(LocalDateTime.now());
//            return recordRepository.save(record);
//        } catch (Exception e) {
//            log.error("Lỗi khi điểm danh user {} tại session {}: {}", userId, sessionId, e.getMessage());
//            throw new RuntimeException(ErrorMessage.AttendanceRecord.ERR_CHECKIN, e);
//        }
//    }

    @Override
    public AttendanceRecord getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.AttendanceRecord.RECORD_NOT_FOUND, new String[]{id.toString()}));
    }

    @Override
    public List<AttendanceRecord> getRecordsBySession(Long sessionId) {
        try {
            return recordRepository.findByAttendanceSession_SessionId(sessionId);
        } catch (Exception e) {
            log.error("Lỗi khi lấy danh sách record của session {}: {}", sessionId, e.getMessage());
            throw new RuntimeException(ErrorMessage.AttendanceRecord.ERR_GET_BY_SESSION, e);
        }
    }

    @Override
    public List<AttendanceRecord> getRecordsByUser(Long userId) {
        try {
            return recordRepository.findByUser_Id(userId);
        } catch (Exception e) {
            log.error("Lỗi khi lấy danh sách record của user {}: {}", userId, e.getMessage());
            throw new RuntimeException(ErrorMessage.AttendanceRecord.ERR_GET_BY_USER, e);
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371000; // bán kính Trái Đất (mét)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // khoảng cách tính bằng mét
    }

}
