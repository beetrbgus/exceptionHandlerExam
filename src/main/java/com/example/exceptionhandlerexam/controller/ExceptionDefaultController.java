package com.example.exceptionhandlerexam.controller;

import com.example.exceptionhandlerexam.dto.CreateReqDto;
import com.example.exceptionhandlerexam.exception.ErrorCode;
import com.example.exceptionhandlerexam.exception.MemberCannotFoundException;
import com.example.exceptionhandlerexam.response.FailureRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExceptionDefaultController {
    @GetMapping
    public void findMember() {
        throw new MemberCannotFoundException();
    }
    @PostMapping
    public void create(@RequestBody CreateReqDto reqDto) {
        long id = reqDto.getId();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailureRes> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ErrorCode invalidInputValue = ErrorCode.INVALID_INPUT_VALUE;
        return ResponseEntity.status(400)
                .body(
                        new FailureRes(400, invalidInputValue.getCode(), invalidInputValue.getMessage())
                );
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<FailureRes> handleMethodNullException(NullPointerException e) {

        return ResponseEntity.status(401)
                .body(
                    new FailureRes(400, "null point", "널임")
                );
    }

}
