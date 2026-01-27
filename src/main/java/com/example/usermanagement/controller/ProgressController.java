package com.example.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.ProgressDTO;
import com.example.usermanagement.service.ProgressService;
import com.example.usermanagement.serviceimplementation.UserPrincipal;

@RestController
@RequestMapping("/api/progress")
@PreAuthorize("hasRole('USER')")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping("/lessons/{lessonId}/complete")
    public String completeLesson(@PathVariable Long lessonId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        progressService.markLessonCompleted( userPrincipal.getUser().getId(), lessonId);
        return "Lesson marked as completed";
    }

    @GetMapping("/courses/{courseId}")
    public ProgressDTO getCourseProgress(@PathVariable Long courseId, @AuthenticationPrincipal UserPrincipal userPrincipal ) {
        return progressService.getCourseProgress(
                userPrincipal.getUser().getId(),
                courseId
        );
    }

}
