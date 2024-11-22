package com.demo.task.management.service;

import com.demo.task.management.model.AuthenticationRequest;
import com.demo.task.management.model.AuthenticationResponse;
import com.demo.task.management.model.RefreshTokenRequest;
import com.demo.task.management.model.RefreshTokenResponse;

public interface AuthenticationService {
    AuthenticationResponse createAuthenticationTokens(AuthenticationRequest authRequest);

    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
