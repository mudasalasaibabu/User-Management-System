package com.example.usermanagement.dto;

public class CourseLessonDTO {

    private Long lessonId;
    private String lessonTitle;
    private String youtubeUrl;
    private Integer lessonOrder;
    private String notesUrl;  //new

    public CourseLessonDTO(Long lessonId,
                           String lessonTitle,
                           String youtubeUrl,
                           Integer lessonOrder,
                           String notesUrl) {
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.youtubeUrl = youtubeUrl;
        this.lessonOrder = lessonOrder;
        this.notesUrl = notesUrl;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public Integer getLessonOrder() {
        return lessonOrder;
    }

    public String getNotesUrl() {
        return notesUrl;
    }
}
