package com.uday.oauthsecuritysystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthorizationServerConfig {

//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(
//            HttpSecurity http) throws Exception {
//
//        OAuth2AuthorizationServerConfiguration
//                .applyDefaultSecurity(http);
//
//        http
//                .oauth2ResourceServer(
//                        oauth2 ->
//                                oauth2.jwt(
//                                        Customizer.withDefaults()
//                                )
//                );
//
//        return http.build();
//    }
}