package com.backend.authsystem.authentication.dto.course_material;

import com.backend.authsystem.authentication.enums.CourseMaterialType;

import java.time.Instant;
import java.util.UUID;

public record MaterialResponseDto(
        UUID materialId,
        String title,
        String description,
        CourseMaterialType materialType,

        String filePath,
        String videoUrl,

        UUID courseId,
        UUID uploadedBy,

        Instant uploadedAt
) {

}
