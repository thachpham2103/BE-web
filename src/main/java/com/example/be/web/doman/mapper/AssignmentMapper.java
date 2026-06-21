package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.request.assignment.AssignmentRequestDto;
import com.example.be.web.doman.dto.response.assignment.AssignmentResponseDto;
import com.example.be.web.doman.entity.Assignment;
import org.mapstruct.*;

import java.util.List;

/**
 * MapStruct mapper chuyển đổi giữa {@link Assignment} entity và DTO.
 *
 * @author auto-generated
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AssignmentMapper {

    /**
     * Chuyển từ request DTO sang entity. Bỏ qua các field quan hệ sẽ set thủ công.
     *
     * @param dto request DTO
     * @return entity Assignment
     */
    @Mapping(target = "assignmentId", ignore = true)
    @Mapping(target = "classRoom", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "submissions", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Assignment toEntity(AssignmentRequestDto dto);

    /**
     * Chuyển từ entity sang response DTO.
     *
     * @param entity Assignment entity
     * @return response DTO
     */
    @Mapping(source = "classRoom.classId", target = "classId")
    @Mapping(source = "classRoom.title", target = "className")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.fullName", target = "createdByName")
    @Mapping(source = "createDate", target = "createdAt")
    @Mapping(source = "lastModifiedDate", target = "updatedAt")
    @Mapping(target = "submissionCount", ignore = true)
    AssignmentResponseDto toResponse(Assignment entity);

    /**
     * Chuyển danh sách entity sang danh sách response DTO.
     *
     * @param entities danh sách entity
     * @return danh sách response DTO
     */
    List<AssignmentResponseDto> toResponses(List<Assignment> entities);

    /**
     * Cập nhật entity từ request DTO (partial update).
     *
     * @param dto    request DTO chứa dữ liệu mới
     * @param entity entity cần cập nhật
     */
    @Mapping(target = "assignmentId", ignore = true)
    @Mapping(target = "classRoom", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "submissions", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    void updateEntityFromDto(AssignmentRequestDto dto, @MappingTarget Assignment entity);
}
