package com.rashed.ecommerce.auth.auth.dto;

import com.rashed.ecommerce.auth.user.Role;

import java.time.LocalDateTime;

public record RegisterResponse(
        Long id,
        String name,
        String email,
        Role role,
        LocalDateTime createdAt
) {
}
