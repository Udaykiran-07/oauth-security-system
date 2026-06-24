package com.uday.oauthsecuritysystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTenantRequest {

    @NotBlank
    private String tenantName;

    private String description;
}