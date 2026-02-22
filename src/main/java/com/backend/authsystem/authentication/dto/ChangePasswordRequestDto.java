package com.backend.authsystem.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequestDto(

        @Schema(description = "Old user password", example = "OldPassw0rd123")
        @NotBlank(message = "oldPassword must not be empty")
        @Size(min = 8, message = "password must be at least 8 characters long") String oldPassword,

        @Schema(description = "New user password", example = "NewP@ssw0rd123")
        @NotBlank(message = "newPassword must not be empty")
        @Size(min = 8, message = "password must be at least 8 characters long") String newPassword
) {
}
