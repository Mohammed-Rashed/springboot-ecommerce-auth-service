package com.rashed.ecommerce.auth.refreshtoken.repository;

import com.rashed.ecommerce.auth.refreshtoken.entity.RefreshToken;
import com.rashed.ecommerce.auth.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
