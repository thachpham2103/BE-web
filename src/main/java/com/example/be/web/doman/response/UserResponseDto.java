package com.example.be.web.doman.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserResponseDto {

    private String id;

    private String username;

    private String fullName;

    private String roleName;

    private String email;

    private String avatarUrl;

    private String birthday;

    private String gender;
}
