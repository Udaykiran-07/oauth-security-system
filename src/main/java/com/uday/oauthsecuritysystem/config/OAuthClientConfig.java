package com.uday.oauthsecuritysystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.UUID;

@Configuration
public class OAuthClientConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository(
            PasswordEncoder passwordEncoder) {

        RegisteredClient registeredClient =
                RegisteredClient.withId(
                                UUID.randomUUID().toString())
                        .clientId("oauth-client")

                        .clientSecret(
                                passwordEncoder.encode(
                                        "oauth-secret"))

                        .clientAuthenticationMethod(
                                ClientAuthenticationMethod.CLIENT_SECRET_BASIC)

                        .authorizationGrantType(
                                AuthorizationGrantType.CLIENT_CREDENTIALS)

                        .authorizationGrantType(
                                AuthorizationGrantType.REFRESH_TOKEN)

                        .scope("read")
                        .scope("write")

                        .build();

        return new InMemoryRegisteredClientRepository(
                registeredClient);
    }
}