package com.example.be.web.doman.dto.response.notification;

import com.example.be.web.doman.model.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin template thông báo")
public class NotificationTemplateResponseDto {

    @Schema(description = "ID template")
    private Long templateId;
    @Schema(description = "Tên template")
    private String name;
    @Schema(description = "Template tiêu đề")
    private String titleTemplate;
    @Schema(description = "Template nội dung")
    private String bodyTemplate;
    @Schema(description = "Loại thông báo")
    private NotificationType type;
    @Schema(description = "Đang hoạt động")
    private Boolean active;
    @Schema(description = "Ngày tạo")
    private LocalDateTime createdAt;
}
