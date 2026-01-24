package com.example.be.web.controller;

import com.example.be.web.base.VsResponseUtil;
import com.example.be.web.constant.UrlConstant;
import com.example.be.web.doman.dto.request.auth.LoginRequest;
import com.example.be.web.doman.dto.request.auth.LoginRequestDto;
import com.example.be.web.doman.dto.request.auth.RefreshRequest;
import com.example.be.web.doman.dto.request.auth.TokenRefreshRequestDto;
import com.example.be.web.doman.dto.response.auth.JwtResponse;
import com.example.be.web.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "API Login", description = "Anonymous")
    @PostMapping(UrlConstant.Auth.LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
        return VsResponseUtil.success(authService.login(request));
    }

    @Operation(summary = "API get Access Token from Refresh Token", description = "Anonymous")
    @PostMapping(UrlConstant.Auth.refreshToken)
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequestDto request) {
        return VsResponseUtil.success(authService.refresh(request));
    }

}

