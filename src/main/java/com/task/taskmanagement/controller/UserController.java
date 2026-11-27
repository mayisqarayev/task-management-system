package com.task.taskmanagement.controller;

import com.task.taskmanagement.dto.request.AddUserRequestDto;
import com.task.taskmanagement.dto.response.AllUserResponseDto;
import com.task.taskmanagement.dto.request.ChangePasswordRequestDto;
import com.task.taskmanagement.dto.request.UpdateUserRequestDto;
import com.task.taskmanagement.dto.response.PageResponseDto;
import com.task.taskmanagement.dto.response.UserResponseDto;
import com.task.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody @Valid AddUserRequestDto requestDto) {
        service.saveUser(requestDto);
    }

    @PutMapping("/{id}")
    public void updateUser(
           @PathVariable Integer id,
           @RequestBody @Valid UpdateUserRequestDto requestDto
    ) {
        service.updateUser(id, requestDto);
    }

    @GetMapping
    public PageResponseDto<AllUserResponseDto> getAll(
            @RequestParam(defaultValue = "0")  int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return service.getAll(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Integer id) {
        service.deleteUserById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateUserById(@PathVariable Integer id) {
        service.activateUserById(id);
    }

    @PatchMapping("/password/{id}")
    public void changePassword(
            @PathVariable Integer id,
            @RequestBody @Valid ChangePasswordRequestDto requestDto
    ) {
        service.changePassword(id, requestDto);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Integer id) {
        return service.getUserById(id);
    }
}
