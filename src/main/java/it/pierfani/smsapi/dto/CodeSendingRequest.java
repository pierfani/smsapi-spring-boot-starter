package it.pierfani.smsapi.dto;

import lombok.Data;

@Data
public class CodeSendingRequest {
    private String phoneNumber;
    private String from;
    private String content;
    private String fast;
}