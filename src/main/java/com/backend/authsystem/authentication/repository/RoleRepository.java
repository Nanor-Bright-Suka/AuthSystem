package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.RoleEntity;
import com.backend.authsystem.authentication.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByRoleName(RoleEnum roleName);

}
