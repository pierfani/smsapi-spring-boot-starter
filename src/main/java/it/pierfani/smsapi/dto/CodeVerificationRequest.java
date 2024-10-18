package it.pierfani.smsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CodeVerificationRequest {
    private String code;
    @JsonProperty("phone_number")
    private String phoneNumber;
}