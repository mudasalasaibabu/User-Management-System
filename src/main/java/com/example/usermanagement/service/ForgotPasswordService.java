package com.example.usermanagement.service;

import com.example.usermanagement.dto.ChangePasswordForForgotPurpose;

public interface ForgotPasswordService {
	public String emailVerfication(String email);
	public String otpVerfication(Integer otp,String email);
	public String changePasswordHandler(ChangePasswordForForgotPurpose changePassword, String email);
}
