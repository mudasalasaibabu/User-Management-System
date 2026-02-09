package com.example.usermanagement.serviceimplementation;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.usermanagement.dto.CourseLessonDTO;
import com.example.usermanagement.entity.Course;
import com.example.usermanagement.entity.CourseLesson;
import com.example.usermanagement.repository.CourseLessonRepository;
import com.example.usermanagement.repository.CourseRepository;
import com.example.usermanagement.repository.UserCourseRepository;
import com.example.usermanagement.service.AdminCourseService;

@Service
public class AdminCourseServiceImpl implements AdminCourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseLessonRepository lessonRepository;
    @Autowired
    private UserCourseRepository userCourseRepository;

    @Override
    public Course createCourse(Course course) {
        course.setActive(true);
        course.setCreatedAt(LocalDateTime.now());
        return courseRepository.save(course);
    }

 
    @Override
    public Course updateCourse(Long courseId, Course course) {

        Course existing = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existing.setTitle(course.getTitle());
        existing.setDescription(course.getDescription());
        existing.setLevel(course.getLevel());
        existing.setLanguage(course.getLanguage());  
        existing.setDomain(course.getDomain());
        existing.setDurationHours(course.getDurationHours());
        existing.setCourseImageUrl(course.getCourseImageUrl());
        existing.setUpdatedAt(LocalDateTime.now());

        return courseRepository.save(existing);
    }

    
    @Override
    public void deleteCourse(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Not Possible BLOCK DELETE if users enrolled
        if (userCourseRepository.existsByCourse(course)) {
            throw new RuntimeException("Cannot delete course. Users are enrolled.");
        }

        // delete lessons first
        lessonRepository.deleteAllByCourse(course);

        // delete course
        courseRepository.delete(course);
    }

    @Override
    public void toggleCourseStatus(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setActive(!course.getActive());
        courseRepository.save(course);
    }

    @Override
    public CourseLesson addLesson(Long courseId, CourseLesson lesson) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        lesson.setCourse(course);
        return lessonRepository.save(lesson);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

	@Override
	public void toggleLessonStatus(Long lessonId) {
	    CourseLesson lesson = lessonRepository.findById(lessonId)
	        .orElseThrow(() -> new RuntimeException("Lesson not found"));

	    lesson.setActive(!lesson.getActive());
	    lessonRepository.save(lesson);
	}
	
	@Override
	public CourseLesson updateLesson(Long lessonId, CourseLesson lesson) {

	    CourseLesson existing = lessonRepository.findById(lessonId)
	        .orElseThrow(() -> new RuntimeException("Lesson not found"));

	    existing.setLessonTitle(lesson.getLessonTitle());
	    existing.setYoutubeUrl(lesson.getYoutubeUrl());
	    existing.setLessonOrder(lesson.getLessonOrder());

	    return lessonRepository.save(existing);
	}

	@Override
	public void deleteLesson(Long lessonId) {

	    CourseLesson lesson = lessonRepository.findById(lessonId)
	        .orElseThrow(() -> new RuntimeException("Lesson not found"));

	    lessonRepository.delete(lesson);
	}
	@Override
	public List<CourseLessonDTO> getLessonsByCourse(Long courseId) {

	    List<CourseLesson> lessons =
	        lessonRepository.findByCourse_IdOrderByLessonOrder(courseId);

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
	@Transactional(readOnly = true)
	@Override
	public CourseLesson getLessonById(Long lessonId) {
	    return lessonRepository.findById(lessonId)
	        .orElseThrow(() -> new RuntimeException("Lesson not found"));
	}





}
