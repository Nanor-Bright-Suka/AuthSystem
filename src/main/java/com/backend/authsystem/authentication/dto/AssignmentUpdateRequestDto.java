package com.backend.authsystem.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record AssignmentUpdateRequestDto(
        @Schema(description = "New title for the assignment", example = "Database Normalization - Updated")
        @Size(max = 150, message = "title must not exceed 150 characters")
        String title,

        @Schema(description = "New description for the assignment", example = "Explain normalization rules with examples")
        @Size(max = 2000, message = "description must not exceed 2000 characters")
        String description,

        @Schema(description = "New due date for the assignment", example = "2026-03-15T23:59:00")
        @Future(message = "dueDate must be in the future")
        Instant dueDate,

        @Schema(description = "Updated total marks for the assignment", example = "100")
        @Min(value = 1, message = "totalMarks must be at least 1")
        @Max(value = 100, message = "totalMarks must not exceed 100")
        Integer totalMarks
) {
}
