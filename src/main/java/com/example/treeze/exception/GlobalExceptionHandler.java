package com.example.treeze.exception;

import com.example.treeze.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String FAIL_VALUE = "failed";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<ObjectError> errorResult = exception.getBindingResult().getAllErrors();

        List<String> errorMessage = new ArrayList<>();
        errorResult.forEach(item -> {
            errorMessage.add(item.getDefaultMessage());
        });

        ErrorResponse response = new ErrorResponse(FAIL_VALUE, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginExceptions(LoginException exception) {
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add(exception.getMessage());
        ErrorResponse response = new ErrorResponse(FAIL_VALUE, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
