package com.board.notification.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Invitee {

	private Integer inviteeId;
	private String inviteeName;
	private String email;
	private String contactNumber;
	private Integer createdBy;
	private Date createdDate;

	public Invitee() {
	}

	public Invitee(Integer inviteeId, String inviteeName, String email, String contactNumber) {
		this.inviteeId = inviteeId;
		this.inviteeName = inviteeName;
		this.email = email;
		this.contactNumber = contactNumber;
	}

	public Invitee(Integer inviteeId, String inviteeName, String email, String contactNumber, Integer createdBy,
			Date createdDate) {
		this.inviteeId = inviteeId;
		this.inviteeName = inviteeName;
		this.email = email;
		this.contactNumber = contactNumber;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	@Id
	public Integer getInviteeId() {
		return this.inviteeId;
	}

	public void setInviteeId(Integer inviteeId) {
		this.inviteeId = inviteeId;
	}

	public String getInviteeName() {
		return this.inviteeName;
	}

	public void setInviteeName(String inviteeName) {
		this.inviteeName = inviteeName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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
