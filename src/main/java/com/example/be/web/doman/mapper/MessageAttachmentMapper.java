package com.example.be.web.doman.mapper;

import com.example.be.web.doman.dto.response.chat.MessageAttachmentResponseDto;
import com.example.be.web.doman.entity.MessageAttachment;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageAttachmentMapper {

    MessageAttachmentResponseDto toResponse(MessageAttachment entity);
}
