package com.example.be.web.security.jwt;

import com.example.be.web.base.RestData;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.util.BeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class JwtAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        MessageSource messageSource = BeanUtil.getBean(MessageSource.class);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // THÊM: Nội dung mặc định nếu không tìm thấy key trong message source
        String defaultMessage = "Unauthorized: Token is invalid or expired";

        String message;
        try {
            message = messageSource.getMessage(
                    ErrorMessage.UNAUTHORIZED,
                    null,
                    LocaleContextHolder.getLocale()
            );
        } catch (Exception e) {
            // Nếu không tìm thấy key 'exception.unauthorized', dùng message mặc định
            message = defaultMessage;
        }

        // Ghi dữ liệu ra response
        new ObjectMapper().writeValue(response.getOutputStream(), RestData.error(message));
    }

}
