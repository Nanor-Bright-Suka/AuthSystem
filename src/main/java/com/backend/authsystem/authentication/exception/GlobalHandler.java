package com.backend.authsystem.authentication.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandler {

    private  ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserExists(UserAlreadyExistException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

  @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    }

  @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFound(RoleNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingTokenException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(MissingTokenException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

  @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    }

@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }


@ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCourseNotFoundException(CourseNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

@ExceptionHandler(InvalidCourseStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCourseStateException(InvalidCourseStateException ex) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT);
    }

@ExceptionHandler(PermissionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePermissionNotFoundException(PermissionNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

@ExceptionHandler(DuplicatePermissionException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePermissionException(DuplicatePermissionException ex) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT);
    }

@ExceptionHandler(DuplicateRoleException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRoleException(DuplicateRoleException ex) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT);
    }





}
