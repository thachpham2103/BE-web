package com.example.be.web.repository;

import com.example.be.web.doman.entity.ClassRegistration;
import com.example.be.web.doman.entity.ClassRoom;
import com.example.be.web.doman.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRegistrationRepository extends JpaRepository<ClassRegistration, Long> {

    boolean existsByClassEntityAndStudent(ClassRoom classRoom, User student);
}
