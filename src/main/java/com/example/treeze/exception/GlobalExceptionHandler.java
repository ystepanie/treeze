package com.example.treeze.exception;

import com.example.treeze.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String FAIL_VALUE = "failed";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse response = new ErrorResponse(FAIL_VALUE, errorMessage);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleLoginExceptions(BadRequestException exception) {
        ErrorResponse response = new ErrorResponse(FAIL_VALUE, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpringSecurityException.class)
    public ResponseEntity<ErrorResponse> handleSecurityExceptions(SpringSecurityException exception) {
        ErrorResponse response = new ErrorResponse(FAIL_VALUE, exception.getMessage());
        HttpStatus errorCode = exception.getCode();
        return new ResponseEntity<>(response, errorCode);
    }
}
