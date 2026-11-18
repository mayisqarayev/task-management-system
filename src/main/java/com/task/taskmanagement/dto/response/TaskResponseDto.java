package com.task.taskmanagement.dto.response;

import com.task.taskmanagement.entity.enums.TaskPriority;
import com.task.taskmanagement.entity.enums.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
public class TaskResponseDto {
    Integer id;
    String title;
    String description;
    TaskStatus taskStatus;
    TaskPriority taskPriority;
    Short taskWeight;
    LocalDateTime createdAt;
}
