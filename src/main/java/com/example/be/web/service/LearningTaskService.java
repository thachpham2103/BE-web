package com.example.be.web.service;

import com.example.be.web.doman.dto.request.task.LearningTaskRequestDto;
import com.example.be.web.doman.dto.response.task.LearningTaskResponseDto;
import com.example.be.web.doman.dto.response.task.StudentTaskProgressResponseDto;
import com.example.be.web.doman.model.TaskProgressStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LearningTaskService {
    LearningTaskResponseDto createTask(LearningTaskRequestDto dto, String username);
    Page<LearningTaskResponseDto> getTasksByClass(Long classId, Pageable pageable);
    LearningTaskResponseDto getTask(Long taskId);
    void cancelTask(Long taskId);

    List<StudentTaskProgressResponseDto> getProgressByTask(Long taskId);
    StudentTaskProgressResponseDto updateProgress(Long taskId, TaskProgressStatus status, String note, String username);
    List<StudentTaskProgressResponseDto> getMyProgress(String username);
}
