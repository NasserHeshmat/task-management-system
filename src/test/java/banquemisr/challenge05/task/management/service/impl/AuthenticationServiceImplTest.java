package banquemisr.challenge05.task.management.service.impl;

import banquemisr.challenge05.task.management.exception.CustomException;
import banquemisr.challenge05.task.management.model.AuthenticationRequest;
import banquemisr.challenge05.task.management.model.RefreshTokenRequest;
import banquemisr.challenge05.task.management.model.RefreshTokenResponse;
import banquemisr.challenge05.task.management.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static banquemisr.challenge05.task.management.constant.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    private final HashSet<String> usedRefreshTokens = new HashSet<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAuthenticationTokens_InvalidCredentials() {
        AuthenticationRequest authRequest = new AuthenticationRequest("testuser", "wrong-password");
        doThrow(new RuntimeException("Invalid credentials")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        CustomException exception = assertThrows(CustomException.class, () -> authenticationService.createAuthenticationTokens(authRequest));
        assertEquals(INVALID_CREDENTIALS, exception.getMessage());
        assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatus());
    }

    @Test
    void testRefreshToken_Success() {
        String refreshToken = "valid-refresh-token";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);
        List<String> roles = Collections.singletonList("ROLE_USER");
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        when(jwtUtil.isTokenExpired(refreshToken)).thenReturn(false);
        when(jwtUtil.isRefreshToken(refreshToken)).thenReturn(true);
        when(jwtUtil.extractRoles(refreshToken)).thenReturn(roles);
        when(jwtUtil.extractUsername(refreshToken)).thenReturn("testuser");
        when(jwtUtil.generateToken("testuser", false, authorities)).thenReturn("new-access-token");

        RefreshTokenResponse response = authenticationService.refreshToken(refreshTokenRequest);

        assertNotNull(response);
        assertEquals("new-access-token", response.getToken());
        verify(jwtUtil, times(1)).extractUsername(refreshToken);
        verify(jwtUtil, times(1)).extractRoles(refreshToken);
    }

    @Test
    void testRefreshToken_ExpiredToken() {
        String expiredToken = "expired-refresh-token";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(expiredToken);

        when(jwtUtil.isTokenExpired(expiredToken)).thenReturn(true);

        CustomException exception = assertThrows(CustomException.class, () -> authenticationService.refreshToken(refreshTokenRequest));
        assertEquals(INVALID_TOKEN, exception.getMessage());
        assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatus());
    }

}
