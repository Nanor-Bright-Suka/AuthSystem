package com.backend.authsystem.authentication.entity;

import com.backend.authsystem.authentication.enums.PermissionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "permission")
public class PermissionEntity {

    @Id
    private UUID permissionId;

    @Enumerated(EnumType.STRING)
    private PermissionEnum permissionName;

}
