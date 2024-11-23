package banquemisr.challenge05.task.management.service;

import banquemisr.challenge05.task.management.model.AuthenticationRequest;
import banquemisr.challenge05.task.management.model.AuthenticationResponse;
import banquemisr.challenge05.task.management.model.RefreshTokenRequest;
import banquemisr.challenge05.task.management.model.RefreshTokenResponse;

public interface AuthenticationService {
    AuthenticationResponse createAuthenticationTokens(AuthenticationRequest authRequest);

    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
