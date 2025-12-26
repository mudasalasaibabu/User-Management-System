package com.example.usermanagement.exceptionHandler;

public class CourseDeletionException extends RuntimeException {
    public CourseDeletionException(String message) {
        super(message);
    }
}

