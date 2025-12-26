package com.example.usermanagement.dto;

public class MonthlyRegistrationDTO {

    private String month;
    private long users;

    public MonthlyRegistrationDTO(String month, long users) {
        this.month = month;
        this.users = users;
    }

    public String getMonth() {
        return month;
    }

    public long getUsers() {
        return users;
    }
}

