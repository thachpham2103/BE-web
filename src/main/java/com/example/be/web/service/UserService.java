package com.example.be.web.service;


import com.example.be.web.doman.request.UserCreateDto;
import com.example.be.web.doman.response.ListUserResponseDto;
import com.example.be.web.doman.response.UserResponseDto;

public interface UserService {

 UserCreateDto createUser(UserCreateDto userCreateDto);
 UserResponseDto getUserById(Long userId);
 void deleteUsers(Long id);
}
