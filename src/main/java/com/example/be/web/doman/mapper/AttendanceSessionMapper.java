package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.request.attendance.AttendanceSessionRequestDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceSessionResponseDto;
import com.example.be.web.doman.entity.AttendanceSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceSessionMapper {

    @Mapping(source = "createdByUser.id", target = "createdByUserId")
//    @Mapping(source = "createdByUser.username", target = "createdByUserName")
    AttendanceSessionResponseDto toResponse(AttendanceSession entity);
    List<AttendanceSessionResponseDto> toResponses(List<AttendanceSession> entities);


    AttendanceSession toEntity(AttendanceSessionRequestDto request);
}

