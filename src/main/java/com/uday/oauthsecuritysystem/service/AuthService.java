package com.uday.oauthsecuritysystem.service;

import com.uday.oauthsecuritysystem.dto.request.CreateUserRequest;
import com.uday.oauthsecuritysystem.dto.request.RefreshTokenRequest;
import com.uday.oauthsecuritysystem.dto.response.AuthResponse;
import com.uday.oauthsecuritysystem.entity.RefreshToken;
import com.uday.oauthsecuritysystem.entity.Role;
import com.uday.oauthsecuritysystem.exception.ResourceNotFoundException;
import com.uday.oauthsecuritysystem.jwt.JwtService;
import com.uday.oauthsecuritysystem.repository.RefreshTokenRepository;
import com.uday.oauthsecuritysystem.repository.RoleRepository;
import com.uday.oauthsecuritysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.uday.oauthsecuritysystem.dto.request.LoginRequest;
import com.uday.oauthsecuritysystem.entity.User;
import jakarta.transaction.Transactional;
import com.uday.oauthsecuritysystem.entity.Tenant;
import com.uday.oauthsecuritysystem.repository.TenantRepository;
import java.time.LocalDateTime;
import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuditLogService auditLogService;
    private final AccountLockService accountLockService;
    private final TenantRepository tenantRepository;

    public AuthResponse register(
            CreateUserRequest request) {

        if (userRepository.findByUsername(
                request.getUsername()).isPresent()) {

            throw new IllegalArgumentException(
                    "Username already exists");
        }

        if (userRepository.findByEmail(
                request.getEmail()).isPresent()) {

            throw new IllegalArgumentException(
                    "Email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        if (request.getTenantId() != null) {

            Tenant tenant =
                    tenantRepository.findById(
                                    request.getTenantId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Tenant not found"));

            user.setTenant(tenant);
        }

        Role role = roleRepository
                .findByRoleName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException(
                                "ROLE_USER not found"));

        user.getRoles().add(role);

        User savedUser =
                userRepository.save(user);

        auditLogService.log(
                savedUser,
                "REGISTER",
                "127.0.0.1",
                "SUCCESS"
        );

        String accessToken =
                jwtService.generateAccessToken(
                        savedUser.getUsername());

        String refreshToken =
                jwtService.generateRefreshToken(
                        savedUser.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    public AuthResponse login(
            LoginRequest request) {

        User user = userRepository
                .findByUsername(
                        request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        if (!accountLockService
                .unlockWhenTimeExpired(user)) {

            throw new RuntimeException(
                    "Account is locked. Try again later.");
        }

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));

        } catch (Exception e) {

            accountLockService.loginFailed(user);

            auditLogService.log(
                    user,
                    "LOGIN_FAILED",
                    "127.0.0.1",
                    "FAILED"
            );

            if (Boolean.TRUE.equals(
                    user.getAccountLocked())) {

                auditLogService.log(
                        user,
                        "ACCOUNT_LOCKED",
                        "127.0.0.1",
                        "FAILED"
                );
            }

            throw new RuntimeException(
                    "Invalid username or password");
        }

        accountLockService.loginSucceeded(user);

        auditLogService.log(
                user,
                "LOGIN",
                "127.0.0.1",
                "SUCCESS"
        );

        String accessToken =
                jwtService.generateAccessToken(
                        user.getUsername());

        String refreshTokenValue =
                jwtService.generateRefreshToken(
                        user.getUsername());

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token(refreshTokenValue)
                        .user(user)
                        .expiryDate(
                                LocalDateTime.now()
                                        .plusDays(7))
                        .revoked(false)
                        .build();

        refreshTokenRepository.save(
                refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenValue)
                .tokenType("Bearer")
                .build();
    }

    public AuthResponse refreshToken(
            RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Refresh token not found"));

        if (Boolean.TRUE.equals(refreshToken.getRevoked())) {
            throw new IllegalArgumentException(
                    "Refresh token revoked");
        }

        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new IllegalArgumentException(
                    "Refresh token expired");
        }

        User user = refreshToken.getUser();

        auditLogService.log(
                user,
                "REFRESH_TOKEN",
                "127.0.0.1",
                "SUCCESS"
        );

        String accessToken =
                jwtService.generateAccessToken(
                        user.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    public void logout(
            RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(
                                request.getRefreshToken())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Refresh token not found"));

        User user = refreshToken.getUser();

        auditLogService.log(
                user,
                "LOGOUT",
                "127.0.0.1",
                "SUCCESS"
        );

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(
                refreshToken);
    }
}
