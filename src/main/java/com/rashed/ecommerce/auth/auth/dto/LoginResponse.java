package com.rashed.ecommerce.auth.auth.dto;

public record LoginResponse(
        String accessToken,
        String tokenType
) {
}
