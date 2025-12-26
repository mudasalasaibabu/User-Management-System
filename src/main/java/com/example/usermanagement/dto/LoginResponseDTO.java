package com.example.usermanagement.dto;

public class LoginResponseDTO {
	private String token;
	private String type="Bearer";
	public LoginResponseDTO(String token) {
		super();
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	public String getType() {
		return type;
	}
	
}
