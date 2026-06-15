package com.example.be.web.repository;

import com.example.be.web.doman.entity.Contest;
import com.example.be.web.doman.entity.ContestSubmission;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.model.ContestSubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestSubmissionRepository extends JpaRepository<ContestSubmission, Long> {

    List<ContestSubmission> findAllByContestOrderBySubmittedAtDesc(Contest contest);

    List<ContestSubmission> findAllByStudentOrderByStartedAtDesc(User student);

    List<ContestSubmission> findAllByContestAndStudentOrderByAttemptNoDesc(Contest contest, User student);

    Optional<ContestSubmission> findTopByContestAndStudentOrderByAttemptNoDesc(Contest contest, User student);

    long countByContestAndStudent(Contest contest, User student);

    List<ContestSubmission> findAllByStatusOrderBySubmittedAtDesc(ContestSubmissionStatus status);
}