package com.uday.oauthsecuritysystem.repository;

import com.uday.oauthsecuritysystem.entity.RefreshToken;
import com.uday.oauthsecuritysystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

    List<RefreshToken> findByUser(User user);
}