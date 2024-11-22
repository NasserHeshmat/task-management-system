package com.demo.task.management.service.impl;

import com.demo.task.management.exception.CustomException;
import com.demo.task.management.model.AuthenticationRequest;
import com.demo.task.management.model.AuthenticationResponse;
import com.demo.task.management.model.RefreshTokenRequest;
import com.demo.task.management.model.RefreshTokenResponse;
import com.demo.task.management.service.AuthenticationService;
import com.demo.task.management.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.demo.task.management.constant.ErrorMessages.*;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final Set<String > usedRefreshTokens = new HashSet<>();
    @Override
    public AuthenticationResponse createAuthenticationTokens(AuthenticationRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new CustomException(INVALID_CREDENTIALS, HttpStatus.FORBIDDEN);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

        return AuthenticationResponse.builder()
                .token(jwtUtil.generateToken(userDetails.getUsername(),false))
                .refreshToken(jwtUtil.generateToken(userDetails.getUsername(),true))
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        if (jwtUtil.isTokenExpired(refreshTokenRequest.getRefreshToken()) || !jwtUtil.isRefreshToken(refreshTokenRequest.getRefreshToken())) {
            throw new CustomException(INVALID_TOKEN, HttpStatus.FORBIDDEN);
        }
        if (usedRefreshTokens.contains(refreshTokenRequest.getRefreshToken())) {
            throw new CustomException(USED_REFRESH_TOKEN, HttpStatus.FORBIDDEN);
        }
        usedRefreshTokens.add(refreshTokenRequest.getRefreshToken());

        String username = jwtUtil.extractUsername(refreshTokenRequest.getRefreshToken());
        return new RefreshTokenResponse(jwtUtil.generateToken(username, false));

    }
}
