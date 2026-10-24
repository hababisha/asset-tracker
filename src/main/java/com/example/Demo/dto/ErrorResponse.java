package com.example.Demo.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message,
        String path) {

    public static ErrorResponse of(int status, String message, String path) {
        return new ErrorResponse(LocalDateTime.now(), status, message, path);
    }
}
