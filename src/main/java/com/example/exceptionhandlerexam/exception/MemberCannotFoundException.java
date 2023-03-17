package com.example.exceptionhandlerexam.exception;

public class MemberCannotFoundException extends AppException {
    public MemberCannotFoundException() {
        super(ErrorCode.NOT_FOUND_USER);
    }
}
