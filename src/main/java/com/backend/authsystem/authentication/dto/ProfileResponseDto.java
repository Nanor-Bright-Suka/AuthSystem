package com.backend.authsystem.authentication.dto;


import jakarta.validation.constraints.NotBlank;

public record ProfileResponseDto(
        String firstName,
        String lastName,
        String bio,
        String imageUrl
) {
}
