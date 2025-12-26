package com.example.usermanagement.service;

import java.util.List;

import com.example.usermanagement.dto.UserCourseDTO;

public interface EnrollmentService {
	// Enroll user into course
	void enroll(Long userId, Long courseId);
	 // Fetch courses enrolled by user
	List<UserCourseDTO> getMyCourses(Long userId);
}
