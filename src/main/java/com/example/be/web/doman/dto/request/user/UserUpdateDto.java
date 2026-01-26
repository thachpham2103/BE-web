package com.example.be.web.doman.dto.request.user;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserUpdateDto {

    private String username;

    private String fullName;

    private String email;

    private String Role;

    private String gender;

    private String birthday;

    private String avatarUrl;


}
