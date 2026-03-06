package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity, UUID> {

    Optional<AssignmentEntity> findByAssignmentId(UUID id);

}
