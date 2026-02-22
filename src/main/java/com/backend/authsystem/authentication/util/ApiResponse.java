package com.backend.authsystem.authentication.util;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse <T> {
    private boolean success = true;
    private String message;
    private T data;
}
