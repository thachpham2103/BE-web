package com.example.be.web.repository;

import com.example.be.web.doman.entity.AnswerOption;
import com.example.be.web.doman.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {

    List<AnswerOption> findAllByQuestionOrderByOrderIndexAsc(Question question);

    List<AnswerOption> findAllByQuestionAndIsCorrectTrue(Question question);
}