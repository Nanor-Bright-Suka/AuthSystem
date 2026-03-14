package com.backend.authsystem.authentication.dto.course_material;

import com.backend.authsystem.authentication.enums.CourseMaterialType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAndUpdateRequestDto(
        @Schema(description = "Title of the course material", example = "Week 1 Lecture Notes")
        @NotBlank(message = "title must not be empty")
        @Size(min = 2, max = 150, message = "title must be between 2 and 150 characters")
        String title,

        @Schema(description = "Optional description of the material", example = "Introduction to basic programming concepts")
        @Size(max = 2000, message = "description cannot exceed 2000 characters")
        String description,

        @Schema(description = "Type of material being uploaded", example = "FILE")
        @NotNull(message = "materialType must not be null")
        CourseMaterialType materialType,

        @Schema(
                description = "External video URL (required if materialType = VIDEO_LINK)",
                example = "https://www.youtube.com/watch?v=example"
        )
        String videoUrl

){
}
