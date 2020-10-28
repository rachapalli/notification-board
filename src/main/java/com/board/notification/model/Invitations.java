package com.board.notification.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Invitations {

	private Integer invitationId;
	private String message;
	private Integer createdBy;
	private Date createdDate;

	public Invitations() {
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

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	
}
