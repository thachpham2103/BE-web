package com.example.be.web.service;


import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.request.UserCreateDto;
import com.example.be.web.doman.request.UserUpdateDto;
import com.example.be.web.doman.response.UserResponseDto;

public interface UserService {

 UserCreateDto createUser(UserCreateDto userCreateDto);
 UserResponseDto getUserById(Long userId);
 void deleteUsers(Long id);
 User updateUser(Long id, UserUpdateDto updateDto);
}
