package com.example.usermanagement.exceptionHandler.advicer;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.usermanagement.exceptionHandler.AccessDeniedException;
import com.example.usermanagement.exceptionHandler.CourseDeletionException;
import com.example.usermanagement.exceptionHandler.EmailAlreadyExistsException;
import com.example.usermanagement.exceptionHandler.ErrorDetails;
import com.example.usermanagement.exceptionHandler.InvalidCredentialsException;
import com.example.usermanagement.exceptionHandler.UserNotFoundException;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException e) {
        return build(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return build(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException() {
        return build(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorDetails> handleDisabledException(DisabledException e) {
        return build(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistException(EmailAlreadyExistsException e) {
        return build(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException e) {
        return build(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(CourseDeletionException.class)
    public ResponseEntity<String> handleCourseDelete(CourseDeletionException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException e) {
        e.printStackTrace(); //  this will show real error in Render logs
        return build(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalExceptionHandler(Exception e) {
        e.printStackTrace(); //  this will show real error in Render logs
        return build(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Something went wrong. Please try again."
        );
    }

    private ResponseEntity<ErrorDetails> build(HttpStatus status, String message) {
        ErrorDetails errorDetails = new ErrorDetails(
            status.getReasonPhrase(),
            message,
            LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDetails, status);
    }
}
