package it.pierfani.smsapi.dto;

import lombok.Data;

@Data
public class CodeSendingResponse {
    private String id;
    private String code;
    private String phone_number;
    private String from;
}