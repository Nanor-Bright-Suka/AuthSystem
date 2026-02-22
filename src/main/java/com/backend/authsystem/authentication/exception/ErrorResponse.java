package com.backend.authsystem.authentication.exception;

public record ErrorResponse(String timestamp, int status, String error, String message) {
}
