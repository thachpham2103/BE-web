package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.notification.NotificationTemplateResponseDto;
import com.example.be.web.doman.entity.NotificationTemplate;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationTemplateMapper {

    @Mapping(source = "createDate", target = "createdAt")
    NotificationTemplateResponseDto toResponse(NotificationTemplate entity);
}
