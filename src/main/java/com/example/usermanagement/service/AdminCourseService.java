package com.example.usermanagement.service;

import java.util.List;

import com.example.usermanagement.dto.CourseLessonDTO;
import com.example.usermanagement.entity.Course;
import com.example.usermanagement.entity.CourseLesson;

public interface AdminCourseService {

    Course createCourse(Course course);

    Course updateCourse(Long courseId, Course course);
    void deleteCourse(Long courseId);
    public void toggleLessonStatus(Long lessonId);
    void toggleCourseStatus(Long courseId);

    CourseLesson addLesson(Long courseId, CourseLesson lesson);

    List<Course> getAllCourses();
    
    CourseLesson updateLesson(Long lessonId, CourseLesson lesson);
    void deleteLesson(Long lessonId);
    public List<CourseLessonDTO> getLessonsByCourse(Long courseId);

}
