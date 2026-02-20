package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.AccountEntity;
import com.backend.authsystem.authentication.entity.RefreshTokenEntity;
import com.backend.authsystem.authentication.entity.RoleEntity;
import com.backend.authsystem.authentication.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Testcontainers
class AccountRepositoryIT {

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
    private AccountRepository accountRepository;

    @Autowired
    private TestEntityManager entityManager;

    private AccountEntity createUser(String email) {
        AccountEntity user = AccountEntity.builder()
                .userId(UUID.randomUUID())
                .firstname("John")
                .lastname("Doe")
                .email(email)
                .password("password")
                .createdAt(Instant.now())
                .build();

       return accountRepository.save(user);
    }

    private RefreshTokenEntity buildTestToken(AccountEntity user) {
        return RefreshTokenEntity.builder()
                .myUser(user)
                .tokenId(UUID.randomUUID())
                .tokenHash("hash")
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .revoked(false)
                .build();
    }

    private RoleEntity buildTestRole(RoleEnum roleEnum) {
        return RoleEntity.builder()
                .roleId(UUID.randomUUID())
                .roleName(roleEnum)
                .createdAt(Instant.now())
                .build();
    }



    @Test
    void shouldFindUserByEmail() {
        String email = "john@example.com";
        createUser(email);

        Optional<AccountEntity> result = accountRepository.findByEmail(email);

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenEmailNotFound() {
        Optional<AccountEntity> result =
                accountRepository.findByEmail("missing@example.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {
        createUser("exists@example.com");

        boolean exists = accountRepository.existsByEmail("exists@example.com");

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {
        boolean exists = accountRepository.existsByEmail("nope@example.com");

        assertFalse(exists);
    }

    @Test
    void shouldPersistRefreshTokensViaCascade() {
        AccountEntity user = createUser("tokens@example.com");

        RefreshTokenEntity token = buildTestToken(user);
        user.getRefreshTokens().add(token);
        accountRepository.save(user);
        entityManager.flush();
        entityManager.clear();


        AccountEntity reloaded = accountRepository.findByEmail("tokens@example.com")
                .orElseThrow(() -> new AssertionError("User should exist"));

        assertEquals(1, reloaded.getRefreshTokens().size());
    }


    @Test
    void shouldRemoveRefreshTokensViaOrphanRemoval() {
        AccountEntity user = createUser("orphan@example.com");

        RefreshTokenEntity token = buildTestToken(user);
        user.getRefreshTokens().add(token);
        accountRepository.save(user);

        // reload managed entity
        AccountEntity managedUser = accountRepository.findByEmail("orphan@example.com")
                .orElseThrow(() -> new AssertionError("User should exist"));

        managedUser.getRefreshTokens().clear();
        accountRepository.save(managedUser);

        entityManager.flush();
        entityManager.clear();

        AccountEntity reloaded = accountRepository.findByEmail("orphan@example.com")
                .orElseThrow(() -> new AssertionError("User should exist"));

        assertTrue(reloaded.getRefreshTokens().isEmpty());
    }


    @Test
    void shouldPersistRolesForUser() {
        RoleEntity role =  buildTestRole(RoleEnum.ROLE_USER);
        entityManager.persist(role);

        AccountEntity user = createUser("roles@example.com");
        user.addRole(role);

        accountRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        AccountEntity reloaded =
                accountRepository.findByEmail("roles@example.com")
                                .orElseThrow(() -> new AssertionError("Role should exist"));

        assertEquals(1, reloaded.getRoles().size());
    }

    @Test
    void shouldLoadRolesEagerly() {
        RoleEntity role =  buildTestRole(RoleEnum.ROLE_ADMIN);
        entityManager.persist(role);

        AccountEntity user = createUser("eager@example.com");
        user.addRole(role);
        accountRepository.save(user);

        entityManager.flush();
        entityManager.clear();

        AccountEntity reloaded =
                accountRepository.findByEmail("eager@example.com")
                        .orElseThrow(() -> new AssertionError("Role should exist"));

        assertFalse(reloaded.getRoles().isEmpty());
    }






























}