package com.example.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.UserCourseDTO;
import com.example.usermanagement.service.EnrollmentService;
import com.example.usermanagement.serviceimplementation.UserPrincipal;

//@RestController
//@RequestMapping("/api/enrollments")
//@PreAuthorize("hasRole('USER')")
//public class EnrollmentController {
//
//    @Autowired
//    private EnrollmentService enrollmentService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/{courseId}")
//    public String enrollCourse(@PathVariable Long courseId) {
//
//        Long userId = getLoggedInUserId();
//        enrollmentService.enroll(userId, courseId);
//
//        return "Enrolled successfully";
//    }
//
//    @GetMapping("/my-courses")
//    public List<UserCourseDTO> getMyCourses() {
//
//        Long userId = getLoggedInUserId();
//        return enrollmentService.getMyCourses(userId);
//    }
//
//    private Long getLoggedInUserId() {
//
//        String email = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//
//        User user = userRepository.findByEmailId(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return user.getId();
//    }
//}
@RestController
@RequestMapping("/api/enrollments")
@PreAuthorize("hasRole('USER')")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/{courseId}")
    public String enrollCourse(
            @PathVariable Long courseId,
            @AuthenticationPrincipal UserPrincipal principal) {

        enrollmentService.enroll(principal.getUser().getId(), courseId);
        return "Enrolled successfully";
    }

    @GetMapping("/my-courses")
    public List<UserCourseDTO> getMyCourses(
            @AuthenticationPrincipal UserPrincipal principal) {

        return enrollmentService.getMyCourses(principal.getUser().getId());
    }
}

