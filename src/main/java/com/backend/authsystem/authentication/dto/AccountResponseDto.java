package com.backend.authsystem.authentication.dto;

import com.backend.authsystem.authentication.enums.PermissionEnum;
import com.backend.authsystem.authentication.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record AccountResponseDto(
       UUID id,
       String email,
        @NotNull(message = "roles must not be null") Set<@NotBlank(message = "role must not be empty") RoleEnum> roles,
        @NotNull(message = "permissions must not be null") Set<@NotBlank(message = "permission must not be empty") PermissionEnum> permissions
) {
}
