package com.example.be.web.service;

import com.example.be.web.doman.dto.request.auth.LoginRequest;
import com.example.be.web.doman.dto.request.auth.LoginRequestDto;
import com.example.be.web.doman.dto.request.auth.RefreshRequest;
import com.example.be.web.doman.dto.request.auth.TokenRefreshRequestDto;
import com.example.be.web.doman.dto.response.auth.*;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);

//    JwtResponse login(LoginRequest loginRequest);

    JwtResponse refresh(RefreshRequest refreshRequest);

    TokenRefreshResponseDto refresh(TokenRefreshRequestDto request);

    CommonResponseDto logout(HttpServletRequest request);

    TotalResponse getTotal();
}

