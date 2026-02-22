package com.backend.authsystem.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserloginDto(
        @Schema(description = "User password ", example = "Passw0rd123")
        @NotBlank(message = "password must not be empty")
        @Size(min = 8, message = "password must be at least 8 characters long") String password,

        @Schema(description = "User email", example = "user@gmail.com")
        @Email(message = "email should be valid")
        @NotBlank(message = "email must not be empty") String email
) { }
