package com.example.usermanagement.dto;

public class CourseLessonDTO {

    private Long lessonId;
    private String lessonTitle;
    private String youtubeUrl;
    private Integer lessonOrder;

    public CourseLessonDTO(Long lessonId, String lessonTitle,String youtubeUrl, Integer lessonOrder) {
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.youtubeUrl = youtubeUrl;
        this.lessonOrder = lessonOrder;
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
}
