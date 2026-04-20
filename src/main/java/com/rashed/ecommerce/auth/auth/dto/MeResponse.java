package com.rashed.ecommerce.auth.auth.dto;

import com.rashed.ecommerce.auth.user.entity.Role;

import java.time.LocalDateTime;

public record MeResponse(
        Long id,
        String name,
        String email,
        Role role,
        LocalDateTime createdAt
) {
}