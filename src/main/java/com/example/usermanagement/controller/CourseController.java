package com.example.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.dto.CourseLessonDTO;
import com.example.usermanagement.dto.CourseResponseDTO;
import com.example.usermanagement.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Get all ACTIVE courses (Student + Admin)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.getActiveCourses();
    }

    //  Get ONLY ACTIVE lessons of a course (Student-safe)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{courseId}/lessons")
    public List<CourseLessonDTO> getCourseLessons(
            @PathVariable Long courseId) {

        return courseService.getCourseLessons(courseId);
    }
}
