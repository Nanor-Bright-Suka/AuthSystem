package com.backend.authsystem.authentication.enums;

public enum CourseState {

    CREATED,            // Course created by lecturer, not visible to students
    PUBLISHED,          // Course visible in catalog, enrollment not open
    ENROLLMENT_OPEN,    // Students can enroll
    ENROLLMENT_CLOSED,  // Enrollment finished
    ACTIVE,             // Semester started, teaching in progress
    COMPLETED,          // Course finished, semester has ended, grades finalized
    ARCHIVED            // Historical record, read-only

}
