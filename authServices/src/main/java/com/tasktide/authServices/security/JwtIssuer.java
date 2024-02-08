package com.tasktide.authServices.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tasktide.authServices.security.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    @Value("${security.jwt.secret-key}")
    private String secret;

    public String issue(String userId, String username, Role role) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("username", username)
                .withClaim("role", role.name())
                .sign(Algorithm.HMAC256(secret));
    }
}
