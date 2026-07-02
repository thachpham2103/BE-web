package com.example.be.web.repository;

import com.example.be.web.doman.entity.LearningTask;
import com.example.be.web.doman.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningTaskRepository extends JpaRepository<LearningTask, Long> {

    Page<LearningTask> findByClassRoom_ClassId(Long classId, Pageable pageable);

    List<LearningTask> findByClassRoom_ClassIdAndStatus(Long classId, TaskStatus status);

    Page<LearningTask> findByCreatedBy_Id(Long userId, Pageable pageable);
}
