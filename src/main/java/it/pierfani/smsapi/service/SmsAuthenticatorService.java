package it.pierfani.smsapi.service;

import it.pierfani.smsapi.dto.CodeSendingRequest;
import it.pierfani.smsapi.dto.CodeSendingResponse;
import it.pierfani.smsapi.dto.CodeVerificationRequest;
import it.pierfani.smsapi.exception.SmsApiException;

public interface SmsAuthenticatorService {
    CodeSendingResponse sendCode(CodeSendingRequest request) throws SmsApiException;

    boolean verifyCode(CodeVerificationRequest request) throws SmsApiException;

}