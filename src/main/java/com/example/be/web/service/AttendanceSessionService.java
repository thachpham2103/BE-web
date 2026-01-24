package com.example.be.web.service;

import com.example.be.web.doman.entity.AttendanceSession;
import java.util.List;

public interface AttendanceSessionService {
    AttendanceSession createSession(AttendanceSession session);
    AttendanceSession updateSession(Long id, AttendanceSession session);
    void deleteSession(Long id);
    AttendanceSession getSessionById(Long id);
    List<AttendanceSession> getAllSessions();
}

