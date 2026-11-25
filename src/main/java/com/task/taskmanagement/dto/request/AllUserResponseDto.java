package com.task.taskmanagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllUserResponseDto {
    Integer id;
    String username;
    String email;
}
