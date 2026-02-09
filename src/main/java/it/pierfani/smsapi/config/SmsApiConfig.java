package it.pierfani.smsapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "it.pierfani.smsapi")
public class SmsApiConfig {
    private String baseUrl = "https://api.smsapi.com";
    private String oauthToken;
}