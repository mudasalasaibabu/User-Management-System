package com.example.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.entity.Certificate;
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByUser_IdAndCourse_Id(Long userId, Long courseId);

    List<Certificate> findByUser_Id(Long userId);
}
