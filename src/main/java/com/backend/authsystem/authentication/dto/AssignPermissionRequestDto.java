package com.backend.authsystem.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record AssignPermissionRequestDto(
        @Schema(description = "Role name, e.g., ROLE_TEACHER", example = "ROLE_TEACHER")
        @NotBlank(message = "role name  must not be empty")
        String roleName,

        @Schema(description = "Permission names, e.g., COURSE_VIEW", example = "COURSE_VIEW, COURSE_CREATE etc")
        @NotEmpty(message = "permissionName cannot be empty")
         Set<@NotBlank String> permissionName
) {
}
