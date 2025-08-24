package com.example.be.web.doman.mapper;

import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.request.UserCreateDto;
import com.example.be.web.doman.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface UserMapper {

    @Mapping(source = "role.name", target = "roleName")
    UserResponseDto toUserResponseDto(User user);

    @Mapping(target = "role", ignore = true)
    @Mapping(source = "email", target = "email")
    User toUser(UserCreateDto userCreateDto);

    List<UserResponseDto> toUserResponseDtos(List<User> user);

}
