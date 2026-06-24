package com.uday.oauthsecuritysystem.jwt;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.uday.oauthsecuritysystem.config.KeyLoader;
import com.uday.oauthsecuritysystem.config.RsaKeyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final RsaKeyProperties rsaKeyProperties;
    private final KeyLoader keyLoader;

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {

        RSAPublicKey publicKey =
                keyLoader.loadPublicKey(
                        rsaKeyProperties.getPublicKeyPath());

        RSAPrivateKey privateKey =
                keyLoader.loadPrivateKey(
                        rsaKeyProperties.getPrivateKeyPath());

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);

        return (selector, context) -> selector.select(jwkSet);
    }

    @Bean
    public JwtEncoder jwtEncoder(
            JWKSource<SecurityContext> jwkSource) {

        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {

        RSAPublicKey publicKey =
                keyLoader.loadPublicKey(
                        rsaKeyProperties.getPublicKeyPath());

        return NimbusJwtDecoder
                .withPublicKey(publicKey)
                .build();
    }
}