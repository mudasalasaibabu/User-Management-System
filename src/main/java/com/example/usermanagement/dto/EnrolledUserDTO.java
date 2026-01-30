package com.example.usermanagement.dto;

public class EnrolledUserDTO {

    private Long userId;
    private String name;
    private String email;
    private Boolean completed;
    private int progressPercentage; 

    public EnrolledUserDTO(
            Long userId,
            String name,
            String email,
            Boolean completed,
            int progressPercentage
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.completed = completed;
        this.progressPercentage = progressPercentage;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }
}
