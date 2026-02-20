package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.PermissionEntity;
import com.backend.authsystem.authentication.enums.PermissionEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {
    Optional<PermissionEntity> findByPermissionName(PermissionEnum permissionName);
}
