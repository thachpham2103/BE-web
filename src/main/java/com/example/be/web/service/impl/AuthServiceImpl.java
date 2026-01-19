package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.dto.request.auth.LoginRequest;
import com.example.be.web.doman.dto.request.auth.RefreshRequest;
import com.example.be.web.doman.dto.response.auth.JwtResponse;
import com.example.be.web.exception.extended.UnauthorizedException;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.security.UserPrincipal;
import com.example.be.web.security.jwt.JwtTokenProvider;
import com.example.be.web.security.jwt.JwtUtil;
import com.example.be.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserPrincipal userPrincipal;
    private final UserRepository userRepository;
    private final User user;

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
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(
                () -> new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME)
        );

        // sinh token
        String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
        String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);

        // 6. Check first login (có null-check)
        boolean isFirstLogin = false;
        if (user.getLastLogin() != null && user.getCreatedDate() != null) {
            isFirstLogin = user.getLastLogin().equals(user.getCreatedDate());
        }

        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse refresh(RefreshRequest request) {
        String username = jwtTokenProvider.extractUsername(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails);

        return new JwtResponse(newAccessToken, request.getRefreshToken());
    }
}

