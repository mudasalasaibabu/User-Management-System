package com.example.usermanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String level;

    @Column(name = "duration_hours")
    private Integer durationHours;

    // Only URL, not file
    private String courseImageUrl;

    // Admin who created the course
    @Column(name = "created_by")
    private Long createdBy;

    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(name = "language")
    private String language;

    public Course() {
    }

    public Course(String title, String description, String level,
            String language,
            Integer durationHours, String courseImageUrl,
            Long createdBy, Boolean active) {

  this.title = title;
  this.description = description;
  this.level = level;
  this.language = language; 
  this.durationHours = durationHours;
  this.courseImageUrl = courseImageUrl;
  this.createdBy = createdBy;
  this.active = active;
  this.createdAt = LocalDateTime.now();
}


    // ===== Getters & Setters =====

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

    public Long getCreatedBy() {
        return createdBy;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setDurationHours(Integer durationHours) {
		this.durationHours = durationHours;
	}

	public void setCourseImageUrl(String courseImageUrl) {
		this.courseImageUrl = courseImageUrl;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getLanguage() {
	    return language;
	}

	public void setLanguage(String language) {
	    this.language = language;
	}

}
