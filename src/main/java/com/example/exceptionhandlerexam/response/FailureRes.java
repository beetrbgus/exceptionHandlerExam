package com.example.exceptionhandlerexam.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.validation.FieldError;

import java.util.List;

@Setter
@Getter
public class FailureRes extends BaseResponse{

    private String errorCode;
    private String message;

    public FailureRes(int code) {
        super(code);
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ValidationError> errors;

    public FailureRes(int statusCode, String errorCode, String message) {
        super(statusCode);
        this.errorCode = errorCode;
        this.message = message;
    }

    public FailureRes(List<ValidationError> errors, int statusCode, String errorCode, String message) {
        super(statusCode);
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
