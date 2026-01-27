package com.example.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.entity.LessonProgress;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress,Long> {//This enables:Progress %,Resume learning,Completion check
	// Check if lesson already completed
    Optional<LessonProgress> findByUser_IdAndLesson_Id(Long userId, Long lessonId);

    // Fetch all completed lessons for a user in a course
    List<LessonProgress> findByUser_IdAndLesson_Course_Id(Long userId, Long courseId);
    

    int countByUser_IdAndLesson_Course_Id(Long userId, Long courseId);

    @Query(value = """
    	    SELECT lp.lesson_id
    	    FROM lesson_progress lp
    	    JOIN course_lessons cl ON lp.lesson_id = cl.id
    	    WHERE lp.user_id = :userId
    	      AND cl.course_id = :courseId
    	""", nativeQuery = true)
    	List<Long> findLessonIdsByUserAndCourse(Long userId, Long courseId);



}
