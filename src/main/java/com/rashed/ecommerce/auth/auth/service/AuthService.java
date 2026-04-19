package com.rashed.ecommerce.auth.auth.service;

import com.rashed.ecommerce.auth.auth.dto.RegisterRequest;
import com.rashed.ecommerce.auth.auth.dto.RegisterResponse;
import com.rashed.ecommerce.auth.common.exception.ConflictException;
import com.rashed.ecommerce.auth.user.Role;
import com.rashed.ecommerce.auth.user.User;
import com.rashed.ecommerce.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request) {
        String email = request.email();
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ConflictException("Email already exists");
        }
        User user = User.builder()
                .name(request.name())
                .email(email)
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
}
