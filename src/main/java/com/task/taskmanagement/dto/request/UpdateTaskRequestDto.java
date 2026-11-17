package com.task.taskmanagement.dto.request;

import com.task.taskmanagement.entity.enums.TaskPriority;
import com.task.taskmanagement.entity.enums.TaskStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTaskRequestDto {
    String title;
    String description;
    TaskStatus taskStatus;
    TaskPriority taskPriority;
    String taskWeight;
}
