package com.example.be.web.security.jwt;

import com.example.be.web.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        try {
            String jwt = getJwtFromRequest(request);

            System.out.println("\n========== JWT FILTER DEBUG ==========");
            System.out.println("REQUEST: " + method + " " + path);
            System.out.println("AUTH HEADER: " + request.getHeader("Authorization"));
            System.out.println("HAS TOKEN: " + StringUtils.hasText(jwt));

            if (StringUtils.hasText(jwt)) {
                System.out.println("TOKEN START: " + (jwt.length() > 25 ? jwt.substring(0, 25) : jwt) + "...");

                boolean valid = tokenProvider.validateToken(jwt);
                System.out.println("TOKEN VALID: " + valid);

                if (valid) {
                    String userId = tokenProvider.extractSubjectFromJwt(jwt);
                    System.out.println("USER ID FROM JWT: " + userId);

                    UserDetails userDetails = customUserDetailsService.loadUserById(Long.valueOf(userId));
                    System.out.println("USER LOADED: " + userDetails.getUsername());
                    System.out.println("AUTHORITIES: " + userDetails.getAuthorities());

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    System.out.println("AUTHENTICATION SET: SUCCESS");
                } else {
                    System.out.println("AUTHENTICATION SET: FAILED because token invalid");
                }
            } else {
                System.out.println("AUTHENTICATION SET: FAILED because token missing");
            }

            System.out.println("CURRENT AUTH: " + SecurityContextHolder.getContext().getAuthentication());
            System.out.println("======================================\n");

        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            System.out.println("\n========== JWT FILTER ERROR ==========");
            System.out.println("REQUEST: " + method + " " + path);
            ex.printStackTrace();
            System.out.println("======================================\n");
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (!StringUtils.hasText(bearerToken)) {
            return null;
        }

        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7).trim();
        }

        return null;
    }
}
