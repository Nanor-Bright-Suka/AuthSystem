package com.backend.authsystem.authentication.dto;

import com.backend.authsystem.authentication.enums.AssignmentState;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record AssignmentResponseDto(
        UUID assignmentId,
        UUID courseId,
        String title,
        String description,
        AssignmentState state,
        Instant dueDate,
        int totalMarks,
        UUID lecturerId,
        Instant createdAt,
        Instant updatedAt
) {
}
