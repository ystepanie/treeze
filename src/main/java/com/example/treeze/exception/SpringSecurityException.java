package com.example.treeze.exception;

import org.springframework.http.HttpStatus;

public class SpringSecurityException extends RuntimeException {
    public SpringSecurityException(String message, HttpStatus code) {
        super(message);
    }
}
