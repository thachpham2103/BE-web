package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.attendance.SessionAttendanceStatsDto;
import com.example.be.web.doman.dto.response.classRoom.ClassRegistrationResponseDto;
import com.example.be.web.doman.entity.AttendanceSession;
import com.example.be.web.doman.entity.ClassRegistration;
import org.mapstruct.*;
import com.example.be.web.doman.model.RecordStatus;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SessionAttendanceStatsMapper {

    @Mapping(target = "sessionId", source = "session.sessionId")
    @Mapping(target = "title", source = "session.title")
        // totalCount và presentCount sẽ set sau
    SessionAttendanceStatsDto toDto(AttendanceSession session);

    @AfterMapping
    default void calculateCounts(AttendanceSession session, @MappingTarget SessionAttendanceStatsDto dto) {
        long total = session.getAttendanceRecords().size();
        long present = session.getAttendanceRecords().stream()
                .filter(r -> r.getRecordStatus() == RecordStatus.PRESENT)
                .count();
        dto.setTotalCount(total);
        dto.setPresentCount(present);
    }

}
