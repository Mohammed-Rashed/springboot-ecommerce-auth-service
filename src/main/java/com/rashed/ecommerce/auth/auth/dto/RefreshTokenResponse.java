package com.rashed.ecommerce.auth.auth.dto;

public record RefreshTokenResponse(
        String accessToken,
        String tokenType
) {
}