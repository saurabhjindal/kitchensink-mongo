package org.jboss.as.quickstarts.kitchensink.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "H+7t94hz7IEZg0GVq2ZpsIgCifOHyz+mDGeCbrLILco="; // should be in vault in production
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    ObjectMapper objectMapper = new ObjectMapper();

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    @SneakyThrows
    public String generateToken(Map<String, String> details) {
        return Jwts.builder()
                .setClaims(details)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 1 day expiration
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Extract full claims (payload) from JWT token
    public Claims getPayload(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Use the correct signing key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public String extractRoles(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("roles", String.class);
    }
}

