package com.example.usermanagement.dto;

import java.time.LocalDateTime;

import com.example.usermanagement.entity.Role;

public class RegisterUserResponseDTO {
		private Long id;
	    private String userName;
	    private String emailId;
	    private String domain;
	    private Role role;
	    private boolean enabled;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    private LocalDateTime lastLoginAt;
		public RegisterUserResponseDTO() {
			super();
		}

		public RegisterUserResponseDTO(String userName, String emailId,String domain, Role role,boolean enabled) {
			super();
			this.userName = userName;
			this.emailId = emailId;
			this.domain=domain;
			this.role = role;
			 this.enabled = enabled;
		}
		public boolean isEnabled() {
	        return enabled;
	    }

	    public void setEnabled(boolean enabled) {
	        this.enabled = enabled;
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

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}
		
}
