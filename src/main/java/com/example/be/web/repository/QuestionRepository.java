package com.example.be.web.repository;

import com.example.be.web.doman.entity.Contest;
import com.example.be.web.doman.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByContestOrderByOrderIndexAsc(Contest contest);

    long countByContest(Contest contest);
}
