package com.example.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.dto.CertificateDTO;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.CertificateService;
import com.example.usermanagement.serviceimplementation.UserPrincipal;

@RestController
@RequestMapping("/api/certificates")
@PreAuthorize("hasRole('USER')")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private UserRepository userRepository;

    // -----------------------------
    // Get single certificate (JSON)
    // -----------------------------
    @GetMapping("/courses/{courseId}")
    public CertificateDTO getCertificate(@PathVariable Long courseId) {
        Long userId = getLoggedInUserId();
        return certificateService.getCertificateForCourse(userId, courseId);
    }


    // -----------------------------
    // Download PDF
    // -----------------------------
    @GetMapping("/courses/{courseId}/download")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long courseId) {

        Long userId = getLoggedInUserId();

        byte[] pdf = certificateService.generateCertificatePdf(userId, courseId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=certificate-course-" + courseId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // -----------------------------
    // FIXED: My Certificates
    // -----------------------------
    @GetMapping("/my-certificates")
    public List<CertificateDTO> getMyCertificates(Authentication authentication) {

        Long userId;

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) principal;
            userId = userPrincipal.getUser().getId();
        } else {
            String email = authentication.getName();
            userId = userRepository.findByEmailId(email)
                    .orElseThrow(() -> new RuntimeException("User not found"))
                    .getId();
        }

        // ONLY return certificates (no generation here)
        return certificateService.getMyCertificates(userId);
    }

    // -----------------------------
    // Helper
    // -----------------------------
    private Long getLoggedInUserId() {

        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) principal;
            return userPrincipal.getUser().getId();
        }

        if (principal instanceof String) {
            String email = (String) principal;
            return userRepository.findByEmailId(email)
                    .orElseThrow(() -> new RuntimeException("User not found"))
                    .getId();
        }

        throw new RuntimeException("Unauthenticated");
    }
}
