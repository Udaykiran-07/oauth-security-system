package com.uday.oauthsecuritysystem.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String token;

    private String newPassword;
}