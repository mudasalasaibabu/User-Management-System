package com.example.usermanagement.serviceimplementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.ProgressDTO;
import com.example.usermanagement.entity.CourseLesson;
import com.example.usermanagement.entity.LessonProgress;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.repository.CourseLessonRepository;
import com.example.usermanagement.repository.LessonProgressRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.ProgressService;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private LessonProgressRepository progressRepository;

    @Autowired
    private CourseLessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void markLessonCompleted(Long userId, Long lessonId) {

        if (progressRepository
                .findByUser_IdAndLesson_Id(userId, lessonId)
                .isPresent()) {
            return;
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CourseLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        progressRepository.save(new LessonProgress(user, lesson));
    }

    @Override
    public ProgressDTO getCourseProgress(Long userId, Long courseId) {

        int totalLessons =
            lessonRepository.countByCourse_Id(courseId);

        if (totalLessons == 0) {
            return new ProgressDTO(0);
        }

        int completedLessons =
            progressRepository.countByUser_IdAndLesson_Course_Id(userId, courseId);

        int percentage = (int) ((completedLessons * 100.0) / totalLessons);

        return new ProgressDTO(percentage);
    }

}

