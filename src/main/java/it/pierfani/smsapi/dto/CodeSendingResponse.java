package it.pierfani.smsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CodeSendingResponse {
    private String id;
    private String code;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String from;
}