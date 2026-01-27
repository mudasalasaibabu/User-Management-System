package com.example.usermanagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.CourseAnalyticsDTO;
import com.example.usermanagement.service.CourseAnalyticsService;

@RestController
@RequestMapping("/admin/coursedashboard/")
public class AdminCourseDashboard {
	@Autowired
	private CourseAnalyticsService courseAnalyticsService;
	
	@GetMapping("{courseId}/analytics")
	public CourseAnalyticsDTO getCourseAnalytics(@PathVariable Long courseId) {
	    return courseAnalyticsService.getCourseAnalytics(courseId);
	}


}
