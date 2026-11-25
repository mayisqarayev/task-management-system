package com.task.taskmanagement.service;

import com.task.taskmanagement.dto.request.AddUserRequestDto;
import com.task.taskmanagement.dto.request.AllUserResponseDto;
import com.task.taskmanagement.dto.request.ChangePasswordRequestDto;
import com.task.taskmanagement.dto.request.UpdateUserRequestDto;
import com.task.taskmanagement.dto.response.PageResponseDto;
import com.task.taskmanagement.dto.response.UserResponseDto;
import com.task.taskmanagement.entity.UserEntity;
import com.task.taskmanagement.exception.*;
import com.task.taskmanagement.mapper.UserMapper;
import com.task.taskmanagement.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userConverter;

    public UserService(UserRepository userRepository, UserMapper userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public void saveUser(AddUserRequestDto requestDto) {
        String username = requestDto.getUsername().trim().toLowerCase();
        String email = requestDto.getEmail().trim().toLowerCase();

        if(userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if(userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        userRepository.save(userConverter.toEntityFromAddUserRequestDto(requestDto));
    }

    public void updateUser(Integer id, UpdateUserRequestDto requestDto) {
        UserEntity entity = getUserEntityById(id);

        String username = requestDto.getUsername().trim().toLowerCase();
        String email = requestDto.getEmail().trim().toLowerCase();
        String firstName = requestDto.getFirstName().trim();
        String lastName = requestDto.getLastName().trim();

        if(userRepository.existsByUsernameAndIdNot(username, id)) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if(userRepository.existsByEmailAndIdNot(email, id)) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        entity.setUsername(username);
        entity.setEmail(email);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);

        userRepository.save(entity);
    }

    public PageResponseDto<AllUserResponseDto> getAll(
            int pageNumber,
            int pageSize
    ) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Direction.ASC, "id")
        );

        Page<UserEntity> all = userRepository.findAllByIsEnabledTrue(pageable);
        List<AllUserResponseDto> elements = all.getContent().stream()
                .map(userConverter::toAllUserResponseDtoFromEntity)
                .collect(Collectors.toList());

        return new PageResponseDto<>(
                elements,
                all.getNumber(),
                all.getSize(),
                all.getTotalElements(),
                all.getTotalPages(),
                all.hasNext()
        );
    }

    public void deleteUserById(Integer id) {
        UserEntity entity = getUserEntityById(id);
        if(!entity.isEnabled()) {
            throw new UserAlreadyDeletedException("User already deleted");
        }

        entity.setEnabled(false);
        userRepository.save(entity);
    }

    public void activateUserById(Integer id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(entity.isEnabled()) {
            throw new UserAlreadyActiveException("User already active");
        }

        entity.setEnabled(true);
        userRepository.save(entity);
    }

    public void changePassword(Integer id, ChangePasswordRequestDto requestDto) {
        UserEntity entity = getUserEntityById(id);

        if(!entity.getPassword().equals(requestDto.getOldPassword())) {
            throw new InvalidChangePasswordException("Old password incorrect");
        }

        if(requestDto.getNewPassword().equals(entity.getPassword())) {
            throw new InvalidChangePasswordException("Old and new password can not be equal");
        }

        entity.setPassword(requestDto.getNewPassword());
        userRepository.save(entity);
    }

    public UserResponseDto getUserById(Integer id) {
        UserEntity entity = getUserEntityById(id);
        return userConverter.toUserResponseDtoFromEntity(entity);
    }

    private UserEntity getUserEntityById(Integer id) {
        return userRepository.findByIdAndIsEnabledTrue(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
