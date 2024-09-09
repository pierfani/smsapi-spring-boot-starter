package it.pierfani.smsapi;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import it.pierfani.smsapi.config.SmsApiConfig;

@Configuration
@EnableConfigurationProperties(SmsApiConfig.class)
@ComponentScan(basePackages = "it.pierfani.smsapi")
public class SmsApiAutoConfiguration {


}