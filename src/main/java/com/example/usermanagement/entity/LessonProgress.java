package com.example.usermanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "lesson_progress")
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  Progress belongs to ONE user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //  Progress belongs to ONE lesson
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private CourseLesson lesson;

    private Boolean watched;

    private LocalDateTime watchedAt;

    public LessonProgress() {
    }

    public LessonProgress(User user, CourseLesson lesson) {
        this.user = user;
        this.lesson = lesson;
        this.watched = true;
        this.watchedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public CourseLesson getLesson() {
        return lesson;
    }

    public Boolean getWatched() {
        return watched;
    }

    public LocalDateTime getWatchedAt() {
        return watchedAt;
    }
}
