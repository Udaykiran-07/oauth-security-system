package com.uday.oauthsecuritysystem.service;

import com.uday.oauthsecuritysystem.dto.request.ForgotPasswordRequest;
import com.uday.oauthsecuritysystem.dto.request.ResetPasswordRequest;
import com.uday.oauthsecuritysystem.entity.PasswordResetToken;
import com.uday.oauthsecuritysystem.entity.User;
import com.uday.oauthsecuritysystem.repository.PasswordResetTokenRepository;
import com.uday.oauthsecuritysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public String forgotPassword(
            ForgotPasswordRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        String token =
                UUID.randomUUID().toString();

        PasswordResetToken resetToken =
                PasswordResetToken.builder()
                        .token(token)
                        .user(user)
                        .expiryDate(
                                LocalDateTime.now()
                                        .plusMinutes(30))
                        .used(false)
                        .build();

        tokenRepository.save(resetToken);

        return token;
    }

    public String resetPassword(
            ResetPasswordRequest request) {

        PasswordResetToken token =
                tokenRepository.findByToken(
                                request.getToken())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid token"));

        if(Boolean.TRUE.equals(
                token.getUsed())) {

            throw new RuntimeException(
                    "Token already used");
        }

        if(token.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Token expired");
        }

        User user = token.getUser();

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()));

        userRepository.save(user);

        token.setUsed(true);

        tokenRepository.save(token);

        return "Password reset successful";
    }
}