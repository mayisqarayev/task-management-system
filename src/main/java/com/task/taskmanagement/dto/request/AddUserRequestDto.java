package com.task.taskmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddUserRequestDto {

    @NotBlank(message = "Username can not be blank")
    @Size(min = 5, max = 40)
    String username;
    @NotBlank(message = "Password can not be blank")
    @Size(min = 8, max = 70)
    String password;
    @Email(message = "Email format not valid")
    @NotBlank(message = "Email can not be blank")
    @Size(max = 255)
    String email;

}
