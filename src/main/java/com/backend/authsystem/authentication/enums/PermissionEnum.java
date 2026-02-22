package com.backend.authsystem.authentication.enums;

public enum PermissionEnum {

    //profile-level
    PROFILE_VIEW,
    PROFILE_UPDATE,

    // account-level
    ACCOUNT_VIEW,
    PASSWORD_CHANGE,


    // student-level
    COURSE_VIEW,
    COURSE_ENROLL,
    ASSIGNMENT_VIEW,
    ASSIGNMENT_SUBMIT,
    COURSE_MATERIAL_VIEW,
    CLASS_FEEDBACK_SUBMIT,
    GENERAL_ANNOUNCEMENT_VIEW,


    // course rep-level
    CLASS_ANNOUNCEMENT_VIEW,
    CLASS_ANNOUNCEMENT_RELAY,
    CLASS_FEEDBACK_COLLECT,
    CLASS_FEEDBACK_FORWARD,


    // lecturer-level
    // course
    COURSE_CREATE,
    COURSE_DELETE,
    COURSE_UPDATE,
    COURSE_PUBLISH,
    COURSE_REP_ASSIGN,
    STUDENT_VIEW,

    // materials
    COURSE_MATERIAL_CREATE,
    COURSE_MATERIAL_UPDATE,
    COURSE_MATERIAL_DELETE,

    ASSIGNMENT_CREATE,
    ASSIGNMENT_GRADE,
    CLASS_FEEDBACK_VIEW,
    CLASS_ANNOUNCEMENT_CREATE,

//    ENROLLED STUDENTS_VIEW,


    // admin-level (system)
    ROLE_ASSIGN,
    USER_MANAGE

}
