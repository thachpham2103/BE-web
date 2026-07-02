package com.example.be.web.repository;

import com.example.be.web.doman.entity.StudentTaskProgress;
import com.example.be.web.doman.model.TaskProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentTaskProgressRepository extends JpaRepository<StudentTaskProgress, Long> {

    Optional<StudentTaskProgress> findByStudent_IdAndLearningTask_TaskId(Long studentId, Long taskId);

    List<StudentTaskProgress> findByLearningTask_TaskId(Long taskId);

    List<StudentTaskProgress> findByStudent_Id(Long studentId);

    long countByLearningTask_TaskIdAndStatus(Long taskId, TaskProgressStatus status);
}
