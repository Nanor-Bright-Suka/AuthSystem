package com.backend.authsystem.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfileUpdateDto(

        @Schema(description = "User first name", example = "John")
        @NotBlank(message = "firstname must not be empty")
        @Size(min = 2, max = 50)  String firstName,

        @Schema(description = "User last name", example = "Doe")
        @NotBlank(message = "lastname must not be empty")
        @Size(min = 2, max = 50)  String lastName,

        @Schema(description = "User bio", example = "Software developer with a passion for open-source projects.")
        @Size(max = 500) String bio,

        @Schema(
                description = "User profile picture URL (allowed formats: png, jpg, jpeg)",
                example = "https://example.com/profile.png"
        )
        @Size(max = 255) String  imageUrl
) {
}
