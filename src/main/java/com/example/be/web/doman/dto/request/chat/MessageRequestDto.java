package com.example.be.web.doman.dto.request.chat;

import com.example.be.web.doman.model.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request body gửi tin nhắn")
public class MessageRequestDto {

    @NotNull(message = "ID cuộc hội thoại không được để trống")
    @Schema(description = "ID cuộc hội thoại", example = "1")
    private Long convoId;

    @Schema(description = "Nội dung tin nhắn")
    private String content;

    @Schema(description = "Loại tin nhắn", example = "TEXT")
    private MessageType messageType;
}
