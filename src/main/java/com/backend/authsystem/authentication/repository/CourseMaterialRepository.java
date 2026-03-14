package com.backend.authsystem.authentication.repository;

import com.backend.authsystem.authentication.entity.CourseMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseMaterialRepository extends JpaRepository<CourseMaterialEntity, UUID> {

    List<CourseMaterialEntity> findAllByCourse_CourseIdAndIsDeletedFalse(UUID courseId);
    Optional<CourseMaterialEntity> findByIdAndIsDeletedFalse(UUID materialId);



}
