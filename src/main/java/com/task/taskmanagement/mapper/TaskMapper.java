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
    private final UserMapper userMapper;

    public TaskMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public TaskResponseDto toTaskResponseDtoFromEntity(TaskEntity taskEntity) {
        return new TaskResponseDto(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getTaskStatus(),
                taskEntity.getTaskPriority(),
                taskEntity.getTaskWeight(),
                taskEntity.getCreatedAt(),
                userMapper.toUserResponseDtoFromEntity(taskEntity.getCreatedBy()),
                userMapper.toUserResponseDtoFromEntity(taskEntity.getAssignee())
        );
    }

    public AllTasksResponseDto toAllTasksResponseDtoFromEntity(TaskEntity taskEntity) {
        return new AllTasksResponseDto(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getTaskStatus(),
                userMapper.toAllUserResponseDtoFromEntity(taskEntity.getCreatedBy()),
                userMapper.toAllUserResponseDtoFromEntity(taskEntity.getAssignee())
        );
    }

    public TaskEntity toEntityFromAddTaskRequestDto(AddTaskRequestDto requestDto) {
        TaskEntity entity = new TaskEntity();
        Short taskWeight = requestDto.getTaskWeight() == null ? 0 : requestDto.getTaskWeight();

        entity.setTitle(requestDto.getTitle().trim());
        entity.setDescription(requestDto.getDescription().trim());
        entity.setTaskStatus(TaskStatus.TODO);
        entity.setTaskPriority(requestDto.getTaskPriority());
        entity.setTaskWeight(taskWeight);
        entity.setStatus(true);

        return entity;
    }
}
