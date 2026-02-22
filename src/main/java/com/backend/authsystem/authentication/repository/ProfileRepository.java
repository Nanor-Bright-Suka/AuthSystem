package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.AccountEntity;
import com.backend.authsystem.authentication.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {
    Optional<ProfileEntity> findByUser(AccountEntity user);


}
