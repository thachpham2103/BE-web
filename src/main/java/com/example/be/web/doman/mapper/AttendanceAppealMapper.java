package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.attendance.AttendanceAppealResponseDto;
import com.example.be.web.doman.entity.AttendanceAppeal;
import org.mapstruct.*;

import java.util.List;

/**
 * MapStruct mapper cho {@link AttendanceAppeal}.
 *
 * @author auto-generated
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AttendanceAppealMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.fullName", target = "studentName")
    @Mapping(source = "attendanceSession.sessionId", target = "sessionId")
    @Mapping(source = "attendanceSession.title", target = "sessionTitle")
    @Mapping(source = "attendanceRecord.recordId", target = "recordId")
    @Mapping(source = "reviewedBy.id", target = "reviewedById")
    @Mapping(source = "reviewedBy.fullName", target = "reviewedByName")
    @Mapping(source = "createDate", target = "createdAt")
    AttendanceAppealResponseDto toResponse(AttendanceAppeal entity);

    List<AttendanceAppealResponseDto> toResponses(List<AttendanceAppeal> entities);
}
