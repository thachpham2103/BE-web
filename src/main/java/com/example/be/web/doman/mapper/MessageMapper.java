package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.chat.MessageResponseDto;
import com.example.be.web.doman.entity.Message;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MessageAttachmentMapper.class})
public interface MessageMapper {

    @Mapping(source = "conversation.convoId", target = "convoId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.fullName", target = "senderName")
    @Mapping(source = "sender.avatarUrl", target = "senderAvatarUrl")
    MessageResponseDto toResponse(Message entity);
}
