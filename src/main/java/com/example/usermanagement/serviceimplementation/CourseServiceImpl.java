package com.example.usermanagement.serviceimplementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.CourseLessonDTO;
import com.example.usermanagement.dto.CourseResponseDTO;
import com.example.usermanagement.entity.Course;
import com.example.usermanagement.entity.CourseLesson;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.repository.CourseLessonRepository;
import com.example.usermanagement.repository.CourseRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseLessonRepository courseLessonRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CourseResponseDTO> getActiveCourses() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Course> courses;

        // CSE CORE → show all
        if ("CSE CORE".equalsIgnoreCase(user.getDomain())) {
            courses = courseRepository.findByActiveTrue();
        } else {
            // Domain specific
            courses = courseRepository
                    .findByActiveTrueAndDomain(user.getDomain());
        }

        List<CourseResponseDTO> response = new ArrayList<>();

        for (Course c : courses) {
            response.add(new CourseResponseDTO(
                    c.getId(),
                    c.getTitle(),
                    c.getDescription(),
                    c.getLevel(),
                    c.getLanguage(),
                    c.getDurationHours(),
                    c.getCourseImageUrl(),
                    c.getActive()
            ));
        }

        return response;
    }

    @Override
    public List<CourseLessonDTO> getCourseLessons(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<CourseLesson> lessons =
                courseLessonRepository.findByCourse_IdOrderByLessonOrder(courseId);

        List<CourseLessonDTO> response = new ArrayList<>();

        for (CourseLesson l : lessons) {
            response.add(new CourseLessonDTO(
                    l.getId(),
                    l.getLessonTitle(),
                    l.getYoutubeUrl(),
                    l.getLessonOrder()
            ));
        }

        return response;
    }

    @Override
    public List<CourseLesson> getLessons(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return courseLessonRepository.findByCourseAndActiveTrue(course);
    }
}
