package com.example.treeze.exception;

import com.example.treeze.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String FAIL_VALUE = "failed";

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> LoginException(LoginException e) {
        ErrorResponse response = new ErrorResponse(FAIL_VALUE, HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    // 기타 예외 처리
}
