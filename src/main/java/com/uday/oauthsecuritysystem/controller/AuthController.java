package com.uday.oauthsecuritysystem.controller;

import com.uday.oauthsecuritysystem.dto.request.CreateUserRequest;
import com.uday.oauthsecuritysystem.dto.request.LoginRequest;
import com.uday.oauthsecuritysystem.dto.request.RefreshTokenRequest;
import com.uday.oauthsecuritysystem.dto.response.AuthResponse;
import com.uday.oauthsecuritysystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(
            @Valid
            @RequestBody CreateUserRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(
            @RequestBody RefreshTokenRequest request) {

        System.out.println("REFRESH API HIT");

        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public String logout(
            @RequestBody
            RefreshTokenRequest request) {

        authService.logout(request);

        return "Logged out successfully";
    }
}