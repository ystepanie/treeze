package com.example.treeze.response;

import java.util.List;

public record ErrorResponse(
        String status,
        List<String> errorMessage
) {
}
