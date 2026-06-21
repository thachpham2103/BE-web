package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.request.attendance.AttendancePolicyRequestDto;
import com.example.be.web.doman.dto.response.attendance.AttendancePolicyResponseDto;
import com.example.be.web.doman.entity.AttendancePolicy;
import org.mapstruct.*;

/**
 * MapStruct mapper cho {@link AttendancePolicy}.
 *
 * @author auto-generated
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AttendancePolicyMapper {

    @Mapping(target = "policyId", ignore = true)
    @Mapping(target = "classRoom", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    AttendancePolicy toEntity(AttendancePolicyRequestDto dto);

    @Mapping(source = "classRoom.classId", target = "classId")
    @Mapping(source = "classRoom.title", target = "className")
    @Mapping(source = "createDate", target = "createdAt")
    @Mapping(source = "lastModifiedDate", target = "updatedAt")
    AttendancePolicyResponseDto toResponse(AttendancePolicy entity);

    @Mapping(target = "policyId", ignore = true)
    @Mapping(target = "classRoom", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    void updateEntityFromDto(AttendancePolicyRequestDto dto, @MappingTarget AttendancePolicy entity);
}
