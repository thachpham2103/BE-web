package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.auth.LoginRequestDto;
import com.example.be.web.doman.dto.request.auth.TokenRefreshRequestDto;
import com.example.be.web.doman.dto.response.auth.*;
import com.example.be.web.doman.entity.User;
import com.example.be.web.exception.extended.InternalServerException;
import com.example.be.web.exception.extended.UnauthorizedException;
import com.example.be.web.repository.ClassRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.security.UserPrincipal;
import com.example.be.web.security.jwt.JwtTokenProvider;
import com.example.be.web.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(
                    () -> new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME)
            );

            String accessToken = jwtTokenProvider.generateToken(userPrincipal, false);
            String refreshToken = jwtTokenProvider.generateToken(userPrincipal, true);

            boolean isFirstLogin = false;
            if (user.getLastLogin() != null && user.getCreateDate() != null) {
                isFirstLogin = user.getLastLogin().equals(user.getCreateDate());
            }

            return new LoginResponseDto(
                    accessToken,
                    refreshToken,
                    userPrincipal.getId(),
                    isFirstLogin,
                    authentication.getAuthorities()
            );

        } catch (InternalAuthenticationServiceException | BadCredentialsException e) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME);
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            log.error("Login failed", e);
            throw new InternalServerException(e.getMessage());
        }
    }

    @Override
    public TokenRefreshResponseDto refresh(TokenRefreshRequestDto request) {
        Authentication authentication = jwtTokenProvider.getAuthenticationByRefreshToken(request.getRefreshToken());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.generateToken(userPrincipal, false);

        return new TokenRefreshResponseDto(accessToken, request.getRefreshToken());
    }

    @Override
    public CommonResponseDto logout(HttpServletRequest request) {
//        SecurityContextHolder.clearContext();
//        return CommonResponseDto.success();
        return null;
    }

    @Override
    public TotalResponse getTotal() {
        return TotalResponse.builder()
                .totalClass(classRepository.count())
                .totalAdmin(userRepository.countAllByRole_Name("ROLE_ADMIN"))
                .totalUser(userRepository.countAllByRole_Name("ROLE_USER"))
                .build();
    }
}
