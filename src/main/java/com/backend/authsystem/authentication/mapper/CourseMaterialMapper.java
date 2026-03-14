package com.backend.authsystem.authentication.mapper;

import com.backend.authsystem.authentication.dto.course_material.MaterialResponseDto;
import com.backend.authsystem.authentication.entity.CourseMaterialEntity;

public class CourseMaterialMapper {

    public static MaterialResponseDto toResponse(CourseMaterialEntity entity) {

        return new MaterialResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getMaterialType(),
                entity.getFilePath(),
                entity.getVideoUrl(),
                entity.getCourse().getCourseId(),
                entity.getUploadedBy().getUserId(),
                entity.getUploadedAt()
        );
    }

}
