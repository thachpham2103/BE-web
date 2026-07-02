package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.task.StudentTaskProgressResponseDto;
import com.example.be.web.doman.entity.StudentTaskProgress;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentTaskProgressMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.fullName", target = "studentName")
    @Mapping(source = "learningTask.taskId", target = "taskId")
    @Mapping(source = "learningTask.title", target = "taskTitle")
    StudentTaskProgressResponseDto toResponse(StudentTaskProgress entity);
}
