package com.board.notification.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import com.board.notification.model.dto.EmailDTO;
import com.board.notification.model.dto.EmailStatusDTO;

public interface EmailService {
	public EmailStatusDTO sendEmail(EmailDTO emailDTO);

	void sendEmailWithAttachment(EmailDTO invitation) throws MessagingException, IOException;

	List<EmailStatusDTO> sendEmails(List<EmailDTO> emailDTOs);

	EmailStatusDTO sendHtmlEmail(EmailDTO emailDTO);

	List<EmailStatusDTO> sendHtmlEmails(List<EmailDTO> emailDTOs);
}
