package com.example.treeze.exception;

import com.example.treeze.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String FAIL_VALUE = "failed";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponse response = new ErrorResponse(FAIL_VALUE, HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<?> handleTokenNotFoundException(TokenNotFoundException e) {
        ErrorResponse response = new ErrorResponse(FAIL_VALUE, HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<?> handleUserDuplicateException(UserDuplicateException e) {
        ErrorResponse response = new ErrorResponse(FAIL_VALUE, HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 기타 예외 처리
}
