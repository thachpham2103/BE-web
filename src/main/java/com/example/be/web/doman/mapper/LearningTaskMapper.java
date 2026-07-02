package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.task.LearningTaskResponseDto;
import com.example.be.web.doman.entity.LearningTask;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LearningTaskMapper {

    @Mapping(source = "classRoom.classId", target = "classId")
    @Mapping(source = "classRoom.title", target = "className")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.fullName", target = "createdByName")
    @Mapping(source = "createDate", target = "createdAt")
    LearningTaskResponseDto toResponse(LearningTask entity);
}
