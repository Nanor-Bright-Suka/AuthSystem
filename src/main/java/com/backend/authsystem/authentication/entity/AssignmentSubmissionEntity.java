package com.backend.authsystem.authentication.entity;


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
@Builder
@Entity
@Table(
        name = "assignment_submissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"assignment_id", "student_id"})
)
public class AssignmentSubmissionEntity {

    @Id
    private UUID submissionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private AssignmentEntity assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private AccountEntity student;

    @Column(nullable = false)
    private String filePath; // path to uploaded PDF

    @Column(nullable = false)
    private String originalFileName; // original PDF file name

    @Column(nullable = false)
    private Instant submittedAt;

    @Column
    private Integer marks; // nullable until graded

    @Column(length = 2000)
    private String feedback; // optional lecturer feedback

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    // Constructors, getters, setters

    @PrePersist
    protected void onCreate() {
        this.submissionId = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        this.submittedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }


}
