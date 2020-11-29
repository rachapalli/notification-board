package com.board.notification.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.board.notification.model.dto.EmailDTO;
import com.board.notification.model.dto.EmailStatusDTO;

public interface EmailService {
	public EmailStatusDTO sendEmail(EmailDTO emailDTO);

	void sendEmailWithAttachment(EmailDTO invitation) throws MessagingException, IOException;
}
