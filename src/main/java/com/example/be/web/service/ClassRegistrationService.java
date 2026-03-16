package com.example.be.web.service;

import com.example.be.web.doman.dto.request.classRoom.ClassRegistrationRequestDto;
import com.example.be.web.doman.dto.response.classRoom.ClassRegistrationResponseDto;
import com.example.be.web.doman.model.RegistrationStatus;
import com.example.be.web.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassRegistrationService {
    ClassRegistrationResponseDto registerStudentToClass(ClassRegistrationRequestDto requestDto);
    ClassRegistrationResponseDto updateRegistrationStatus(Long registrationId, RegistrationStatus status);
    void deleteRegistration(Long registrationId);
    Page<ClassRegistrationResponseDto> getByStudentId (Pageable pageable, Long studentId);
    Page<ClassRegistrationResponseDto> getByClassId (Pageable pageable, Long classId);
    Page<ClassRegistrationResponseDto> getByStudentIdAndStatus(Pageable pageable, Long studentId, RegistrationStatus status);
    Page<ClassRegistrationResponseDto> getByClassIdAndStatus(Pageable pageable, Long classId, RegistrationStatus status);
    Page<ClassRegistrationResponseDto> getRegistrationsByUser(Long userId, Pageable pageable, UserPrincipal principal);
}
