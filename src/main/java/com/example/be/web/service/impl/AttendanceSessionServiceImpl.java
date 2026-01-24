package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.entity.AttendanceSession;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.AttendanceSessionRepository;
import com.example.be.web.service.AttendanceSessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AttendanceSessionServiceImpl implements AttendanceSessionService {

    private final AttendanceSessionRepository sessionRepository;

    @Override
    public AttendanceSession createSession(AttendanceSession session) {
        try {
            session.setCreateAt(LocalDateTime.now());
            session.setUpdateAt(LocalDateTime.now());
            return sessionRepository.save(session);
        } catch (Exception e) {
            log.error("Lỗi khi tạo AttendanceSession: {}", e.getMessage());
            throw new RuntimeException(ErrorMessage.AttendanceSession.ERR_CREATE_SESSION, e);
        }
    }

    @Override
    public AttendanceSession updateSession(Long id, AttendanceSession session) {
        AttendanceSession existing = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.AttendanceSession.SESSION_NOT_FOUND, new String[]{id.toString()}));
        try {
            existing.setTitle(session.getTitle());
            existing.setStartTime(session.getStartTime());
            existing.setEndTime(session.getEndTime());
            existing.setLocationLatitude(session.getLocationLatitude());
            existing.setLocationLongatitude(session.getLocationLongatitude());
            existing.setRadiusMeters(session.getRadiusMeters());
            existing.setUpdateAt(LocalDateTime.now());
            return sessionRepository.save(existing);
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật AttendanceSession id {}: {}", id, e.getMessage());
            throw new RuntimeException(ErrorMessage.AttendanceSession.ERR_UPDATE_SESSION, e);
        }
    }

    @Override
    public void deleteSession(Long id) {
        AttendanceSession existing = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.AttendanceSession.SESSION_NOT_FOUND, new String[]{id.toString()}));
        try {
            sessionRepository.delete(existing);
        } catch (Exception e) {
            log.error("Lỗi khi xóa AttendanceSession id {}: {}", id, e.getMessage());
            throw new RuntimeException(ErrorMessage.AttendanceSession.ERR_DELETE_SESSION, e);
        }
    }

    @Override
    public AttendanceSession getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.AttendanceSession.SESSION_NOT_FOUND, new String[]{id.toString()}));
    }

    @Override
    public List<AttendanceSession> getAllSessions() {
        try {
            return sessionRepository.findAll();
        } catch (Exception e) {
            log.error("Lỗi khi lấy danh sách AttendanceSession: {}", e.getMessage());
            throw new RuntimeException(ErrorMessage.AttendanceSession.ERR_GET_ALL_SESSION, e);
        }
    }
}
