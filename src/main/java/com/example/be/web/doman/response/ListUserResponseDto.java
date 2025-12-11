package com.example.be.web.doman.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor

public class ListUserResponseDto {
    List<UserResponseDto> data;
    Long amountOfAllUsers;
}