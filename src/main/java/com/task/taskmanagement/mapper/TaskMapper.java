package com.task.taskmanagement.mapper;

import com.task.taskmanagement.dto.request.AddTaskRequestDto;
import com.task.taskmanagement.dto.response.AllTasksResponseDto;
import com.task.taskmanagement.dto.response.TaskResponseDto;
import com.task.taskmanagement.entity.TaskEntity;
import com.task.taskmanagement.entity.enums.TaskStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {
    public TaskResponseDto toTaskResponseDtoFromEntity(TaskEntity taskEntity) {
        return new TaskResponseDto(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getTaskStatus(),
                taskEntity.getTaskPriority(),
                taskEntity.getTaskWeight(),
                taskEntity.getCreatedAt()
        );
    }

    public AllTasksResponseDto toAllTasksResponseDtoFromEntity(TaskEntity taskEntity) {
        return new AllTasksResponseDto(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getTaskStatus()
        );
    }

    public TaskEntity toEntityFromAddTaskRequestDto(AddTaskRequestDto requestDto) {
        TaskEntity entity = new TaskEntity();
        entity.setTitle(requestDto.getTitle());
        entity.setDescription(requestDto.getDescription());
        entity.setTaskStatus(TaskStatus.TODO);
        entity.setTaskPriority(requestDto.getTaskPriority());
        entity.setTaskWeight(requestDto.getTaskWeight());
        entity.setStatus(true);
        entity.setCreatedAt(LocalDateTime.now());

        return entity;
    }
}
