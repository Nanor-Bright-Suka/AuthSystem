package com.backend.authsystem.authentication.dto;

public record RefreshAndAccessToken(
        String refreshToken,
        String accessToken
) {
}
