package com.example.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.dto.CourseLessonDTO;
import com.example.usermanagement.entity.Course;
import com.example.usermanagement.entity.CourseLesson;
import com.example.usermanagement.service.AdminCourseService;

@RestController
@RequestMapping("/api/admin/courses")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCourseController {

    @Autowired
    private AdminCourseService adminCourseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course saved = adminCourseService.createCourse(course);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course) {

        Course updated = adminCourseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updated);
    }

    // Enable / Disable course
    @PutMapping("/{courseId}/toggle-status")
    public ResponseEntity<String> toggleCourseStatus(@PathVariable Long courseId) {

        adminCourseService.toggleCourseStatus(courseId);
        return ResponseEntity.ok("Course status updated");
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<CourseLesson> addLesson(
            @PathVariable Long courseId,
            @RequestBody CourseLesson lesson) {

        CourseLesson savedLesson =
                adminCourseService.addLesson(courseId, lesson);

        return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
    }

    // View all courses (ADMIN)
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(adminCourseService.getAllCourses());
    }

    //Delete course (ADMIN)
    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {

        adminCourseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully");
    }
    @PutMapping("/lessons/{lessonId}/toggle-status")
    public ResponseEntity<String> toggleLesson(@PathVariable Long lessonId) {
        adminCourseService.toggleLessonStatus(lessonId);
        return ResponseEntity.ok("Lesson status updated");
    }
    
    @PutMapping("/lessons/{lessonId}")
    public ResponseEntity<CourseLesson> updateLesson(
            @PathVariable Long lessonId,
            @RequestBody CourseLesson lesson) {

        CourseLesson updated = adminCourseService.updateLesson(lessonId, lesson);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        adminCourseService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }
 //View lessons of a course (ADMIN)
    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<CourseLessonDTO>> getCourseLessons(
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
            adminCourseService.getLessonsByCourse(courseId)
        );
    }


}
