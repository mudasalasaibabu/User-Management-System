package com.example.usermanagement.dto;

import java.time.LocalDateTime;

public class CertificateDTO {

    private Long courseId;
    private String courseTitle;
    private String certificateCode;
    private LocalDateTime issuedAt;

    public CertificateDTO(
            Long courseId,
            String courseTitle,
            String certificateCode,
            LocalDateTime issuedAt
    ) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.certificateCode = certificateCode;
        this.issuedAt = issuedAt;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
}
