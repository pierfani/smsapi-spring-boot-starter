package it.pierfani.smsapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SmsApiWebClientConfig {

    @Autowired
    private SmsApiConfig smsApiConfig;

    @Bean
    public WebClient smsApiWebClient() {
        return WebClient.builder()
                .baseUrl(smsApiConfig.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + smsApiConfig.getOauthToken())
                .build();
    }
}