package com.example.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.ChangePasswordForForgotPurpose;
import com.example.usermanagement.service.ForgotPasswordService;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {
	@Autowired
private ForgotPasswordService forgotPasswordService;
	@PostMapping("verifyMail/{email}")
	public ResponseEntity<String> verifyEmail(@PathVariable String email){
		String emailVerfication = forgotPasswordService.emailVerfication(email);
		return new ResponseEntity<String>(emailVerfication,HttpStatus.OK);
	}
	@PostMapping("/verifyOtp")
	public ResponseEntity<String> verifyOTP(@RequestParam  Integer otp,@RequestParam  String email){
		String otpVerfication = forgotPasswordService.otpVerfication(otp, email);
		return new ResponseEntity<String>(otpVerfication,HttpStatus.OK);
	}
	@PostMapping("/changePassword/{email}")
	public ResponseEntity<String> changePasswordForForgot(@RequestBody ChangePasswordForForgotPurpose changePassword,@PathVariable String email){
		String changePasswordHandler = forgotPasswordService.changePasswordHandler(changePassword, email);
		return new ResponseEntity<String>(changePasswordHandler,HttpStatus.OK);
	}
}
