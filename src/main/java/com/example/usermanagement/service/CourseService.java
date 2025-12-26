package com.example.usermanagement.service;

import java.util.List;

import com.example.usermanagement.dto.CourseLessonDTO;
import com.example.usermanagement.dto.CourseResponseDTO;
import com.example.usermanagement.entity.CourseLesson;

public interface CourseService {
	 // View all active courses
	List<CourseResponseDTO> getActiveCourses();
	// View lessons of a course
	List<CourseLessonDTO> getCourseLessons(Long courseId);
	public List<CourseLesson> getLessons(Long courseId);

}
