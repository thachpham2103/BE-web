package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.notification.NotificationResponseDto;
import com.example.be.web.doman.entity.Notification;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    NotificationResponseDto toResponse(Notification entity);
}
