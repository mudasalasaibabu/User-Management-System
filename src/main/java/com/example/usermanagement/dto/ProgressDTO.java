package com.example.usermanagement.dto;

import java.util.List;

public class ProgressDTO {

    private int percentage;
    private List<Long> completedLessonIds;

    public ProgressDTO() {
    }

    public ProgressDTO(int percentage, List<Long> completedLessonIds) {
        this.percentage = percentage;
        this.completedLessonIds = completedLessonIds;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public List<Long> getCompletedLessonIds() {
        return completedLessonIds;
    }

    public void setCompletedLessonIds(List<Long> completedLessonIds) {
        this.completedLessonIds = completedLessonIds;
    }
}
