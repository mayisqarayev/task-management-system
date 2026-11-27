package com.task.taskmanagement.service;

import com.task.taskmanagement.dto.response.PageResponseDto;
import com.task.taskmanagement.entity.UserEntity;
import com.task.taskmanagement.entity.enums.TaskPriority;
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
import com.task.taskmanagement.repository.UserRepository;
import com.task.taskmanagement.specification.TaskSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class TaskService {
    private final TaskRepository repository;
    private final TaskMapper converter;
    private final UserService userService;

    public TaskService(TaskRepository repository, TaskMapper converter, UserService userService) {
        this.repository = repository;
        this.converter = converter;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public PageResponseDto<AllTasksResponseDto> getAll(
            int pageNumber,
            int pageSize,
            TaskStatus taskStatus,
            TaskPriority taskPriority
    ) {

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Direction.DESC, "id")
        );
        Specification<TaskEntity> specification = Specification.allOf(
                TaskSpecification.isActive(),
                TaskSpecification.hasTaskStatus(taskStatus),
                TaskSpecification.hasTaskPriority(taskPriority)
        );

        Page<TaskEntity> page = repository.findAll(specification, pageable);
        List<AllTasksResponseDto> elements = page.getContent()
                .stream()
                .map(converter::toAllTasksResponseDtoFromEntity)
                .toList();

        return new PageResponseDto<>(
                elements,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }

    @Transactional(readOnly = true)
    public TaskResponseDto getById(Integer id) {
        TaskEntity taskEntity = repository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return converter.toTaskResponseDtoFromEntity(taskEntity);
    }

    public void saveTask(AddTaskRequestDto requestDto) {
        log.debug("Task saving, title={}", requestDto.getTitle());

        UserEntity createdBy = userService.getUserEntityById(requestDto.getCreatedBy());
        UserEntity assignee = userService.getUserEntityById(requestDto.getAssigneeId());

        if(repository.existsByTitleIgnoreCase(requestDto.getTitle())) {
            log.warn("Task already exists, title={}", requestDto.getTitle());
            throw new TaskAlreadyExistsException("Task already exists");
        }

        TaskEntity convertedEntity = converter.toEntityFromAddTaskRequestDto(requestDto);
        convertedEntity.setCreatedBy(createdBy);
        convertedEntity.setAssignee(assignee);

        TaskEntity entity = repository.save(convertedEntity);
        log.info("Task created, id={}", entity.getId());
    }

    public void deleteById(Integer id) {
        log.debug("Task deleting, id={}", id);
        TaskEntity entity = getEntityById(id);

        if(entity.getStatus().equals(false)) {
            log.warn("Task already deleted, id={}", id);
            throw new TaskAlreadyDeletedException("Task already deleted");
        }

        entity.setStatus(false);
        repository.save(entity);
        log.info("Task deleted, id={}", id);
    }

    public void updateTask(Integer id, UpdateTaskRequestDto requestDto) {
        log.debug("Task updating, id={}", id);
        TaskEntity entity = getEntityById(id);

        if(repository.existsByTitleIgnoreCaseAndIdNot(requestDto.getTitle(), id)) {
            log.warn("Task already exists, id={}, title={}", id, requestDto.getTitle());
            throw new TaskAlreadyExistsException("Task already exists");
        }

        if(entity.getStatus().equals(false) || entity.getTaskStatus().equals(TaskStatus.DONE)) {
            log.warn("Task modification failed, id={}, status={}, taskStatus={}",
                    id, entity.getStatus(), entity.getTaskStatus());
            throw new InvalidTaskModificationException("Invalid task modification");
        }

        entity.setTitle(requestDto.getTitle().trim());
        entity.setDescription(requestDto.getDescription().trim());
        entity.setTaskStatus(requestDto.getTaskStatus());
        entity.setTaskPriority(requestDto.getTaskPriority());
        entity.setTaskWeight(requestDto.getTaskWeight());

        repository.save(entity);
        log.info("Task updated, id={}", id);
    }

    private TaskEntity getEntityById(Integer id) {
        return repository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> {
                    log.warn("Task not found, id={}", id);
                    return new TaskNotFoundException("Task not found");
                });
    }
}
