package com.example.usermanagement.service;

import com.example.usermanagement.dto.ProgressDTO;

public interface ProgressService {
	// Mark lesson as completed
    void markLessonCompleted(Long userId, Long lessonId);

    // Get progress of a course
    ProgressDTO getCourseProgress(Long userId, Long courseId);
}
