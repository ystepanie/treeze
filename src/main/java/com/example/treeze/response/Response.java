package com.example.treeze.response;

// 컨트롤러 Response 객체
public record Response(
        String status,
        String message,
        Object data
) {
    public Response(String status, String message) {
        this(status, message, null);
    }
}
