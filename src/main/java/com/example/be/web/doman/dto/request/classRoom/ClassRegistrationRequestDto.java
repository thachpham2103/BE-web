package com.example.be.web.doman.dto.request.classRoom;

import com.example.be.web.doman.model.RegistrationStatus;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClassRegistrationRequestDto {
    private Long classId;
    private Long studentId;
//    private RegistrationStatus status; // optional, mặc định PENDING
}

