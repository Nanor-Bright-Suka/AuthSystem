package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

    Optional<CourseEntity> findByCourseId(UUID courseId);
}
