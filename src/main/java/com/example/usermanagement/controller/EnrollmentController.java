package com.example.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.dto.UserCourseDTO;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollments")
@PreAuthorize("hasRole('USER')")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{courseId}")
    public String enrollCourse(@PathVariable Long courseId) {

        Long userId = getLoggedInUserId();
        enrollmentService.enroll(userId, courseId);

        return "Enrolled successfully";
    }

    @GetMapping("/my-courses")
    public List<UserCourseDTO> getMyCourses() {

        Long userId = getLoggedInUserId();
        return enrollmentService.getMyCourses(userId);
    }

    private Long getLoggedInUserId() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getId();
    }
}
