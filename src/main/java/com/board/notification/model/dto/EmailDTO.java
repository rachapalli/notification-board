package com.board.notification.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EmailDTO {
	@NotNull(message = "Email cannot be null")
	@Email(message = "Invalid Email")
	private String email;

	@NotBlank
	private String subject;
	@NotBlank
	private String message;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EmailDTO(String email, String subject, String message) {
		super();
		this.email = email;
		this.subject = subject;
		this.message = message;
	}

	public EmailDTO() {
		super();
	}

	@Override
	public String toString() {
		return "EmailDTO [email=" + email + ", subject=" + subject + ", message=" + message + "]";
	}

}
