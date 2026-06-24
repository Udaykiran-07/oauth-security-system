package com.uday.oauthsecuritysystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "rsa")
public class RsaKeyProperties {

    private String publicKeyPath;
    private String privateKeyPath;
}