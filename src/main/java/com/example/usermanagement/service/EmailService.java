package com.example.usermanagement.service;

import com.example.usermanagement.dto.MailBodyDTO;

public interface EmailService {
	public void sendSimpleMessage(MailBodyDTO mailBody);
}
