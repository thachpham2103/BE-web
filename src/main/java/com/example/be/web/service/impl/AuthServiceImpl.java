package com.example.be.web.service.impl;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.doman.dto.request.auth.LoginRequestDto;
import com.example.be.web.doman.dto.request.auth.TokenRefreshRequestDto;
import com.example.be.web.doman.dto.response.auth.*;
import com.example.be.web.doman.entity.User;
import com.example.be.web.doman.dto.request.auth.LoginRequest;
import com.example.be.web.doman.dto.request.auth.RefreshRequest;
import com.example.be.web.exception.extended.InternalServerException;
import com.example.be.web.exception.extended.UnauthorizedException;
import com.example.be.web.repository.ClassRepository;
import com.example.be.web.repository.UserRepository;
import com.example.be.web.security.UserPrincipal;
import com.example.be.web.security.jwt.JwtTokenProvider;
//import com.example.be.web.security.jwt.JwtUtil;
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
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserPrincipal userPrincipal;
    private final UserRepository userRepository;
    private final User user;
    private final ClassRepository classRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            // 1. Xác thực username/password với Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // 2. Lưu context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Lấy principal (custom)
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            // 4. Load lại User từ DB
            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(
                    () -> new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME)
            );

            // 5. Sinh JWT
            String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);
            String refreshToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.TRUE);

            // 6. Check first login (có null-check)
            boolean isFirstLogin = false;
            if (user.getLastLogin() != null && user.getCreateDate()!= null) {
                isFirstLogin = user.getLastLogin().equals(user.getCreateDate());
            }

            // 7. Trả về response
            return new LoginResponseDto(
                    accessToken,
                    refreshToken,
                    userPrincipal.getId(),
                    isFirstLogin,
                    authentication.getAuthorities()
            );

        } catch (InternalAuthenticationServiceException | BadCredentialsException e) {
            // Sai username hoặc password
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_USERNAME);
        } catch (UnauthorizedException e) {
            // Ném lại nếu đã có Unauthorized
            throw e;
        } catch (Exception e) {
            log.error("Login failed with unexpected error: {}", e.getMessage(), e);
            throw new InternalServerException(e.getMessage());
        }
    }

    @Override
    public JwtResponse refresh(RefreshRequest refreshRequest) {
        return null;
    }

    @Override
    public TokenRefreshResponseDto refresh(TokenRefreshRequestDto request) {
        Authentication authentication = jwtTokenProvider.getAuthenticationByRefreshToken(request.getRefreshToken());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.generateToken(userPrincipal, Boolean.FALSE);

        return new TokenRefreshResponseDto(accessToken, request.getRefreshToken());
    }

    @Override
    public CommonResponseDto logout(HttpServletRequest request) {
        return null;
    }

    @Override
    public TotalResponse getTotal() {
        Long totalClassCount = classRepository.count();
        Long totalAdminCount = userRepository.countAllByRole_Name("ROLE_ADMIN");
        Long totalUserCount = userRepository.countAllByRole_Name("ROLE_USER");

        return TotalResponse.builder()
                .totalAdmin(totalAdminCount)
                .totalClass(totalClassCount)
                .totalUser(totalUserCount)
                .build();
    }
}

