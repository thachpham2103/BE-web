package com.example.be.web.doman.dto.response.auth;

import com.example.be.web.constant.CommonConstant;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoginResponseDto {

    private String tokenType = CommonConstant.BEARER_TOKEN;

    private String accessToken;

    private String refreshToken;

    private Long id;

    private String username;

    private boolean firstLogin;

    private Collection<? extends GrantedAuthority> authorities;

    public LoginResponseDto(String accessToken, String refreshToken, Long id, boolean firstLogin, String username, Collection<? extends GrantedAuthority> authorities) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.firstLogin = firstLogin;
        this.authorities = authorities;
        this.username=username;
    }
}
