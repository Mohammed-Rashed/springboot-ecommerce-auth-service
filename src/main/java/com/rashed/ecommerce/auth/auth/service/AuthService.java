package com.rashed.ecommerce.auth.auth.service;

import com.rashed.ecommerce.auth.auth.dto.*;
import com.rashed.ecommerce.auth.common.exception.ConflictException;
import com.rashed.ecommerce.auth.common.exception.UnauthorizedException;
import com.rashed.ecommerce.auth.refreshtoken.entity.RefreshToken;
import com.rashed.ecommerce.auth.refreshtoken.service.RefreshTokenService;
import com.rashed.ecommerce.auth.security.jwt.JwtService;
import com.rashed.ecommerce.auth.user.entity.Role;
import com.rashed.ecommerce.auth.user.entity.User;
import com.rashed.ecommerce.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public RegisterResponse register(RegisterRequest request) {
        String email = request.email();
        String normalizedEmail=email.trim().toLowerCase();
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new ConflictException("Email already exists");
        }
        User user = User.builder()
                .name(request.name().trim())
                .email(normalizedEmail)
                .password(passwordEncoder.encode(request.password()))
                .role(Role.CUSTOMER)
                .build();
        User savedUser = userRepository.save(user);
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getCreatedAt()
        );
    }
    public LoginResponse login(LoginRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();

        User user = userRepository.findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());

        if (!passwordMatches) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return new LoginResponse(
                user.getEmail(),
                user.getName(),
                accessToken,
                "Bearer",
                refreshToken.getToken()
        );
    }

    public MeResponse me(User currentUser) {
        return new MeResponse(
                currentUser.getId(),
                currentUser.getName(),
                currentUser.getEmail(),
                currentUser.getRole(),
                currentUser.getCreatedAt()
        );
    }

}
