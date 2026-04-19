package com.rashed.ecommerce.auth.auth.controller;

import com.rashed.ecommerce.auth.auth.dto.LoginRequest;
import com.rashed.ecommerce.auth.auth.dto.LoginResponse;
import com.rashed.ecommerce.auth.auth.dto.RegisterRequest;
import com.rashed.ecommerce.auth.auth.dto.RegisterResponse;
import com.rashed.ecommerce.auth.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

}
