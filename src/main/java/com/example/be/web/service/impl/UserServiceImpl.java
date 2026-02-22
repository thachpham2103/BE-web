package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.mapper.UserMapper;
import com.example.be.web.doman.model.Role;
import com.example.be.web.doman.dto.request.user.UserCreateDto;
import com.example.be.web.doman.dto.request.user.UserUpdateDto;
import com.example.be.web.doman.dto.response.user.UserResponseDto;
import com.example.be.web.exception.extended.NotFoundException;
import com.example.be.web.repository.RoleRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional

public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserCreateDto createUser(UserCreateDto dto) {
        User user = userMapper.toUser(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role role = roleRepository.findById(dto.getRole().getId())
                .orElseThrow(() -> new RuntimeException(ErrorMessage.ROLE_NOT_FOUND));
        user.setRole(role);

        user.setCreateDate(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());

        userRepository.save(user);

        return dto;
    }


    @Override
    @Cacheable(value = "userDto", key = "id")
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USER_NOT_FOUND_ID, new String[]{userId.toString()}));
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public void deleteUsers(Long id) {
     User user=userRepository.findById(id).orElseThrow(()-> new RuntimeException(ErrorMessage.User.USER_NOT_FOUND_ID));

        if (userRepository.existsById(id)) {
            throw new RuntimeException(ErrorMessage.User.USER_NOT_FOUND_ID);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, UserUpdateDto updateDto) {
           User user= userRepository.findById(id).orElseThrow(()-> new RuntimeException(ErrorMessage.User.USER_NOT_FOUND_ID));
           return userRepository.save(user);
    }

}
