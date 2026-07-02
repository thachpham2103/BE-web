package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.activity.ActivityLogResponseDto;
import com.example.be.web.doman.entity.ActivityLog;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ActivityLogMapper {

    @Mapping(source = "actor.id", target = "actorId")
    @Mapping(source = "actor.fullName", target = "actorName")
    ActivityLogResponseDto toResponse(ActivityLog entity);
}
