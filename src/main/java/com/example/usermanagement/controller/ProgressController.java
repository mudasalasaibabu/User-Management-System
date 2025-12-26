package com.example.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.dto.ProgressDTO;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.ProgressService;
import com.example.usermanagement.serviceimplementation.UserPrincipal;

@RestController
@RequestMapping("/api/progress")
@PreAuthorize("hasRole('USER')")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping("/lessons/{lessonId}/complete")
    public String completeLesson(@PathVariable Long lessonId) {

        Long userId = getLoggedInUserId();
        progressService.markLessonCompleted(userId, lessonId);

        return "Lesson marked as completed";
    }

    @GetMapping("/courses/{courseId}")
    public ProgressDTO getCourseProgress(@PathVariable Long courseId) {

        Long userId = getLoggedInUserId();
        return progressService.getCourseProgress(userId, courseId);
    }

    private Long getLoggedInUserId() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserPrincipal userPrincipal) {
            return userPrincipal.getUser().getId();
        }

        throw new RuntimeException("Invalid authentication");
    }
}
