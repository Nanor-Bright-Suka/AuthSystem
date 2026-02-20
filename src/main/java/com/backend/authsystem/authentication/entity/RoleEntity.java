package com.backend.authsystem.authentication.entity;


import com.backend.authsystem.authentication.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {

        @Id
        private UUID roleId;

        @Enumerated(EnumType.STRING)
        private RoleEnum roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )

    @Builder.Default
    private Set<PermissionEntity> permissions = new HashSet<>();

        private Instant createdAt;

        private Instant updatedAt;

    public void addPermission(PermissionEntity permission) {
        this.permissions.add(permission);
    }


    }
