package com.backend.authsystem.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AssignRoleRequestDto(
        @Schema(description = "User id, e.g., UUID", example = "7333757b-d2de-4f38-aa20-7291e74dddee")
        @NotBlank(message = "User id must not be empty")
        String userId,

        @Schema(description = "Role name, e.g., ROLE_LECTURE", example = "ROLE_LECTURE")
        @NotBlank(message = "role name  must not be empty")
        String roleName
) {
}
