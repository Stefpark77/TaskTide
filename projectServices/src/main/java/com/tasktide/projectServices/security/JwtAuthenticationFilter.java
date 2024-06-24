package com.tasktide.projectServices.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tasktide.projectServices.security.model.Role;
import com.tasktide.projectServices.security.model.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProperties properties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(this::decode)
                .map(this::convert)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (StringUtils.hasLength(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }

    private DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                .build()
                .verify(token);
    }

    private UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal.builder()
                .userId(jwt.getSubject())
                .username(jwt.getClaim("username").asString())
                .role(Role.valueOf(jwt.getClaim("role").asString()))
                .build();
    }


}
