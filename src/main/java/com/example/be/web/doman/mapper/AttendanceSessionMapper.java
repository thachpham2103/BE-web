package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.request.attendance.AttendanceSessionRequestDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceSessionResponseDto;
import com.example.be.web.doman.entity.AttendanceSession;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AttendanceSessionMapper {

    @Mapping(source = "classRoom.id", target = "classId")
    @Mapping(source = "createdByUser.id", target = "createdByUserId")
//    @Mapping(source = "createdByUser.username", target = "createdByUserName")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(target = "sessionId", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "attendanceRecords", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    AttendanceSession toEntity(AttendanceSessionRequestDto request);

    @Mapping(source = "createdByUser.id", target = "createdByUserId")
    AttendanceSessionResponseDto toResponse(AttendanceSession entity);
    AttendanceSession toEntity(AttendanceSessionRequestDto request);

    void updateEntityFromDto(AttendanceSessionRequestDto dto, @MappingTarget AttendanceSession entity);
    List<AttendanceSessionResponseDto> toResponses(List<AttendanceSession> entities);
}

