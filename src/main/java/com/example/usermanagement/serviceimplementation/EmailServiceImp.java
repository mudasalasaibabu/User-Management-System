package com.example.usermanagement.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.MailBodyDTO;
import com.example.usermanagement.service.EmailService;
@Service
public class EmailServiceImp implements EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String mailUserName;
	@Override
	public void sendSimpleMessage(MailBodyDTO mailBody) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailBody.getTo());
		message.setFrom(mailUserName);
		message.setSubject(mailBody.getSubject());
		message.setText(mailBody.getText());
		javaMailSender.send(message);
	}

}
