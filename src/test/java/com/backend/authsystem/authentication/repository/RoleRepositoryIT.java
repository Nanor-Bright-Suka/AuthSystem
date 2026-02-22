package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.PermissionEntity;
import com.backend.authsystem.authentication.entity.RoleEntity;
import com.backend.authsystem.authentication.enums.PermissionEnum;
import com.backend.authsystem.authentication.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@Testcontainers
class RoleRepositoryIT {
    @SuppressWarnings("resource")
    @Container
    static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }




    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    private RoleEntity saveRole(RoleEnum roleEnum) {
        RoleEntity role = RoleEntity.builder()
                .roleId(UUID.randomUUID())
                .roleName(roleEnum)
                .createdAt(Instant.now())
                .build();

        return roleRepository.save(role);
    }


    private PermissionEntity savePermission(String name) {
        PermissionEntity permission = PermissionEntity.builder()
                .permissionId(UUID.randomUUID())
                .permissionName(PermissionEnum.valueOf(name))
                .build();

        entityManager.persist(permission);
        return permission;
    }



    @Test
    void shouldSaveRole() {
        RoleEntity role = saveRole(RoleEnum.ROLE_USER);

        assertThat(role.getRoleId()).isNotNull();
    }

    @Test
    void shouldFindRoleByRoleName() {
        saveRole(RoleEnum.ROLE_ADMIN);

        Optional<RoleEntity> result =
                roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN);

        assertThat(result).isPresent();
    }

    @Test
    void shouldReturnEmptyWhenRoleNameDoesNotExist() {
        Optional<RoleEntity> result =
                roleRepository.findByRoleName(RoleEnum.ROLE_USER);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldPersistPermissionsForRole() {
        PermissionEntity read = savePermission("PROFILE_VIEW");
        PermissionEntity update = savePermission("PROFILE_UPDATE");

        RoleEntity role = saveRole(RoleEnum.ROLE_USER);
        role.addPermission(read);
        role.addPermission(update);

        roleRepository.save(role);
        entityManager.flush();
        entityManager.clear();

        RoleEntity reloaded =
                roleRepository.findByRoleName(RoleEnum.ROLE_USER)
                        .orElseThrow(() -> new AssertionError("Role ROLE_USER was not persisted"));

        assertThat(reloaded.getPermissions()).hasSize(2);
    }

    @Test
    void shouldEagerlyLoadPermissions() {
        PermissionEntity permission = savePermission("PROFILE_VIEW");

        RoleEntity role = saveRole(RoleEnum.ROLE_ADMIN);
        role.addPermission(permission);
        roleRepository.save(role);

        entityManager.flush();
        entityManager.clear();

        RoleEntity reloaded =
                roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN)
                        .orElseThrow(() -> new AssertionError("Role ROLE_ADMIN was not persisted"));

        assertThat(reloaded.getPermissions()).isNotEmpty();
    }

    @Test
    void shouldNotMixPermissionsAcrossRoles() {
        PermissionEntity userPerm = savePermission("PROFILE_VIEW");
        PermissionEntity adminPerm = savePermission("ROLE_ASSIGN");

        RoleEntity userRole = saveRole(RoleEnum.ROLE_USER);
        userRole.addPermission(userPerm);
        roleRepository.save(userRole);

        RoleEntity adminRole = saveRole(RoleEnum.ROLE_ADMIN);
        adminRole.addPermission(adminPerm);
        roleRepository.save(adminRole);

        entityManager.flush();
        entityManager.clear();

        RoleEntity reloadedUser =
                roleRepository.findByRoleName(RoleEnum.ROLE_USER).orElseThrow();

        RoleEntity reloadedAdmin =
                roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN).orElseThrow();

        assertThat(reloadedUser.getPermissions())
                .extracting(PermissionEntity::getPermissionName)
                .containsExactly(PermissionEnum.valueOf("PROFILE_VIEW"));

        assertThat(reloadedAdmin.getPermissions())
                .extracting(PermissionEntity::getPermissionName)
                .containsExactly(PermissionEnum.valueOf("ROLE_ASSIGN"));
    }




















}