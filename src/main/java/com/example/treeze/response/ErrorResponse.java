package com.example.treeze.response;

public record ErrorResponse(
        String status,
        String errorMessage
) {
}
