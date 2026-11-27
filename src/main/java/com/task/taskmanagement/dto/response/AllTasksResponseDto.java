package com.task.taskmanagement.dto.response;

import com.task.taskmanagement.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
@AllArgsConstructor
public class AllTasksResponseDto {
    Integer id;
    String title;
    TaskStatus taskStatus;
    AllUserResponseDto createdBy;
    AllUserResponseDto assignee;
}
