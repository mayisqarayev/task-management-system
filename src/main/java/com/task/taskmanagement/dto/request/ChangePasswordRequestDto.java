package com.task.taskmanagement.dto.request;

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
public class ChangePasswordRequestDto {

    @NotBlank(message = "Old password can not be blank")
    String oldPassword;
    @NotBlank(message = "New password can not be blank")
    @Size(min = 8, max = 70)
    String newPassword;

}
