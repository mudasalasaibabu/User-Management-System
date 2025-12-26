package com.example.usermanagement.serviceimplementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.MailBodyDTO;
import com.example.usermanagement.service.EmailService;
//@Service
//public class EmailServiceImp implements EmailService {
//	@Autowired
//	private JavaMailSender javaMailSender;
//	@Value("${spring.mail.username}")
//	private String mailUserName;
//	@Override
//	public void sendSimpleMessage(MailBodyDTO mailBody) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(mailBody.getTo());
//		message.setFrom(mailUserName);
//		message.setSubject(mailBody.getSubject());
//		message.setText(mailBody.getText());
//		javaMailSender.send(message);
//	}
//
//}
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailServiceImp implements EmailService {

    @Value("${MAIL_FROM}")
    private String fromEmail;

    @Override
    public void sendSimpleMessage(MailBodyDTO mailBody) {

        Email from = new Email(fromEmail);
        Email to = new Email(mailBody.getTo());
        Content content = new Content("text/plain", mailBody.getText());
        Mail mail = new Mail(from, mailBody.getSubject(), to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
