package com.board.notification.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.board.notification.model.Invitation;
import com.board.notification.model.StatusEnum;

public interface EmailService {
	public StatusEnum sendEmail(Invitation invitation);

	void sendEmailWithAttachment(Invitation invitation) throws MessagingException, IOException;
}
