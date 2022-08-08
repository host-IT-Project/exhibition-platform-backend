package com.hostit.exhibitionplatform.domain.auth.jwt.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByUserId(String userId);

    UserRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);
}
