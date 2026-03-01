package com.example.be.web.service;

import com.example.be.web.doman.dto.request.classRoom.ClassRegistrationRequestDto;
import com.example.be.web.doman.dto.response.classRoom.ClassRegistrationResponseDto;
import com.example.be.web.doman.model.RegistrationStatus;

public interface ClassRegistrationService {
    ClassRegistrationResponseDto registerStudentToClass(ClassRegistrationRequestDto requestDto);
    ClassRegistrationResponseDto updateRegistrationStatus(Long registrationId, RegistrationStatus status);
    void deleteRegistration(Long registrationId);
}
