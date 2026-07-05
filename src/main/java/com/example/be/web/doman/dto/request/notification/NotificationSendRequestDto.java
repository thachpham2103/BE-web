package com.example.be.web.doman.dto.request.notification;

import com.example.be.web.doman.model.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationSendRequestDto {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "Type cannot be null")
    private NotificationType type;

    private Long classId;

    private String studentId;
}
