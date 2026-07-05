package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.task.LearningTaskRequestDto;
import com.example.be.web.doman.dto.response.task.LearningTaskResponseDto;
import com.example.be.web.doman.dto.response.task.StudentTaskProgressResponseDto;
import com.example.be.web.doman.entity.ClassRoom;
import com.example.be.web.doman.entity.LearningTask;
import com.example.be.web.doman.entity.StudentTaskProgress;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.mapper.LearningTaskMapper;
import com.example.be.web.doman.mapper.StudentTaskProgressMapper;
import com.example.be.web.doman.model.TaskProgressStatus;
import com.example.be.web.doman.model.TaskStatus;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.ClassRepository;
import com.example.be.web.repository.LearningTaskRepository;
import com.example.be.web.repository.StudentTaskProgressRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.service.LearningTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LearningTaskServiceImpl implements LearningTaskService {

    private final LearningTaskRepository learningTaskRepository;
    private final StudentTaskProgressRepository studentTaskProgressRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final LearningTaskMapper learningTaskMapper;
    private final StudentTaskProgressMapper studentTaskProgressMapper;

    @Override
    public LearningTaskResponseDto createTask(LearningTaskRequestDto dto, String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));
        ClassRoom classRoom = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ClassRoom.CLASS_NOT_FOUND));

        LearningTask task = LearningTask.builder()
                .classRoom(classRoom)
                .createdBy(creator)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .targetType(dto.getTargetType())
                .targetId(dto.getTargetId())
                .deadline(dto.getDeadline())
                .build();
        learningTaskRepository.save(task);
        return learningTaskMapper.toResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LearningTaskResponseDto> getTasksByClass(Long classId, Pageable pageable) {
        return learningTaskRepository.findByClassRoom_ClassId(classId, pageable)
                .map(learningTaskMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public LearningTaskResponseDto getTask(Long taskId) {
        LearningTask task = learningTaskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.LearningTask.TASK_NOT_FOUND));
        return learningTaskMapper.toResponse(task);
    }

    @Override
    public void cancelTask(Long taskId) {
        LearningTask task = learningTaskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.LearningTask.TASK_NOT_FOUND));
        task.setStatus(TaskStatus.CANCELLED);
        learningTaskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentTaskProgressResponseDto> getProgressByTask(Long taskId) {
        return studentTaskProgressRepository.findByLearningTask_TaskId(taskId)
                .stream().map(studentTaskProgressMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StudentTaskProgressResponseDto updateProgress(Long taskId, TaskProgressStatus status, String note, String username) {
        User student = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));
        LearningTask task = learningTaskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.LearningTask.TASK_NOT_FOUND));

        StudentTaskProgress progress = studentTaskProgressRepository
                .findByStudent_IdAndLearningTask_TaskId(student.getId(), taskId)
                .orElseGet(() -> StudentTaskProgress.builder()
                        .student(student)
                        .learningTask(task)
                        .build());

        progress.setStatus(status);
        progress.setNote(note);
        if (status == TaskProgressStatus.COMPLETED) {
            progress.setCompletedAt(LocalDateTime.now());
        }
        studentTaskProgressRepository.save(progress);
        return studentTaskProgressMapper.toResponse(progress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentTaskProgressResponseDto> getMyProgress(String username) {
        User student = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND));
        return studentTaskProgressRepository.findByStudent_Id(student.getId())
                .stream().map(studentTaskProgressMapper::toResponse)
                .collect(Collectors.toList());
    }
}
