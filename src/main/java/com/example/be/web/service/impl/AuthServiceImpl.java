package com.example.be.web.service.impl;

import com.example.be.web.doman.request.LoginRequest;
import com.example.be.web.doman.request.RefreshRequest;
import com.example.be.web.doman.response.JwtResponse;
import com.example.be.web.security.JwtUtil;
import com.example.be.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        // xác thực username + password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // load user từ DB
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        // sinh token
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse refresh(RefreshRequest request) {
        String username = jwtUtil.extractUsername(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String newAccessToken = jwtUtil.generateAccessToken(userDetails);

        return new JwtResponse(newAccessToken, request.getRefreshToken());
    }
}

