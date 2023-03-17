package com.example.exceptionhandlerexam.exception.handler;

import com.example.exceptionhandlerexam.exception.AppException;
import com.example.exceptionhandlerexam.exception.ErrorCode;
import com.example.exceptionhandlerexam.exception.InputValidationException;
import com.example.exceptionhandlerexam.response.FailureRes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> globalExceptionHandler(AppException e) {
        return handleExceptionInternal(e);
    }

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<Object> accessDeniedExceptionHandler(InputValidationException e){
        return handleExceptionInternal(e);
    }

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<Object> nullPointException(NullPointerException e) {
        ErrorCode errorCode = ErrorCode.NULL_POINT;
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        List<FailureRes.ValidationError> ve = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FailureRes.ValidationError::of)
                .collect(Collectors.toList());
        return ResponseEntity.status(errorCode.getStatus())
                .body(buildResponse(errorCode, errorCode.getMessage(), ve));
    }
    private FailureRes buildResponse(ErrorCode errorCode) {
        return new FailureRes(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
    }

    private FailureRes buildResponse(ErrorCode errorCode, String message) {
        return new FailureRes(null, errorCode.getStatus(), errorCode.getCode(), message);
    }

    private FailureRes buildResponse(ErrorCode errorCode, String message, List<FailureRes.ValidationError> ve) {
        return new FailureRes(ve, errorCode.getStatus(), errorCode.getCode(), message);
    }
}
