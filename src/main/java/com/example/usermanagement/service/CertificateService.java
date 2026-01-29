package com.example.usermanagement.service;

import java.util.List;

import com.example.usermanagement.dto.CertificateDTO;
import com.example.usermanagement.entity.Certificate;

public interface CertificateService {
	// Generate or fetch certificate
    Certificate generateCertificate(Long userId, Long courseId);
    byte[] generateCertificatePdf(Long userId, Long courseId);
    public List<Certificate> getMyCertificates(Long userId);
	CertificateDTO getCertificateForCourse(Long userId, Long courseId);
}
