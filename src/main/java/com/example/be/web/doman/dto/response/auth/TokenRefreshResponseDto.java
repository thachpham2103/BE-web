package com.example.be.web.doman.dto.response.auth;

import com.example.be.web.constant.CommonConstant;

public class TokenRefreshResponseDto {

    private String tokenType = CommonConstant.BEARER_TOKEN;

    private String accessToken;

    private String refreshToken;

    public TokenRefreshResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }



}
