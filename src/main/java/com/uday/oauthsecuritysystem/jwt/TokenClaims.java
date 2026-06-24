package com.uday.oauthsecuritysystem.jwt;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TokenClaims {

    private Long userId;
    private String username;
    private Long tenantId;

    private Set<String> roles;
    private Set<String> permissions;
}