package com.example.usermanagement.serviceimplementation;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.UserCourseDTO;
import com.example.usermanagement.entity.Course;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.entity.UserCourse;
import com.example.usermanagement.repository.CourseRepository;
import com.example.usermanagement.repository.UserCourseRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.EnrollmentService;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void enroll(Long userId, Long courseId) {

        if (userCourseRepository
                .findByUser_IdAndCourse_Id(userId, courseId)
                .isPresent()) {
            throw new RuntimeException("Already enrolled");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        userCourseRepository.save(new UserCourse(user, course));
    }

    @Override
    public List<UserCourseDTO> getMyCourses(Long userId) {

        List<UserCourse> list = userCourseRepository.findByUser_Id(userId);
        List<UserCourseDTO> response = new ArrayList<>();

        for (UserCourse uc : list) {
            response.add(new UserCourseDTO(
                    uc.getCourse().getId(),                 
                    uc.getCourse().getTitle(),              
                    uc.getCourse().getLevel(),             
                    uc.getCourse().getDurationHours(),      
                    uc.getCourse().getCourseImageUrl(),     
                    uc.getCompleted()                      
            ));
        }

        return response;
    }

}
