package com.task.taskmanagement.dto.request;

import com.task.taskmanagement.entity.enums.TaskPriority;
import com.task.taskmanagement.entity.enums.TaskStatus;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTaskRequestDto {
    @NotBlank(message = "Title can not be blank")
    String title;
    @NotBlank(message = "Description can not be blank")
    String description;
    @NotNull(message = "Status can not be null")
    TaskStatus taskStatus;
    @NotNull(message = "Priority can not be null")
    TaskPriority taskPriority;
    @Min(value = 1, message = "Weight must be least 1")
    @Max(value = 10, message = "Weight must be most 10")
    Short taskWeight;
}
