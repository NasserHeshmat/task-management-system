package banquemisr.challenge05.task.management.service.impl;

import banquemisr.challenge05.task.management.exception.CustomException;
import banquemisr.challenge05.task.management.model.AuthenticationRequest;
import banquemisr.challenge05.task.management.model.AuthenticationResponse;
import banquemisr.challenge05.task.management.model.RefreshTokenRequest;
import banquemisr.challenge05.task.management.model.RefreshTokenResponse;
import banquemisr.challenge05.task.management.repository.UserRepository;
import banquemisr.challenge05.task.management.service.AuthenticationService;
import banquemisr.challenge05.task.management.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static banquemisr.challenge05.task.management.constant.ErrorMessages.*;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    @Override
    public AuthenticationResponse createAuthenticationTokens(AuthenticationRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new CustomException(INVALID_CREDENTIALS, HttpStatus.FORBIDDEN);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

        return AuthenticationResponse.builder()
                .token(jwtUtil.generateToken(userDetails.getUsername(),false,roles))
                .refreshToken(jwtUtil.generateToken(userDetails.getUsername(),true,roles))
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        if (jwtUtil.isTokenExpired(refreshTokenRequest.getRefreshToken()) || !jwtUtil.isRefreshToken(refreshTokenRequest.getRefreshToken())) {
            throw new CustomException(INVALID_TOKEN, HttpStatus.FORBIDDEN);
        }
        List<String> roles = jwtUtil.extractRoles(refreshTokenRequest.getRefreshToken());

        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        String username = jwtUtil.extractUsername(refreshTokenRequest.getRefreshToken());
        return new RefreshTokenResponse(jwtUtil.generateToken(username, false,authorities));

    }

}
