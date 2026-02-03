package com.example.usermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterUserRequestDTO {
	@NotBlank
	private String userName;
	@Email(message = "Invalid email format")
	@Pattern(
	    regexp = "^[A-Za-z0-9+_.-]+@gmail\\.(com|in)$",
	    message = "Email must be gmail.com or gmail.in"
	)
	@NotBlank
    private String emailId;
	@NotBlank
	@Size(min=8)
    private String password;
	private String domain;
	public RegisterUserRequestDTO() {
		super();
	}
	public RegisterUserRequestDTO(String userName, String emailId, String password,String domain) {
		super();
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
		this.domain=domain;
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
    
}
