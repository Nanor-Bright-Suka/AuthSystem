package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.AssignmentSubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmissionEntity, UUID> {

    List<AssignmentSubmissionEntity> findAllByAssignment_AssignmentId(UUID assignmentId);

    Optional<AssignmentSubmissionEntity> findBySubmissionId(UUID assignmentId);

    boolean existsByAssignment_AssignmentIdAndStudent_UserId(UUID assignmentId, UUID studentId);


}
