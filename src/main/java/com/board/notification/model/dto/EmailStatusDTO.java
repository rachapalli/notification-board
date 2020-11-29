package com.board.notification.model.dto;

import com.board.notification.model.StatusEnum;

public class EmailStatusDTO {
	private String email;
	private StatusEnum status;
	private String message;

	public EmailStatusDTO() {
		super();
	}

	public EmailStatusDTO(String email, StatusEnum status) {
		super();
		this.email = email;
		this.status = status;
	}

	public EmailStatusDTO(String email, StatusEnum status, String message) {
		super();
		this.email = email;
		this.status = status;
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "EmailStatusDTO [email=" + email + ", status=" + status + ", message=" + message + "]";
	}

}
