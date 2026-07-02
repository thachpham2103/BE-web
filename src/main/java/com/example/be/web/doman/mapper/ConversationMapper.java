package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.chat.ConversationResponseDto;
import com.example.be.web.doman.entity.Conversation;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConversationMapper {

    @Mapping(source = "classRoom.classId", target = "classId")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.fullName", target = "createdByName")
    @Mapping(target = "memberCount", ignore = true)
    @Mapping(target = "members", ignore = true)
    ConversationResponseDto toResponse(Conversation entity);
}
