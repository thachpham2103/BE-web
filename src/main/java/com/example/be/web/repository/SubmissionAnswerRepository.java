package com.example.be.web.repository;

import com.example.be.web.doman.entity.ContestSubmission;
import com.example.be.web.doman.entity.Question;
import com.example.be.web.doman.entity.SubmissionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionAnswerRepository extends JpaRepository<SubmissionAnswer, Long> {

    List<SubmissionAnswer> findAllBySubmission(ContestSubmission submission);

    Optional<SubmissionAnswer> findBySubmissionAndQuestion(ContestSubmission submission, Question question);

    List<SubmissionAnswer> findAllByQuestion(Question question);
}