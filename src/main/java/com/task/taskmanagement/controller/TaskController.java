package com.task.taskmanagement.controller;

import com.task.taskmanagement.dto.request.AddTaskRequestDto;
import com.task.taskmanagement.dto.request.UpdateTaskRequestDto;
import com.task.taskmanagement.dto.response.AllTasksResponseDto;
import com.task.taskmanagement.dto.response.TaskResponseDto;
import com.task.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<AllTasksResponseDto> getAll() {
        return service.getAll();
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
