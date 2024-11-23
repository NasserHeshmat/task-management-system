package banquemisr.challenge05.task.management.controller;

import banquemisr.challenge05.task.management.model.AuthenticationRequest;
import banquemisr.challenge05.task.management.model.AuthenticationResponse;
import banquemisr.challenge05.task.management.model.RefreshTokenRequest;
import banquemisr.challenge05.task.management.model.RefreshTokenResponse;
import banquemisr.challenge05.task.management.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@Valid @RequestBody AuthenticationRequest authRequest) throws Exception {
        return ResponseEntity.ok(authenticationService.createAuthenticationTokens(authRequest));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenResponse response = authenticationService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(response);
    }
}
