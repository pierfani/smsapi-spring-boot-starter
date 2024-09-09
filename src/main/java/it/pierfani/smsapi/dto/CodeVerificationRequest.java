package it.pierfani.smsapi.dto;

import lombok.Data;

@Data
public class CodeVerificationRequest {
    private String code;
    private String phoneNumber;
}