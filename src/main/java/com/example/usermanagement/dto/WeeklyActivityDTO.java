package com.example.usermanagement.dto;
public class WeeklyActivityDTO {

    private String day;
    private long logins;
    private long signups;

    public WeeklyActivityDTO(String day, long logins, long signups) {
        this.day = day;
        this.logins = logins;
        this.signups = signups;
    }

    public String getDay() {
        return day;
    }

    public long getLogins() {
        return logins;
    }

    public long getSignups() {
        return signups;
    }
}

