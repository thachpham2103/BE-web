package com.example.be.web.service;

import com.example.be.web.doman.dto.response.activity.ActivityLogResponseDto;
import com.example.be.web.doman.model.ActivityAction;
import com.example.be.web.doman.model.TargetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityLogService {
    void logActivity(String username, ActivityAction action, TargetType targetType, Long targetId, String description, String ipAddress);
    Page<ActivityLogResponseDto> getAllLogs(Pageable pageable);
    Page<ActivityLogResponseDto> getLogsByUser(Long userId, Pageable pageable);
    Page<ActivityLogResponseDto> getLogsByAction(ActivityAction action, Pageable pageable);
    Page<ActivityLogResponseDto> getLogsByTarget(TargetType targetType, Long targetId, Pageable pageable);
    Page<ActivityLogResponseDto> getMyLogs(Pageable pageable);
}
