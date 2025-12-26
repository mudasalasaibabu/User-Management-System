package com.example.usermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
	@Email
	@NotBlank
	 private String emailId;
	@NotBlank
	 private String password;
	public LoginRequestDTO() {
		super();
	}
	public LoginRequestDTO(String emailId, String password) {
		super();
		this.emailId = emailId;
		this.password = password;
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
	 
}
