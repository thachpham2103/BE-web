package com.example.be.web.service.impl;

import com.example.be.web.doman.dto.response.activity.ActivityLogResponseDto;
import com.example.be.web.doman.entity.ActivityLog;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.mapper.ActivityLogMapper;
import com.example.be.web.doman.model.ActivityAction;
import com.example.be.web.doman.model.TargetType;
import com.example.be.web.repository.ActivityLogRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.service.ActivityLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;
    private final ActivityLogMapper activityLogMapper;

    @Override
    public void logActivity(String username, ActivityAction action, TargetType targetType, Long targetId, String description, String ipAddress) {
        try {
            User actor = userRepository.findByUsername(username).orElse(null);
            if (actor != null) {
                ActivityLog activityLog = ActivityLog.builder()
                        .actor(actor)
                        .action(action)
                        .targetType(targetType)
                        .targetId(targetId)
                        .description(description)
                        .ipAddress(ipAddress)
                        .build();
                activityLogRepository.save(activityLog);
            }
        } catch (Exception e) {
            log.error("Failed to log activity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityLogResponseDto> getAllLogs(Pageable pageable) {
        return activityLogRepository.findAll(pageable)
                .map(activityLogMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityLogResponseDto> getLogsByUser(Long userId, Pageable pageable) {
        return activityLogRepository.findByActor_Id(userId, pageable)
                .map(activityLogMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityLogResponseDto> getLogsByAction(ActivityAction action, Pageable pageable) {
        return activityLogRepository.findByAction(action, pageable)
                .map(activityLogMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityLogResponseDto> getLogsByTarget(TargetType targetType, Long targetId, Pageable pageable) {
        return activityLogRepository.findByTargetTypeAndTargetId(targetType, targetId, pageable)
                .map(activityLogMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityLogResponseDto> getMyLogs(Pageable pageable) {
        Object principal = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = null;
        if (principal instanceof com.example.be.web.security.UserPrincipal) {
            currentUserId = ((com.example.be.web.security.UserPrincipal) principal).getId();
        } else {
            throw new com.example.be.web.exception.extended.UnauthorizedException("User not authenticated");
        }
        return activityLogRepository.findByActor_Id(currentUserId, pageable)
                .map(activityLogMapper::toResponse);
    }
}
