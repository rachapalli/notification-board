package com.board.notification.model;

import org.springframework.data.annotation.Id;

public class Invitee {

	private Integer inviteeId;
	private String inviteeName;
	private String email;

	public Invitee() {
	}

	public Invitee(Integer inviteeId, String inviteeName, String email) {
		this.inviteeId = inviteeId;
		this.inviteeName = inviteeName;
		this.email = email;
	}

	public Invitee(String email) {
		this.email = email;
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

}
