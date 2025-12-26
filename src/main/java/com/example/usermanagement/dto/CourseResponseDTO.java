package com.example.usermanagement.dto;

public class CourseResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String level;
    private Integer durationHours;
    private String courseImageUrl;
    private Boolean active;

    public CourseResponseDTO(Long id, String title, String description,String level, Integer durationHours,String courseImageUrl, Boolean active) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.level = level;
        this.durationHours = durationHours;
        this.courseImageUrl = courseImageUrl;
        this.active = active;
    }

    // Getters
    public Long getId() { 
    	return id; 
    	}
    public String getTitle() { 
    	return title; 
    	}
    public String getDescription() {
    	return description; 
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
    public Boolean getActive() { 
    	return active; 
    	}
}
