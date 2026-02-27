package com.backend.authsystem.authentication.mapper;

import com.backend.authsystem.authentication.dto.AccountResponseDto;
import com.backend.authsystem.authentication.entity.AccountEntity;
import com.backend.authsystem.authentication.entity.PermissionEntity;
import com.backend.authsystem.authentication.entity.RoleEntity;
import com.backend.authsystem.authentication.enums.PermissionEnum;
import com.backend.authsystem.authentication.enums.RoleEnum;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    public AccountResponseDto toResponse(AccountEntity account) {
        Set<RoleEnum> roles = account.getRoles()
                .stream()
                .map(RoleEntity::getRoleName)
                .collect(Collectors.toSet());

        Set<PermissionEnum> permissions = account.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(PermissionEntity::getPermissionName)
                .collect(Collectors.toSet());

        return new AccountResponseDto(
                account.getUserId(),
                account.getEmail(),
                roles,
                permissions

        );
    }



}
