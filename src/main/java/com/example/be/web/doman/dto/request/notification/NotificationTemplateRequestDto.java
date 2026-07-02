package com.example.be.web.doman.dto.request.notification;

import com.example.be.web.doman.model.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Request body tạo template thông báo")
public class NotificationTemplateRequestDto {

    @NotBlank(message = "Tên template không được để trống")
    @Schema(description = "Tên template", example = "assignment_new")
    private String name;

    @NotBlank(message = "Template tiêu đề không được để trống")
    @Schema(description = "Template tiêu đề", example = "Bài tập mới: {title}")
    private String titleTemplate;

    @NotBlank(message = "Template nội dung không được để trống")
    @Schema(description = "Template nội dung")
    private String bodyTemplate;

    @Schema(description = "Loại thông báo")
    private NotificationType type;
}
