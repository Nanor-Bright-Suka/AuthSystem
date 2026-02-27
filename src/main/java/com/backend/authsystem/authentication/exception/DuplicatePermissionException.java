package com.backend.authsystem.authentication.exception;

public class DuplicatePermissionException extends RuntimeException {
    public DuplicatePermissionException(String message) {
        super(message);
    }
}
