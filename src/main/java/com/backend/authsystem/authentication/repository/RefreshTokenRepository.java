package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.RefreshTokenEntity;
import com.backend.authsystem.authentication.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    List<RefreshTokenEntity> findAllByMyUser(AccountEntity user);
    Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);
}
