package com.backend.authsystem.authentication.entity;


import com.backend.authsystem.authentication.enums.AssignmentState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "assignment")
public class AssignmentEntity {

    @Id
    private UUID assignmentId;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course; // Assignment belongs to a course

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentState state;

    @Column(nullable = false)
    private Instant dueDate;

    @Column(nullable = false)
    private int totalMarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id", nullable = false)
    private AccountEntity lecturer; // Owner of assignment

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    // Constructors, getters, setters

    @PrePersist
    protected void onCreate() {
        this.assignmentId = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        this.state = AssignmentState.DRAFT; // Default initial state
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }










}
