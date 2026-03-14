package com.backend.authsystem.authentication.dto.profile;


public record ProfileResponseDto(
        String firstName,
        String lastName,
        String bio,
        String imageUrl
) {
}
