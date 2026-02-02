package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.request.attendance.AttendanceSessionRequestDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceSessionResponseDto;
import com.example.be.web.doman.entity.AttendanceSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceSessionMapper {

    @Mapping(target = "sessionId", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "attendanceRecords", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    AttendanceSession toEntity(AttendanceSessionRequestDto request);

    @Mapping(source = "createdByUser.id", target = "createdByUserId")
    AttendanceSessionResponseDto toResponse(AttendanceSession entity);

    List<AttendanceSessionResponseDto> toResponses(List<AttendanceSession> entities);
}

