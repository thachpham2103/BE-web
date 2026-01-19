package com.example.be.web.service;

import com.example.be.web.doman.request.LoginRequest;
import com.example.be.web.doman.request.RefreshRequest;
import com.example.be.web.doman.response.JwtResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    JwtResponse refresh(RefreshRequest refreshRequest);
}

