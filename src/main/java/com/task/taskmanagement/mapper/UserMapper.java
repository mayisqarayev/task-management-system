package com.task.taskmanagement.mapper;

import com.task.taskmanagement.dto.request.AddUserRequestDto;
import com.task.taskmanagement.dto.response.AllUserResponseDto;
import com.task.taskmanagement.dto.response.UserResponseDto;
import com.task.taskmanagement.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserEntity toEntityFromAddUserRequestDto(AddUserRequestDto requestDto) {
        UserEntity entity = new UserEntity();
        entity.setUsername(requestDto.getUsername().trim().toLowerCase());
        entity.setPassword(requestDto.getPassword());
        entity.setEmail(requestDto.getEmail().trim().toLowerCase());
        entity.setFirstName(null);
        entity.setLastName(null);

        return entity;
    }

    public UserResponseDto toUserResponseDtoFromEntity(UserEntity entity) {
        return new UserResponseDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName()
        );
    }

    public AllUserResponseDto toAllUserResponseDtoFromEntity(UserEntity entity) {
        return new AllUserResponseDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail()
        );
    }
}
