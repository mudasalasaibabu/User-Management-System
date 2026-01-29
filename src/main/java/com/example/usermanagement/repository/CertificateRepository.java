package com.example.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.entity.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByUser_IdAndCourse_Id(Long userId, Long courseId);

    // OLD (remove)
    // List<Certificate> findByUser_Id(Long userId);

    // NEW (fixes LazyInitializationException)
    @Query("""
        SELECT c FROM Certificate c
        JOIN FETCH c.course
        WHERE c.user.id = :userId
    """)
    List<Certificate> findByUserWithCourse(@Param("userId") Long userId);
}
