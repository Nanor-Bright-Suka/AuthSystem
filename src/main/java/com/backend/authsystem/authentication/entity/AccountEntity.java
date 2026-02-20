package com.backend.authsystem.authentication.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "my_users")
public class AccountEntity {

    @Id
    private UUID userId;

        private String firstname;

        private String lastname;

        private String email;

        private String password;

        private Instant createdAt;

       private Instant updatedAt;

    @OneToMany(mappedBy = "myUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)

    @Builder.Default
    private List<RefreshTokenEntity> refreshTokens = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    @Builder.Default
    private Set<RoleEntity> roles = new HashSet<>();

    public void addRole(RoleEntity role) {
        this.roles.add(role);
    }



}
