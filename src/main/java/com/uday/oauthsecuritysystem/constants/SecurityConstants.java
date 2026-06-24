package com.uday.oauthsecuritysystem.constants;

public final class SecurityConstants {

    private SecurityConstants() {
    }

    public static final String TOKEN_TYPE = "Bearer";

    public static final long ACCESS_TOKEN_EXPIRY_MINUTES = 30;

    public static final long REFRESH_TOKEN_EXPIRY_DAYS = 7;

    public static final int MAX_FAILED_ATTEMPTS = 5;

    public static final int ACCOUNT_LOCK_MINUTES = 15;
}