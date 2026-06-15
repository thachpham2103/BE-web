package com.example.be.web.repository;

import com.example.be.web.doman.entity.ClassRoom;
import com.example.be.web.doman.entity.Contest;
import com.example.be.web.doman.entity.Subject;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.model.ContestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    List<Contest> findAllByClassRoomOrderByCreatedAtDesc(ClassRoom classRoom);

    List<Contest> findAllBySubjectOrderByCreatedAtDesc(Subject subject);

    List<Contest> findAllByCreatedByOrderByCreatedAtDesc(User createdBy);

    List<Contest> findAllByStatusOrderByStartTimeDesc(ContestStatus status);
}
