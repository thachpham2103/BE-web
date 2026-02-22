//package com.example.be.web.security;
//
//import com.example.be.web.service.impl.RateLimitService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.time.Duration;
//
//@Component
//public class RateLimitFilter extends OncePerRequestFilter {
//
//    private final RateLimitService rateLimitService;
//
//    public RateLimitFilter(RateLimitService rateLimitService) {
//        this.rateLimitService = rateLimitService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        if (request.getRequestURI().startsWith("/auth/login")) {
//            String clientIp = request.getRemoteAddr();
//            boolean allowed = rateLimitService.isAllowed(clientIp, 5, Duration.ofMinutes(1));
//
//            if (!allowed) {
//                response.setStatus(429); // Too Many Requests
//                response.getWriter().write("Too many login attempts. Please try again later.");
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
//
