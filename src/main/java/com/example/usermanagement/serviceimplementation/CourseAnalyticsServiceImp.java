package com.example.usermanagement.serviceimplementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.CourseAnalyticsDTO;
import com.example.usermanagement.dto.EnrolledUserDTO;
import com.example.usermanagement.entity.Course;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.entity.UserCourse;
import com.example.usermanagement.repository.CourseRepository;
import com.example.usermanagement.repository.UserCourseRepository;
import com.example.usermanagement.service.CourseAnalyticsService;
@Service
public class CourseAnalyticsServiceImp implements CourseAnalyticsService {
	@Autowired
	private UserCourseRepository userCourseRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Override
	public CourseAnalyticsDTO getCourseAnalytics(Long courseId) {
		 Course course = courseRepository.findById(courseId)
			        .orElseThrow(() -> new RuntimeException("Course not found"));

			    String courseName = course.getTitle();

	    int enrolled = userCourseRepository.countByCourse_Id(courseId);
	    
	    int completed = userCourseRepository.countByCourse_IdAndCompletedTrue(courseId);

	    List<UserCourse> list = userCourseRepository.findByCourse_Id(courseId);
	    List<EnrolledUserDTO> users = new ArrayList<>();

	    for (UserCourse uc : list) {
	        User u = uc.getUser();

	        users.add(new EnrolledUserDTO(
	                u.getId(),
	                u.getUserName(),
	                u.getEmailId(),
	                uc.getCompleted()
	        ));
	    }

	    return new CourseAnalyticsDTO(courseId, courseName, enrolled, completed, users);
	}


}
