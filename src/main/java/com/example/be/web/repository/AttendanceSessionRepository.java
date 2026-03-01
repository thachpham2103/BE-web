package com.example.be.web.repository;

import com.example.be.web.doman.entity.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {}

