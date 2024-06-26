package com.example.treeze.exception;

import org.springframework.http.HttpStatus;

public class SpringSecurityException extends RuntimeException {
    private final HttpStatus code;
    public SpringSecurityException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }

    public HttpStatus getCode() {
        return code;
    }
}
