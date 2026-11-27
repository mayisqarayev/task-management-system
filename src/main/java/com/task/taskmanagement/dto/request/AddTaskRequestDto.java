package com.task.taskmanagement.dto.request;

import com.task.taskmanagement.entity.enums.TaskPriority;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddTaskRequestDto {
    @NotBlank(message = "Title can not be blank")
    String title;
    @NotEmpty(message = "Description can not be empty")
    String description;
    @NotNull(message = "Priority can not be null")
    TaskPriority taskPriority;
    @Min(value = 1, message = "Weight must be least 1")
    @Max(value = 10, message = "Weight must be most 10")
    Short taskWeight;
    @NotNull(message = "Created by can not be null")
    Integer createdBy;
    @NotNull(message = "Assignee id can not be null")
    Integer assigneeId;
}
