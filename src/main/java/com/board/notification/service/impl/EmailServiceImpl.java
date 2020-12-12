package com.board.notification.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.board.notification.model.StatusEnum;
import com.board.notification.model.dto.EmailDTO;
import com.board.notification.model.dto.EmailStatusDTO;
import com.board.notification.service.EmailService;
import com.board.notification.utils.NotificationConstants;
import com.board.notification.utils.NotificationUtils;

@Service
public class EmailServiceImpl implements EmailService {
	private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public EmailStatusDTO sendEmail(EmailDTO emailDTO) {
		EmailStatusDTO emailStatus = null;
		if (!NotificationUtils.isValidEmail(emailDTO.getEmail())) {
			return new EmailStatusDTO(emailDTO.getEmail(), StatusEnum.FAIL, NotificationConstants.MSG_INVALID_EMAIL);
		}
		try {
			SimpleMailMessage simplMailMessage = new SimpleMailMessage();
			simplMailMessage.setTo(emailDTO.getEmail());
			simplMailMessage.setSubject(emailDTO.getSubject());
			simplMailMessage.setText(emailDTO.getMessage());
			javaMailSender.send(simplMailMessage);
			emailStatus = new EmailStatusDTO(emailDTO.getEmail(), StatusEnum.SUCCESS);
		} catch (Exception e) {
			logger.error("Error while sending email to:" + emailDTO.getEmail(), e);
			emailStatus = new EmailStatusDTO(emailDTO.getEmail(), StatusEnum.FAIL, e.getMessage());
		}
		return emailStatus;
	}

	@Override
	public List<EmailStatusDTO> sendEmails(List<EmailDTO> emailDTOs) {
		List<EmailStatusDTO> emailStatusDTOs = new ArrayList<>();
		for (EmailDTO emailDTO : emailDTOs) {
			emailStatusDTOs.add(sendEmail(emailDTO));
		}
		return emailStatusDTOs;
	}

	@Override
	public void sendEmailWithAttachment(EmailDTO emailDTO) throws MessagingException, IOException {
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

	@Override
	public EmailStatusDTO sendHtmlEmail(EmailDTO emailDTO) {
		EmailStatusDTO emailStatus = null;
		if (!NotificationUtils.isValidEmail(emailDTO.getEmail())) {
			return new EmailStatusDTO(emailDTO.getEmail(), StatusEnum.FAIL, NotificationConstants.MSG_INVALID_EMAIL);
		}
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setTo(emailDTO.getEmail());
			helper.setSubject(emailDTO.getSubject());
			helper.setText(emailDTO.getMessage(), true);
			javaMailSender.send(msg);
			emailStatus = new EmailStatusDTO(emailDTO.getEmail(), StatusEnum.SUCCESS);
		} catch (Exception e) {
			logger.error("Error while sending email to:" + emailDTO.getEmail(), e);
			emailStatus = new EmailStatusDTO(emailDTO.getEmail(), StatusEnum.FAIL, e.getMessage());
		}
		return emailStatus;
	}
	
	@Override
	public List<EmailStatusDTO> sendHtmlEmails(List<EmailDTO> emailDTOs) {
		List<EmailStatusDTO> emailStatusDTOs = new ArrayList<>();
		for (EmailDTO emailDTO : emailDTOs) {
			emailStatusDTOs.add(sendHtmlEmail(emailDTO));
		}
		return emailStatusDTOs;
	}

}
