package com.appsdeveloperblog.app.ws.mobileappws.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.mobileappws.service.EmailService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImp implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendVerificationEmail(UserDto user) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

			helper.setFrom("noreply@apiwit.com");
			helper.setTo(user.getEmail());
			helper.setSubject("Verification - " + user.getFirstName());
			String TEXTBODY = "<h1>Please verify your email address</h1>"
					+ "<p>Thank you for register with our app. To complete registration process and be able to log in</p>"
					+ "Click on the following link: "
					+ "<a href='http://localhost:8080/users/email-verification?token=$tokenValue'> Click </a>";
			String textBodyWithToken = TEXTBODY.replace("$tokenValue", user.getEmailVerificationToken());
			mimeMessage.setContent(textBodyWithToken, "text/html; charset=utf-8");
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			log.debug(e.getMessage());
		}
	}

	@Override
	public boolean sendPasswordResetRequest(String firstName, String email, String token) {
		try {
			boolean returnValue = false;
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			
			helper.setFrom("noreply@apiwit.com");
			helper.setTo(email);
			helper.setSubject("Password Reset - " + firstName);
			String TEXTBODY = "<p>Hi, $firstName</p>"
					+ "<p>Somesone has requested to reset your password with our project. If it were not you, please ignore it.</p>"
					+ "<p>otherwise please click on the link below to set a new password</p>"
					+ "<a href='http://localhost:8080/user/password-reset-request?token=$tokenValue'> Click this link to Reset Password</a><br /><br />"
					+ "Thank you!";
			String textBodyWithToken = TEXTBODY
					.replace("$tokenValue", token)
					.replace("$firstName", firstName);
			mimeMessage.setContent(textBodyWithToken, "text/html; charset=utf-8");
			mailSender.send(mimeMessage);
			
			returnValue = true;
			
			return returnValue;
		} catch (MessagingException e) {
			log.debug(e.getMessage());
			return false;
		} 
	}

}
