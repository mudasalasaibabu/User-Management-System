package com.example.usermanagement.entity;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @OneToOne(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ForgotPassword forgotPassword;
    @Column(name ="domain",nullable=false)
    private String domain;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

	public User() {
		super();
	}

	public User(String userName, String emailId, String password,String domain, Role role) {
		super();
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
		this.domain=domain;
		this.role = role;
	}
	

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public LocalDateTime getLastLoginAt() {
	    return lastLoginAt;
	}

	public void setLastLoginAt(LocalDateTime lastLoginAt) {
	    this.lastLoginAt = lastLoginAt;
	}

}
