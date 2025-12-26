package com.example.usermanagement.dto;

public class ProgressDTO {

    private int percentage;

    public ProgressDTO() {
    }


    public ProgressDTO(int percentage) {
        this.percentage = percentage;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
