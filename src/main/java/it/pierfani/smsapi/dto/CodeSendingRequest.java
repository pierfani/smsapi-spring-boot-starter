package it.pierfani.smsapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CodeSendingRequest {
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String from;
    private String content;
    private String fast;
}