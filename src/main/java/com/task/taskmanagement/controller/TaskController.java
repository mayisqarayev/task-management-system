package com.task.taskmanagement.controller;

import com.task.taskmanagement.dto.request.AddTaskRequestDto;
import com.task.taskmanagement.dto.request.UpdateTaskRequestDto;
import com.task.taskmanagement.dto.response.AllTasksResponseDto;
import com.task.taskmanagement.dto.response.PageResponseDto;
import com.task.taskmanagement.dto.response.TaskResponseDto;
import com.task.taskmanagement.entity.enums.TaskPriority;
import com.task.taskmanagement.entity.enums.TaskStatus;
import com.task.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponseDto<AllTasksResponseDto> getAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) TaskStatus taskStatus,
            @RequestParam(required = false) TaskPriority taskPriority
    ) {
        return service.getAll(pageNumber, pageSize, taskStatus, taskPriority);
    }

    @GetMapping("/{id}")
    public TaskResponseDto getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTask(@RequestBody @Valid AddTaskRequestDto requestDto) {
        service.saveTask(requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Integer id,
                           @RequestBody @Valid UpdateTaskRequestDto requestDto) {
        service.updateTask(id, requestDto);
    }
}
