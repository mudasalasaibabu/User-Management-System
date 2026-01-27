package com.example.usermanagement.dto;

import java.util.List;

public class CourseAnalyticsDTO {

    private Long courseId;
    private String courseName; 
    private int enrolledCount;
    private int completedCount;
    private List<EnrolledUserDTO> users;

    public CourseAnalyticsDTO(Long courseId, String courseName, int enrolledCount, int completedCount, List<EnrolledUserDTO> users) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.enrolledCount = enrolledCount;
        this.completedCount = completedCount;
        this.users = users;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void setEnrolledCount(int enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public List<EnrolledUserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<EnrolledUserDTO> users) {
        this.users = users;
    }
}
