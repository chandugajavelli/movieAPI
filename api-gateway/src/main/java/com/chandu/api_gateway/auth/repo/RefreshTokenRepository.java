package com.chandu.api_gateway.auth.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.chandu.api_gateway.auth.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
