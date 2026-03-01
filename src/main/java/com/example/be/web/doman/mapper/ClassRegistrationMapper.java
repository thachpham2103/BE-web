package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.classRoom.ClassRegistrationResponseDto;
import com.example.be.web.doman.entity.ClassRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassRegistrationMapper {

    @Mapping(source = "classEntity.classId", target = "classId")
    @Mapping(source = "classEntity.title", target = "classTitle")
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.name", target = "studentName")
    ClassRegistrationResponseDto toResponseDto(ClassRegistration registration);

    List<ClassRegistrationResponseDto> toResponseDtoList(List<ClassRegistration> registrations);
}
