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
			helper.setTo("murray.powlowski23@ethereal.email");
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

}
