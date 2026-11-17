package com.task.taskmanagement.service;

import com.task.taskmanagement.entity.enums.TaskStatus;
import com.task.taskmanagement.exception.InvalidTaskModificationException;
import com.task.taskmanagement.exception.TaskAlreadyDeletedException;
import com.task.taskmanagement.exception.TaskAlreadyExistsException;
import com.task.taskmanagement.exception.TaskNotFoundException;
import com.task.taskmanagement.mapper.TaskMapper;
import com.task.taskmanagement.dto.request.AddTaskRequestDto;
import com.task.taskmanagement.dto.request.UpdateTaskRequestDto;
import com.task.taskmanagement.dto.response.AllTasksResponseDto;
import com.task.taskmanagement.dto.response.TaskResponseDto;
import com.task.taskmanagement.entity.TaskEntity;
import com.task.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final TaskMapper converter;

    public TaskService(TaskRepository repository, TaskMapper converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public List<AllTasksResponseDto> getAll() {
        return repository.findAll().stream()
                .filter(entity -> entity.getStatus().equals(true))
                .map(converter::toAllTasksResponseDtoFromEntity)
                .collect(Collectors.toList());
    }

    public TaskResponseDto getById(Integer id) {
        TaskEntity taskEntity = getEntityById(id);
        return converter.toTaskResponseDtoFromEntity(taskEntity);
    }

    public void saveTask(AddTaskRequestDto requestDto) {
        if(repository.existsByTitleIgnoreCase(requestDto.getTitle())) {
            throw new TaskAlreadyExistsException("Task already exists");
        }
        repository.save(converter.toEntityFromAddTaskRequestDto(requestDto));
    }

    public void deleteById(Integer id) {
        TaskEntity entity = getEntityById(id);

        if(entity.getStatus().equals(false)) {
            throw new TaskAlreadyDeletedException("Task already deleted");
        }

        entity.setStatus(false);
        repository.save(entity);
    }

    public void updateTask(Integer id, UpdateTaskRequestDto requestDto) {
        TaskEntity entity = getEntityById(id);

        if(repository.existsByTitleAndIdNot(requestDto.getTitle(), id)) {
            throw new TaskAlreadyExistsException("Task already exists");
        }

        if(entity.getStatus().equals(false) || entity.getTaskStatus().equals(TaskStatus.DONE)) {
            throw new InvalidTaskModificationException("Invalid task modification");
        }

        entity.setTitle(requestDto.getTitle().trim());
        entity.setDescription(requestDto.getDescription().trim());
        entity.setTaskStatus(requestDto.getTaskStatus());
        entity.setTaskPriority(requestDto.getTaskPriority());
        entity.setTaskWeight(requestDto.getTaskWeight());

        repository.save(entity);
    }

    private TaskEntity getEntityById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }
}
