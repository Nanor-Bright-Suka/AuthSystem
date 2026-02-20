package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.AccountEntity;
import com.backend.authsystem.authentication.entity.RefreshTokenEntity;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Testcontainers
class RefreshTokenRepositoryIT {


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
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AccountRepository accountRepositoryIT;

    @Autowired
    private TestEntityManager entityManager;


    private AccountEntity savedUser(String email) {
        AccountEntity user = AccountEntity.builder()
                .userId(UUID.randomUUID())
                .firstname("bright")
                .lastname("nanor")
                .email(email)
                .password("password")
                .createdAt(Instant.now())
                .build();

        return accountRepositoryIT.save(user);
    }

    private RefreshTokenEntity savedToken(AccountEntity user, String hash) {
        RefreshTokenEntity token = RefreshTokenEntity.builder()
                .tokenId(UUID.randomUUID())
                .myUser(user)
                .tokenHash(hash)
                .expiresAt(Instant.now().plusSeconds(3600))
                .createdAt(Instant.now())
                .revoked(false)
                .build();

        return refreshTokenRepository.save(token);
    }



    @Test
    void shouldSaveRefreshToken() {
        AccountEntity user = savedUser("user@test.com");

        RefreshTokenEntity token = savedToken(user, "hash-1");

        assertThat(token.getTokenId()).isNotNull();
    }

    @Test
    void shouldFindTokenByHash() {
        AccountEntity user = savedUser("user@test.com");
        savedToken(user, "hash-123");

        Optional<RefreshTokenEntity> result =
                refreshTokenRepository.findByTokenHash("hash-123");

        assertThat(result).isPresent();
    }

    @Test
    void shouldReturnEmptyWhenTokenHashDoesNotExist() {
        Optional<RefreshTokenEntity> result =
                refreshTokenRepository.findByTokenHash("missing-hash");

        assertThat(result).isEmpty();
    }


    @Test
    void shouldFindAllTokensForUser() {
        AccountEntity user = savedUser("user@test.com");
        savedToken(user, "hash-1");
        savedToken(user, "hash-2");

        List<RefreshTokenEntity> tokens =
                refreshTokenRepository.findAllByMyUser(user);

        assertThat(tokens.size()).isEqualTo(2);
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoTokens() {
        AccountEntity user = savedUser("user@test.com");

        List<RefreshTokenEntity> tokens =
                refreshTokenRepository.findAllByMyUser(user);

        assertThat(tokens).isEmpty();

    }

    @Test
    void shouldNotReturnTokensForAnotherUser() {
        AccountEntity userA = savedUser("a@test.com");
        AccountEntity userB = savedUser("b@test.com");

        savedToken(userA, "hash-a");

        List<RefreshTokenEntity> tokens =
                refreshTokenRepository.findAllByMyUser(userB);

        assertThat(tokens).isEmpty();
    }

    @Test
    void shouldPersistRevokedToken() {
        AccountEntity user = savedUser("user@test.com");

        RefreshTokenEntity token = savedToken(user, "hash-1");
        token.setRevoked(true);
        token.setRevokedAt(Instant.now());

        refreshTokenRepository.save(token);

        RefreshTokenEntity reloaded =
                refreshTokenRepository.findByTokenHash("hash-1")
                        .orElseThrow(() -> new AssertionError("Token should exist"));

        assertThat(reloaded.getRevoked()).isTrue();
    }


    @Test
    void shouldPersistExpiresAt() {
        AccountEntity user = savedUser("user@test.com");

        Instant expiresAt = Instant.now().plusSeconds(600);
        RefreshTokenEntity token = RefreshTokenEntity.builder()
                .tokenId(UUID.randomUUID())
                .myUser(user)
                .tokenHash("hash-exp")
                .expiresAt(expiresAt)
                .createdAt(Instant.now())
                .revoked(false)
                .build();

        refreshTokenRepository.save(token);

        RefreshTokenEntity reloaded =
                refreshTokenRepository.findByTokenHash("hash-exp")
                        .orElseThrow(() -> new AssertionError("Token should exist"));

        assertThat(reloaded.getExpiresAt()).isEqualTo(expiresAt);
    }











}