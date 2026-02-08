package com.example.be.web.doman.mapper;

import com.example.be.web.doman.entity.AttendanceRecord;
import com.example.be.web.doman.dto.request.attendance.AttendanceRecordRequestDto;
import com.example.be.web.doman.dto.response.attendance.AttendanceRecordResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceRecordMapper {
    // Map từ Entity sang Response DTO
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "attendanceSession.sessionId", target = "attendanceSessionId")
    @Mapping(source = "attendanceSession.title", target = "attendanceSessionTitle")
    AttendanceRecordResponseDto toResponse(AttendanceRecord entity);

    List<AttendanceRecordResponseDto> toResponseList(List<AttendanceRecord> entities);

    // Map từ Request DTO sang Entity
    @Mapping(target = "user", ignore = true) // sẽ set trong service
    @Mapping(target = "attendanceSession", ignore = true) // sẽ set trong service
    AttendanceRecord toEntity(AttendanceRecordRequestDto request);
}
