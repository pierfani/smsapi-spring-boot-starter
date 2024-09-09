package it.pierfani.smsapi.exception;

public class SmsApiException extends RuntimeException {
    private static final long serialVersionUID = -5971647861683358400L;
    private final ErrorCode errorCode;

    public enum ErrorCode {
        INVALID_REQUEST,
        AUTHENTICATION_ERROR,
        CODE_EXPIRED,
        WRONG_CODE,
        SERVER_ERROR,
        NETWORK_ERROR,
        UNEXPECTED_ERROR
    }

    public SmsApiException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SmsApiException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}