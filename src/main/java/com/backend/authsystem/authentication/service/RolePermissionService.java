package com.backend.authsystem.authentication.service;


import com.backend.authsystem.authentication.dto.AssignPermissionRequestDto;
import com.backend.authsystem.authentication.dto.AssignRoleRequestDto;
import com.backend.authsystem.authentication.dto.RolePermissionResponseDto;
import com.backend.authsystem.authentication.entity.AccountEntity;
import com.backend.authsystem.authentication.entity.PermissionEntity;
import com.backend.authsystem.authentication.entity.RoleEntity;
import com.backend.authsystem.authentication.enums.PermissionEnum;
import com.backend.authsystem.authentication.enums.RoleEnum;
import com.backend.authsystem.authentication.exception.*;
import com.backend.authsystem.authentication.repository.AccountRepository;
import com.backend.authsystem.authentication.repository.PermissionRepository;
import com.backend.authsystem.authentication.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RolePermissionService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final PermissionRepository permissionRepository;

    public RolePermissionResponseDto assignRoleToUser(AssignRoleRequestDto request) {

        String adminEmail = authenticatedUserService.getCurrentUserEmail();
        log.info("Admin {} attempting to assign role {} to user {}",
                adminEmail, request.roleName(), request.userId());

        AccountEntity user = accountRepository.findById(UUID.fromString(request.userId()))
                .orElseThrow(() -> {
                    log.warn("Admin {} failed to assign role {}. User {} not found",
                            adminEmail, request.roleName(), request.userId());
                    return new UserNotFoundException("User not found");
                });

        RoleEntity role = roleRepository.findByRoleName(RoleEnum.valueOf(request.roleName()))
                .orElseThrow(() -> {
                    log.warn("Admin {} failed to assign role. Role {} not found",
                            adminEmail, request.roleName());
                    return new RoleNotFoundException("Role not found");
                });

        // 🔥 STRICT DUPLICATE CHECK
        if (user.getRoles().contains(role)) {
            log.warn("Admin {} attempted to assign duplicate role {} to user {}",
                    adminEmail, role.getRoleName(), user.getUserId());
            throw new DuplicateRoleException("User already has this role assigned");
        }

        user.getRoles().add(role);
        accountRepository.save(user);

        log.info("Admin {} successfully assigned role {} to user {}",
                adminEmail, role.getRoleName(), user.getUserId());

        return new RolePermissionResponseDto("Role assigned successfully to user");
    }




    @Transactional
    public RolePermissionResponseDto assignPermissionsToRole(AssignPermissionRequestDto request) {

        String adminEmail = authenticatedUserService.getCurrentUserEmail();

        log.info("User {} attempting to assign {} permission(s) to role {}",
                adminEmail,
                request.permissionName().size(),
                request.roleName());

        // Fetch role
        RoleEntity role = roleRepository
                .findByRoleName(RoleEnum.valueOf(request.roleName()))
                .orElseThrow(() -> {
                    log.warn("Admin {} failed to assign permissions. Role {} not found",
                            adminEmail, request.roleName());
                    return new RoleNotFoundException("Role not found");
                });

        // Fetch permissions
        Set<PermissionEntity> permissionsToAssign = request.permissionName()
                .stream()
                .map(name -> permissionRepository
                        .findByPermissionName(PermissionEnum.valueOf(name))
                        .orElseThrow(() -> {
                            log.warn("Admin {} failed. Permission {} not found",
                                    adminEmail, name);
                            return new PermissionNotFoundException("Permission not found: " + name);
                        })
                )
                .collect(Collectors.toSet());

        // Duplicate detection
        Set<PermissionEntity> existingPermissions = role.getPermissions();

        Set<PermissionEntity> duplicates = permissionsToAssign.stream()
                .filter(existingPermissions::contains)
                .collect(Collectors.toSet());

        if (!duplicates.isEmpty()) {
            log.warn("Admin {} attempted to assign duplicate permission(s) {} to role {}",
                    adminEmail,
                    duplicates.stream()
                            .map(p -> p.getPermissionName().name())
                            .collect(Collectors.toSet()),
                    role.getRoleName());

            throw new DuplicatePermissionException("Some permissions are already assigned to this role");
        }

        // Assign permissions
        role.getPermissions().addAll(permissionsToAssign);
        roleRepository.save(role);

        log.info("User {} successfully assigned {} permission(s) to role {}",
                adminEmail,
                permissionsToAssign.size(),
                role.getRoleName());

        return new RolePermissionResponseDto("Permission(s) assigned successfully to role");
    }



}
