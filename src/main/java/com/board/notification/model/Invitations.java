package com.board.notification.model;

import org.springframework.data.annotation.Id;

public class Invitations {

	private Integer invitationId;
	private String subject;
	private String message;

	public Invitations() {
	}

	public Invitations(String subject, String message) {
		super();
		this.subject = subject;
		this.message = message;
	}

	@Id
	public Integer getInvitationId() {
		return this.invitationId;
	}

	public void setInvitationId(Integer invitationId) {
		this.invitationId = invitationId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
