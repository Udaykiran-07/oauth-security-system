package com.uday.oauthsecuritysystem.jwt;

import com.uday.oauthsecuritysystem.entity.Permission;
import com.uday.oauthsecuritysystem.entity.Role;
import com.uday.oauthsecuritysystem.entity.User;
import com.uday.oauthsecuritysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;

    public String generateAccessToken(
            String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        List<String> authorities =
                new ArrayList<>();

        List<String> roles =
                new ArrayList<>();

        List<String> permissions =
                new ArrayList<>();

        for (Role role : user.getRoles()) {

            roles.add(
                    role.getRoleName()
            );

            authorities.add(
                    role.getRoleName()
            );

            for (Permission permission
                    : role.getPermissions()) {

                permissions.add(
                        permission.getPermissionName()
                );

                authorities.add(
                        permission.getPermissionName()
                );
            }
        }

        Instant now = Instant.now();

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer(
                                "oauth-security-system"
                        )
                        .subject(username)

                        .claim(
                                "userId",
                                user.getId()
                        )

                        .claim(
                                "tenantId",
                                user.getTenant() != null
                                        ? user.getTenant()
                                          .getId()
                                        : null
                        )

                        .claim(
                                "tenantName",
                                user.getTenant() != null
                                        ? user.getTenant()
                                          .getTenantName()
                                        : null
                        )

                        .claim(
                                "roles",
                                roles
                        )

                        .claim(
                                "permissions",
                                permissions
                        )

                        .claim(
                                "authorities",
                                authorities
                        )

                        .issuedAt(now)

                        .expiresAt(
                                now.plus(
                                        30,
                                        ChronoUnit.MINUTES
                                )
                        )

                        .build();

        return jwtEncoder.encode(
                        JwtEncoderParameters
                                .from(claims)
                )
                .getTokenValue();
    }

    public String generateRefreshToken(
            String username) {

        Instant now = Instant.now();

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer(
                                "oauth-security-system"
                        )
                        .subject(username)

                        .claim(
                                "type",
                                "refresh"
                        )

                        .issuedAt(now)

                        .expiresAt(
                                now.plus(
                                        7,
                                        ChronoUnit.DAYS
                                )
                        )

                        .build();

        return jwtEncoder.encode(
                        JwtEncoderParameters
                                .from(claims)
                )
                .getTokenValue();
    }

    public Jwt decodeToken(
            String token) {

        return jwtDecoder.decode(token);
    }

    public String extractUsername(
            String token) {

        return jwtDecoder
                .decode(token)
                .getSubject();
    }

    public boolean isValid(
            String token) {

        try {

            jwtDecoder.decode(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}