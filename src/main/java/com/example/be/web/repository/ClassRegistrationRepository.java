package com.example.be.web.repository;

import com.example.be.web.doman.entity.ClassRegistration;
import com.example.be.web.doman.entity.ClassRoom;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.model.RegistrationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRegistrationRepository extends JpaRepository<ClassRegistration, Long> {

    boolean existsByClassEntityAndStudent(ClassRoom classRoom, User student);

    Page<ClassRegistration> findByStudent_Id(Pageable pageable, Long userId);
    Page<ClassRegistration> findByClassEntity_ClassId(Pageable pageable, Long classId); //ủa nó có quy tắc gì vậy nhỉ
    Page<ClassRegistration> findByStudent_IdAndStatus(Pageable pageable, Long userId, RegistrationStatus status);
    Page<ClassRegistration> findByClassEntity_ClassIdAndStatus(Pageable pageable, Long classId, RegistrationStatus status);
}
