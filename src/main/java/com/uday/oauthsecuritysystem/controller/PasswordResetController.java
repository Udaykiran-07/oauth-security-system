package com.uday.oauthsecuritysystem.controller;

import com.uday.oauthsecuritysystem.dto.request.ForgotPasswordRequest;
import com.uday.oauthsecuritysystem.dto.request.ResetPasswordRequest;
import com.uday.oauthsecuritysystem.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService
            passwordResetService;

    @PostMapping("/forgot")
    public String forgotPassword(
            @RequestBody
            ForgotPasswordRequest request) {

        return passwordResetService
                .forgotPassword(request);
    }

    @PostMapping("/reset")
    public String resetPassword(
            @RequestBody
            ResetPasswordRequest request) {

        return passwordResetService
                .resetPassword(request);
    }
}