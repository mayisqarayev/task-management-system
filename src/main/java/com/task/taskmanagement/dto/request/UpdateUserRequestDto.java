package com.task.taskmanagement.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequestDto {
    @NotBlank(message = "Username can not be blank")
    @Size(min = 5, max = 40)
    String username;
    @Email(message = "Email format not valid")
    @NotBlank(message = "Email can not be blank")
    @Size(max = 255)
    String email;
    @NotBlank(message = "First name can not be blank")
    @Size(max = 40)
    String firstName;
    @NotBlank(message = "Last name can not be blank")
    @Size(max = 40)
    String lastName;
}
