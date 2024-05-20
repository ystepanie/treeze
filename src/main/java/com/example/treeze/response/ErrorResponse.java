package com.example.treeze.response;

public record ErrorResponse(
        String status,
        int errorCode,
        String message
) {
}
