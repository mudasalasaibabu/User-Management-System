package com.example.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.entity.Course;
import com.example.usermanagement.entity.CourseLesson;
@Repository
public interface CourseLessonRepository extends JpaRepository<CourseLesson, Long> {
	// Fetch lessons of a course in order
    List<CourseLesson> findByCourse_IdOrderByLessonOrder(Long courseId);
    int countByCourse_Id(Long courseId);
    void deleteAllByCourse(Course course);
    List<CourseLesson> findByCourseAndActiveTrue(Course course);
//    List<CourseLesson> findByCourseOrderByLessonOrder(Course course);

}
