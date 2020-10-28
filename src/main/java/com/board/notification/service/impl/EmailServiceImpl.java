package com.board.notification.service.impl;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.board.notification.model.Invitation;
import com.board.notification.model.StatusEnum;
import com.board.notification.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public StatusEnum sendEmail(Invitation invitation) {
		try {
			SimpleMailMessage simplMailMessage = new SimpleMailMessage();
			simplMailMessage.setTo(invitation.getEmail());
			simplMailMessage.setSubject(invitation.getSubject());
			simplMailMessage.setText(invitation.getMessage());
			javaMailSender.send(simplMailMessage);
		} catch (Exception e) {
			logger.error("Error while sending email to:" + invitation.getEmail(), e);
			return StatusEnum.FAIL;
		}
		return StatusEnum.SUCCESS;
	}

	@Override
	public void sendEmailWithAttachment(Invitation invitation) throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo("to_@email");
		helper.setSubject("Testing from Spring Boot");
		// default = text/plain
		// helper.setText("Check attachment for image!");
		// true = text/html
		helper.setText("<h1>Check attachment for image!</h1>", true);

		// hard coded a file path
		// FileSystemResource file = new FileSystemResource(new
		// File("path/android.png"));

		helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

		javaMailSender.send(msg);

	}

}
