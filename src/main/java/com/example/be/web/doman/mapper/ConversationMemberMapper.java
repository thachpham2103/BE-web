package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.chat.ConversationMemberResponseDto;
import com.example.be.web.doman.entity.ConversationMember;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConversationMemberMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "user.avatarUrl", target = "avatarUrl")
    ConversationMemberResponseDto toResponse(ConversationMember entity);
}
