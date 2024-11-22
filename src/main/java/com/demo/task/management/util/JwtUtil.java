package com.demo.task.management.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

import static com.demo.task.management.constant.Constants.*;

@Component
public class JwtUtil {

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (List<String>) claims.get("roles");
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, boolean isRefreshToken, Collection<? extends GrantedAuthority> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.stream().map(GrantedAuthority::getAuthority).toArray());
        return createToken(claims, username, isRefreshToken);
    }

    private String createToken(Map<String, Object> claims, String subject, boolean isRefreshToken) {
        Integer expirationPeriod = (isRefreshToken)? REFRESH_EXPIRATION_PERIOD : TOKEN_EXPIRATION_PERIOD;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationPeriod))
                .claim("isRefreshToken", isRefreshToken)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    public boolean isRefreshToken(String token) {
        return (boolean) Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("isRefreshToken");
    }
}

