package com.uday.oauthsecuritysystem.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String username;

    @Email
    private String email;

    private Boolean enabled;
}