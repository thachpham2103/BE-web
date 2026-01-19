package com.example.be.web.service;

import com.example.be.web.doman.dto.request.auth.LoginRequest;
import com.example.be.web.doman.dto.request.auth.RefreshRequest;
import com.example.be.web.doman.dto.response.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    JwtResponse refresh(RefreshRequest refreshRequest);
}

