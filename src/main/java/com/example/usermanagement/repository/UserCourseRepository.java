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
}
