package it.pierfani.smsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import it.pierfani.smsapi.dto.CodeSendingRequest;
import it.pierfani.smsapi.dto.CodeSendingResponse;
import it.pierfani.smsapi.dto.CodeVerificationRequest;
import it.pierfani.smsapi.exception.SmsApiException;
import reactor.core.publisher.Mono;

@Service
public class SmsAuthenticatorServiceImpl implements SmsAuthenticatorService {

    @Autowired
    private WebClient smsApiWebClient;

    @Override
    public CodeSendingResponse sendCode(CodeSendingRequest request) throws SmsApiException {
        try {
            return smsApiWebClient.post()
                    .uri("/mfa/codes")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        if (response.statusCode() == HttpStatus.UNAUTHORIZED) {
                            return Mono.error(new SmsApiException("Authentication error",
                                    SmsApiException.ErrorCode.AUTHENTICATION_ERROR));
                        } else {
                            return Mono.error(new SmsApiException("Client error: " + response.statusCode(),
                                    SmsApiException.ErrorCode.INVALID_REQUEST));
                        }
                    })
                    .onStatus(HttpStatusCode::is5xxServerError,
                            response -> Mono.error(new SmsApiException("Server error: " + response.statusCode(),
                                    SmsApiException.ErrorCode.SERVER_ERROR)))
                    .bodyToMono(CodeSendingResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new SmsApiException("Error sending SMS code: " + e.getResponseBodyAsString(),
                    SmsApiException.ErrorCode.NETWORK_ERROR, e);
        } catch (Exception e) {
            throw new SmsApiException("Unexpected error sending SMS code", SmsApiException.ErrorCode.UNEXPECTED_ERROR,
                    e);
        }
    }

    @Override
    public boolean verifyCode(CodeVerificationRequest request) throws SmsApiException {
        try {
            return smsApiWebClient.post()
                    .uri("/mfa/codes/verifications")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        if (response.statusCode() == HttpStatus.NOT_FOUND) {
                            return Mono.error(
                                    new SmsApiException("Code not found", SmsApiException.ErrorCode.WRONG_CODE));
                        } else if (response.statusCode() == HttpStatus.REQUEST_TIMEOUT) {
                            return Mono
                                    .error(new SmsApiException("Code expired", SmsApiException.ErrorCode.CODE_EXPIRED));
                        } else if (response.statusCode() == HttpStatus.UNAUTHORIZED) {
                            return Mono.error(new SmsApiException("Authentication error",
                                    SmsApiException.ErrorCode.AUTHENTICATION_ERROR));
                        } else {
                            return Mono.error(new SmsApiException("Client error: " + response.statusCode(),
                                    SmsApiException.ErrorCode.INVALID_REQUEST));
                        }
                    })
                    .onStatus(HttpStatusCode::is5xxServerError,
                            response -> Mono.error(new SmsApiException("Server error: " + response.statusCode(),
                                    SmsApiException.ErrorCode.SERVER_ERROR)))
                    .toBodilessEntity()
                    .map(response -> {
                        // Il codice è valido se lo status è 204 NO_CONTENT
                        return response.getStatusCode() == HttpStatus.NO_CONTENT;
                    })
                    .onErrorResume(e -> {
                        if (e instanceof SmsApiException
                                && ((SmsApiException) e).getErrorCode() == SmsApiException.ErrorCode.WRONG_CODE) {
                            return Mono.just(false);
                        }
                        return Mono.error(e);
                    })
                    .block();
        } catch (SmsApiException e) {
            if (e.getErrorCode() == SmsApiException.ErrorCode.WRONG_CODE) {
                return false;
            }
            throw e;
        } catch (Exception e) {
            throw new SmsApiException("Unexpected error verifying SMS code", SmsApiException.ErrorCode.UNEXPECTED_ERROR,
                    e);
        }
    }
}