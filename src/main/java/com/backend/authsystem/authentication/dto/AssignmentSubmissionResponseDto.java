package com.backend.authsystem.authentication.dto;

import java.time.Instant;
import java.util.UUID;

public record AssignmentSubmissionResponseDto(
        UUID id,
        UUID assignmentId,
        UUID studentId,
        String filePath,
        String originalFileName,
        Instant submittedAt,
        Integer marks,
        String feedback,
        Instant createdAt,
        Instant updatedAt
) {
}
