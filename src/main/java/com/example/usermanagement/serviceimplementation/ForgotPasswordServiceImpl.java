package com.example.usermanagement.serviceimplementation;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.ChangePasswordForForgotPurpose;
import com.example.usermanagement.dto.MailBodyDTO;
import com.example.usermanagement.entity.ForgotPassword;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.exceptionHandler.UserNotFoundException;
import com.example.usermanagement.repository.ForgotPasswordRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.EmailService;
import com.example.usermanagement.service.ForgotPasswordService;
@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ForgotPasswordRepository forgotPasswordRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public String emailVerfication(String email) {
		User user= userRepository.findByEmailId(email).orElseThrow(()->new UserNotFoundException("User Not Found"));
		int otp = otpGenerator();
		MailBodyDTO mailBody=new MailBodyDTO();
		mailBody.setTo(email);
		mailBody.setText("This is the OTP for your Forgot Password request : "+otp);
		mailBody.setSubject("OTP for Forgot Password request");
		emailService.sendSimpleMessage(mailBody);
		ForgotPassword forgotPassword = forgotPasswordRepository.findByUser(user).orElse(new ForgotPassword());
		forgotPassword.setOtp(otp);
		forgotPassword.setExpirationTime(new Date(System.currentTimeMillis()+60*1000));
		forgotPassword.setUser(user);
		forgotPasswordRepository.save(forgotPassword);
		return "Email sent for verification!";
	}
	
	private Integer otpGenerator() {
		Random random = new Random();
		return random.nextInt(100_000,999_999);
	}

	@Override
	public String otpVerfication(Integer otp, String email) {

	    User user = userRepository.findByEmailId(email)
	            .orElseThrow(() -> new UserNotFoundException("User Not Found"));

	    ForgotPassword forgotPassword = forgotPasswordRepository
	            .findByUser(user)
	            .orElseThrow(() -> new RuntimeException("OTP not requested"));

	    // Expiry check
	    if (forgotPassword.getExpirationTime().before(new Date())) {
	        forgotPasswordRepository.delete(forgotPassword);
	        return "OTP has expired!";
	    }

	    // OTP match check
	    if (!forgotPassword.getOtp().equals(otp)) {
	        return "Invalid OTP!";
	    }

	    // OTP verified → one-time use
	    forgotPasswordRepository.delete(forgotPassword);

	    return "OTP verified!";
	}


	@Override
	public String changePasswordHandler(ChangePasswordForForgotPurpose changePassword, String email) {
		if(!Objects.equals(changePassword.getPassword(),changePassword.getRepeatPassword())) {
			return "Please enter the password again!";
		}
		String encodedPassword= passwordEncoder.encode(changePassword.getPassword());
		userRepository.updatePassword(email, encodedPassword);
		return "Password Changed Successfully!";
	}
}
