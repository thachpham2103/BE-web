package com.example.be.web.security;

import com.example.be.web.constant.ErrorMessage;
import com.example.be.web.exception.extended.InvalidException;
import com.example.be.web.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final String CLAIM_TYPE = "type";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";
    private static final String USERNAME_KEY = "username";
    private static final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access.expiration_time}")
    private long EXPIRATION_TIME_ACCESS_TOKEN;

    @Value("${jwt.refresh.expiration_time}")
    private long EXPIRATION_TIME_REFRESH_TOKEN;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /* ===================== COMMON ===================== */

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /* ===================== GENERATE TOKEN ===================== */

    public String generateToken(UserPrincipal userPrincipal, boolean isRefreshToken) {

        String authorities = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long expirationMinutes = isRefreshToken
                ? EXPIRATION_TIME_REFRESH_TOKEN
                : EXPIRATION_TIME_ACCESS_TOKEN;

        return Jwts.builder()
                .setSubject(userPrincipal.getId().toString())
                .claim(CLAIM_TYPE, isRefreshToken ? TYPE_REFRESH : TYPE_ACCESS)
                .claim(USERNAME_KEY, userPrincipal.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMinutes * 60 * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /* ===================== AUTHENTICATION ===================== */

    public Authentication getAuthenticationByRefreshToken(String refreshToken) {
        Claims claims = getClaims(refreshToken);

        String type = String.valueOf(claims.get(CLAIM_TYPE));
        if (!TYPE_REFRESH.equals(type)
                || ObjectUtils.isEmpty(claims.get(AUTHORITIES_KEY))
                || ObjectUtils.isEmpty(claims.get(USERNAME_KEY))) {
            throw new InvalidException(ErrorMessage.Auth.INVALID_REFRESH_TOKEN);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserPrincipal principal = (UserPrincipal)
                customUserDetailsService.loadUserById(Long.valueOf(claims.getSubject()));

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    /* ===================== EXTRACT CLAIM ===================== */

    public String extractClaimUsername(String token) {
        return getClaims(token).get(USERNAME_KEY).toString();
    }

    public String extractSubjectFromJwt(String token) {
        return getClaims(token).getSubject();
    }

    public Date extractExpirationFromJwt(String token) {
        return getClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationFromJwt(token).before(new Date());
    }

    /* ===================== VALIDATE ===================== */

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty");
        }
        return false;
    }
}
