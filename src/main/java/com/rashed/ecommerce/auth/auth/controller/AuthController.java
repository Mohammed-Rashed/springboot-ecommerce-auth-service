package com.rashed.ecommerce.auth.auth.controller;

import com.rashed.ecommerce.auth.auth.dto.*;
import com.rashed.ecommerce.auth.auth.service.AuthService;
import com.rashed.ecommerce.auth.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @GetMapping("/me")
    public MeResponse me(@AuthenticationPrincipal User currentUser) {
        return authService.me(currentUser);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse login(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}
