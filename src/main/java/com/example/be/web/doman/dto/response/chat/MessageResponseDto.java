package com.example.be.web.doman.dto.response.chat;

import com.example.be.web.doman.model.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin tin nhắn")
public class MessageResponseDto {

    @Schema(description = "ID tin nhắn")
    private Long messageId;
    @Schema(description = "ID cuộc hội thoại")
    private Long convoId;
    @Schema(description = "ID người gửi")
    private Long senderId;
    @Schema(description = "Tên người gửi")
    private String senderName;
    @Schema(description = "Avatar người gửi")
    private String senderAvatarUrl;
    @Schema(description = "Nội dung")
    private String content;
    @Schema(description = "Loại tin nhắn")
    private MessageType messageType;
    @Schema(description = "Thời gian gửi")
    private LocalDateTime sentAt;
    @Schema(description = "Thời gian chỉnh sửa")
    private LocalDateTime editedAt;
    @Schema(description = "Đã xóa")
    private Boolean deleted;
    @Schema(description = "Danh sách file đính kèm")
    private List<MessageAttachmentResponseDto> attachments;
}
