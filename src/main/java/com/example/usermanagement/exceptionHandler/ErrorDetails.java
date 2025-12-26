package com.example.usermanagement.exceptionHandler;


import java.time.LocalDateTime;

public class ErrorDetails {

    private String status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorDetails(String status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
