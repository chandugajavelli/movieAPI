package com.chandu.api_gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chandu.api_gateway.auth.entities.RefreshToken;
import com.chandu.api_gateway.auth.entities.User;
import com.chandu.api_gateway.auth.services.AuthService;
import com.chandu.api_gateway.auth.services.JwtService;
import com.chandu.api_gateway.auth.services.RefreshTokenService;
import com.chandu.api_gateway.auth.utils.AuthResponse;
import com.chandu.api_gateway.auth.utils.LoginRequest;
import com.chandu.api_gateway.auth.utils.RefreshTokenRequest;
import com.chandu.api_gateway.auth.utils.RegisterRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }
}