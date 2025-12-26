package com.example.usermanagement.dto;

public class UserCourseDTO {

    private Long courseId;
    private String courseTitle;
    private String level;
    private Integer durationHours;
    private String courseImageUrl;
    private Boolean completed;

    public UserCourseDTO(
            Long courseId,
            String courseTitle,
            String level,
            Integer durationHours,
            String courseImageUrl,
            Boolean completed
    ) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.level = level;
        this.durationHours = durationHours;
        this.courseImageUrl = courseImageUrl;
        this.completed = completed;
    }

    /* ===================== GETTERS ===================== */

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getLevel() {
        return level;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
