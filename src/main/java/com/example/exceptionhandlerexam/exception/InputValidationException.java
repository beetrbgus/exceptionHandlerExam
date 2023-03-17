package com.example.exceptionhandlerexam.exception;

public class InputValidationException extends AppException {
    public InputValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InputValidationException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
