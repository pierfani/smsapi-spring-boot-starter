package it.pierfani.smsapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SmsApiWebClientConfig {

    private final SmsApiConfig smsApiConfig;

    @Bean
    public WebClient smsApiWebClient() {
        return WebClient.builder()
                .baseUrl(smsApiConfig.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + smsApiConfig.getOauthToken())
                .build();
    }
}