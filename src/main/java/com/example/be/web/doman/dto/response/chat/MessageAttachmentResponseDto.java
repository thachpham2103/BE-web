package com.example.be.web.doman.dto.response.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin file đính kèm")
public class MessageAttachmentResponseDto {

    @Schema(description = "ID file")
    private Long attachmentId;
    @Schema(description = "URL file")
    private String fileUrl;
    @Schema(description = "Tên file")
    private String fileName;
    @Schema(description = "Kích thước file (bytes)")
    private Long fileSize;
    @Schema(description = "Loại MIME")
    private String mimeType;
    @Schema(description = "Thời điểm upload")
    private LocalDateTime uploadedAt;
}
