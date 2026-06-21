package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.request.assignment.AssignmentSubmissionRequestDto;
import com.example.be.web.doman.dto.response.assignment.AssignmentSubmissionResponseDto;
import com.example.be.web.doman.entity.AssignmentSubmission;
import org.mapstruct.*;

import java.util.List;

/**
 * MapStruct mapper chuyển đổi giữa {@link AssignmentSubmission} entity và DTO.
 *
 * @author auto-generated
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AssignmentSubmissionMapper {

    /**
     * Chuyển từ request DTO sang entity.
     *
     * @param dto request DTO
     * @return entity AssignmentSubmission
     */
    @Mapping(target = "submissionId", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "score", ignore = true)
    @Mapping(target = "teacherComment", ignore = true)
    @Mapping(target = "gradedBy", ignore = true)
    @Mapping(target = "gradedAt", ignore = true)
    AssignmentSubmission toEntity(AssignmentSubmissionRequestDto dto);

    /**
     * Chuyển từ entity sang response DTO.
     *
     * @param entity AssignmentSubmission entity
     * @return response DTO
     */
    @Mapping(source = "assignment.assignmentId", target = "assignmentId")
    @Mapping(source = "assignment.title", target = "assignmentTitle")
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.fullName", target = "studentName")
    @Mapping(source = "gradedBy.id", target = "gradedById")
    @Mapping(source = "gradedBy.fullName", target = "gradedByName")
    AssignmentSubmissionResponseDto toResponse(AssignmentSubmission entity);

    /**
     * Chuyển danh sách entity sang danh sách response DTO.
     *
     * @param entities danh sách entity
     * @return danh sách DTO
     */
    List<AssignmentSubmissionResponseDto> toResponses(List<AssignmentSubmission> entities);
}
