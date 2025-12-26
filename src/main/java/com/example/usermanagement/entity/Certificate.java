package com.example.usermanagement.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Certificate belongs to ONE user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // Certificate belongs to ONE course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "certificate_code", unique = true, nullable = false)
    private String certificateCode;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;


    public Certificate() {
    }

    public Certificate(User user, Course course) {
        this.user = user;
        this.course = course;
        this.certificateCode = UUID.randomUUID().toString();
        this.issuedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
}
