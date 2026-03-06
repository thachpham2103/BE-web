package com.example.be.web.doman.dto.response.classRoom;

import com.example.be.web.doman.model.RegistrationStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClassRegistrationResponseDto {
    private Long registrationId;
    private Long classId;
    private String classTitle;
    private Long studentId;
    private String studentName;
    private LocalDateTime registeredAt;
    private RegistrationStatus status;
    private boolean pending;
}

