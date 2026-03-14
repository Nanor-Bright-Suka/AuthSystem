package com.backend.authsystem.authentication.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.UUID;

public record AssignmentCreateRequestDto(
        @Schema(description = "Course ID the assignment belongs to")
        @NotNull(message = "courseId must not be null")
        UUID courseId,

        @Schema(description = "Assignment title", example = "Database Normalization")
        @NotBlank(message = "title must not be empty")
        @Size(max = 150, message = "title must not exceed 150 characters")
        String title,

        @Schema(description = "Assignment description", example = "Explain 1NF, 2NF and 3NF with examples.")
        @NotBlank(message = "description must not be empty")
        @Size(max = 2000, message = "description must not exceed 2000 characters")
        String description,

        @Schema(description = "Due date of the assignment")
        @NotNull(message = "dueDate must not be null")
        @Future(message = "dueDate must be in the future")
        Instant dueDate,

        @Schema(description = "Total marks for the assignment", example = "100")
        @NotNull(message = "totalMarks must not be null")
        @Min(value = 1, message = "totalMarks must be at least 1")
        @Max(value = 100, message = "totalMarks must not exceed 100")
        Integer totalMarks
) {
}
