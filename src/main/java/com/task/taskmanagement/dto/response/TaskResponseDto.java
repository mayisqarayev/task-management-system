package com.task.taskmanagement.dto.response;

import com.task.taskmanagement.entity.UserEntity;
import com.task.taskmanagement.entity.enums.TaskPriority;
import com.task.taskmanagement.entity.enums.TaskStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TaskResponseDto {
    Integer id;
    String title;
    String description;
    TaskStatus taskStatus;
    TaskPriority taskPriority;
    Short taskWeight;
    LocalDateTime createdAt;
    UserResponseDto createdBy;
    UserResponseDto assignee;
}
