package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.attendance.AttendanceWarningResponseDto;
import com.example.be.web.doman.entity.AttendanceWarning;
import org.mapstruct.*;

import java.util.List;

/**
 * MapStruct mapper cho {@link AttendanceWarning}.
 *
 * @author auto-generated
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AttendanceWarningMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.fullName", target = "studentName")
    @Mapping(source = "classRoom.classId", target = "classId")
    @Mapping(source = "classRoom.title", target = "className")
    @Mapping(source = "createDate", target = "createdAt")
    AttendanceWarningResponseDto toResponse(AttendanceWarning entity);

    List<AttendanceWarningResponseDto> toResponses(List<AttendanceWarning> entities);
}
