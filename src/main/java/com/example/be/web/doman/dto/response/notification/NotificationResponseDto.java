package com.example.be.web.doman.dto.response.notification;

import com.example.be.web.doman.model.NotificationType;
import com.example.be.web.doman.model.TargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Response thông tin thông báo")
public class NotificationResponseDto {

    @Schema(description = "ID thông báo")
    private Long notifId;
    @Schema(description = "Tiêu đề")
    private String title;
    @Schema(description = "Nội dung")
    private String body;
    @Schema(description = "Đã đọc")
    private Boolean isRead;
    @Schema(description = "Loại thông báo")
    private NotificationType type;
    @Schema(description = "Loại đối tượng")
    private TargetType targetType;
    @Schema(description = "ID đối tượng")
    private Long targetId;
    @Schema(description = "Ngày tạo")
    private LocalDateTime createAt;
}
