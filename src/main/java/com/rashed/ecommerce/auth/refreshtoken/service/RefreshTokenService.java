package com.rashed.ecommerce.auth.refreshtoken.service;

import com.rashed.ecommerce.auth.auth.dto.RefreshTokenRequest;
import com.rashed.ecommerce.auth.common.exception.UnauthorizedException;
import com.rashed.ecommerce.auth.refreshtoken.entity.RefreshToken;
import com.rashed.ecommerce.auth.refreshtoken.repository.RefreshTokenRepository;
import com.rashed.ecommerce.auth.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;


    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusNanos(refreshTokenExpiration * 1_000_000))
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }

    public RefreshToken verfiyExpiration(RefreshTokenRequest token) {
        RefreshToken refreshToken = findByToken(token.refreshToken());
        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now()) || refreshToken.isRevoked()) {
            refreshTokenRepository.delete(refreshToken);
            throw new UnauthorizedException("Refresh token has expired or revoked. Please login again.");
        }
        return refreshToken;
    }

    public void revokeRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }
}
