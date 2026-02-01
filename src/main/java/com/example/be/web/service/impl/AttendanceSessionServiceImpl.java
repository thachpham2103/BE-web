package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.attendance.AttendanceSessionRequestDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceSessionResponseDto;
import com.example.be.web.doman.entity.AttendanceSession;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.mapper.AttendanceSessionMapper;
import com.example.be.web.exception.extended.InternalServerException;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.AttendanceSessionRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.security.UserPrincipal;
import com.example.be.web.service.AttendanceSessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AttendanceSessionServiceImpl implements AttendanceSessionService {

    private final AttendanceSessionRepository sessionRepository;
    private final AttendanceSessionMapper mapper;
    private final UserRepository userRepository;

    @Override
    public AttendanceSessionResponseDto createSession(AttendanceSessionRequestDto requestDto) {

        AttendanceSession session = mapper.toEntity(requestDto);

        // lấy user đang đăng nhập từ SecurityContext
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User creator = userRepository.findById(principal.getId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND_ID, new String[]{principal.getId().toString()}));
        session.setCreatedByUser(creator);

        // set thời gian tạo và cập nhật
        session.setCreateAt(LocalDateTime.now());
        session.setUpdateAt(LocalDateTime.now());

        // lưu vào DB
        AttendanceSession saved = sessionRepository.save(session);
        return mapper.toResponse(saved);
    }

    @Override
    public AttendanceSessionResponseDto updateSession(Long id, AttendanceSessionRequestDto requestDto) {

        AttendanceSession existing = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceSession.SESSION_NOT_FOUND,
                        new String[]{id.toString()}
                ));

        // cập nhật từ DTO sang entity
        existing.setTitle(requestDto.getTitle());
        existing.setStartTime(requestDto.getStartTime());
        existing.setEndTime(requestDto.getEndTime());
        existing.setLocationLatitude(requestDto.getLocationLatitude());
        existing.setLocationLongitude(requestDto.getLocationLongitude());
        existing.setRadiusMeters(requestDto.getRadiusMeters());
        existing.setUpdateAt(LocalDateTime.now());

        AttendanceSession updated = sessionRepository.save(existing);
        return mapper.toResponse(updated);
    }

    @Override
    public void deleteSession(Long id) {
        AttendanceSession existing = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceSession.SESSION_NOT_FOUND,
                        new String[]{id.toString()}
                ));
        sessionRepository.delete(existing);
    }

    @Override
    public AttendanceSessionResponseDto getSessionById(Long id) {
        AttendanceSession entity = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.AttendanceSession.SESSION_NOT_FOUND,
                        new String[]{id.toString()}
                ));
        return mapper.toResponse(entity);
    }

    @Override
    public List<AttendanceSessionResponseDto> getAllSessions() {
        try {
            List<AttendanceSession> entities = sessionRepository.findAll();
            return mapper.toResponses(entities);
        } catch (Exception e) {
            log.error("Error fetching AttendanceSessions: {}", e.getMessage(), e);
            throw new InternalServerException(ErrorMessage.AttendanceSession.ERR_GET_ALL_SESSION);
        }
    }


}

