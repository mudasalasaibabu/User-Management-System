package com.example.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.entity.Course;
import com.example.usermanagement.entity.UserCourse;
@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {//This repo powers:Enroll,My Courses,Dashboard
	 // Check if user already enrolled
    Optional<UserCourse> findByUser_IdAndCourse_Id(Long userId, Long courseId);
    // Fetch all courses of a user
    List<UserCourse> findByUser_Id(Long userId);
    boolean existsByCourse(Course course);
 
    // NEW for admin feature
    // 1. How many users enrolled in a course
    int countByCourse_Id(Long courseId);

    // 2. How many users completed the course
    int countByCourse_IdAndCompletedTrue(Long courseId);

    // 3. List of enrolled users for a course
    List<UserCourse> findByCourse_Id(Long courseId);

}
