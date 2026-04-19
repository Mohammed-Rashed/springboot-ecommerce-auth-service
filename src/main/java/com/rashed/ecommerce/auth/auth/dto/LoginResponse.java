package com.rashed.ecommerce.auth.auth.dto;

public record LoginResponse(
        String email,
        String name,
        String accessToken,
        String tokenType
) {
}
