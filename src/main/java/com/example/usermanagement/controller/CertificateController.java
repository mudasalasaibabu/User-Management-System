package com.example.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.CertificateDTO;
import com.example.usermanagement.dto.UserCourseDTO;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.CertificateService;
import com.example.usermanagement.service.EnrollmentService;
import com.example.usermanagement.serviceimplementation.UserPrincipal;

@RestController
@RequestMapping("/api/certificates")
@PreAuthorize("hasRole('USER')")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    public  EnrollmentService enrollmentService;
    //EXISTING API (JSON DETAILS) 
    @GetMapping("/courses/{courseId}")
    public CertificateDTO getCertificate(@PathVariable Long courseId) {

        Long userId = getLoggedInUserId();

        //  Just trigger generation (if needed)
        certificateService.generateCertificate(userId, courseId);

        //  Reuse service DTO logic
        List<CertificateDTO> certs =
                certificateService.getMyCertificateDTOs(userId);

        for (CertificateDTO dto : certs) {
            if (dto.getCourseId().equals(courseId)) {
                return dto;
            }
        }

        throw new RuntimeException("Certificate not found");
    }

    //NEW API (PDF DOWNLOAD)

    @GetMapping("/courses/{courseId}/download")
    public ResponseEntity<byte[]> downloadCertificate(
            @PathVariable Long courseId) {

        Long userId = getLoggedInUserId();

        byte[] pdf =
                certificateService.generateCertificatePdf(userId, courseId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=certificate-course-" + courseId + ".pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    @GetMapping("/my-certificates")
    public List<CertificateDTO> getMyCertificates() {

        Long userId = getLoggedInUserId();

        //  Auto-generate certificates for completed courses
        List<UserCourseDTO> enrolledCourses = enrollmentService.getMyCourses(userId);

        for (UserCourseDTO course : enrolledCourses) {
            certificateService.generateCertificate(userId, course.getCourseId());
        }

        return certificateService.getMyCertificateDTOs(userId);
    }


    
    //Helper Method
    private Long getLoggedInUserId() {

        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserPrincipal userPrincipal) {
            return userPrincipal.getUser().getId();
        }

        if (principal instanceof String email) {
            return userRepository.findByEmailId(email)
                    .orElseThrow(() -> new RuntimeException("User not found"))
                    .getId();
        }

        throw new RuntimeException("Unauthenticated");
    }


}
