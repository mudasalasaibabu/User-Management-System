package com.example.usermanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "course_lessons")
public class CourseLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many lessons in one course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // ✅ BASIC COLUMN (NOT JoinColumn)
    @Column(name = "lesson_title", nullable = false)
    private String lessonTitle;

    @Column(name = "youtube_url", length = 500, nullable = false)
    private String youtubeUrl;

    // ✅ BASIC COLUMN (NOT JoinColumn)
    @Column(name = "lesson_order", nullable = false)
    private Integer lessonOrder;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean active = true;

    public CourseLesson() {}

    public CourseLesson(Course course, String lessonTitle,
                        String youtubeUrl, Integer lessonOrder) {
        this.course = course;
        this.lessonTitle = lessonTitle;
        this.youtubeUrl = youtubeUrl;
        this.lessonOrder = lessonOrder;
        this.createdAt = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getLessonTitle() {
		return lessonTitle;
	}

	public void setLessonTitle(String lessonTitle) {
		this.lessonTitle = lessonTitle;
	}

	public String getYoutubeUrl() {
		return youtubeUrl;
	}

	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

	public Integer getLessonOrder() {
		return lessonOrder;
	}

	public void setLessonOrder(Integer lessonOrder) {
		this.lessonOrder = lessonOrder;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

   
}
