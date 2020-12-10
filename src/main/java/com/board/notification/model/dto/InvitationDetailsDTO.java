package com.board.notification.model.dto;

public class InvitationDetailsDTO {
	private String boardOwnerEmail;
	private Integer invitedGroupId;
	private String invitedGroupName;

	public String getBoardOwnerEmail() {
		return boardOwnerEmail;
	}

	public void setBoardOwnerEmail(String boardOwnerEmail) {
		this.boardOwnerEmail = boardOwnerEmail;
	}

	public Integer getInvitedGroupId() {
		return invitedGroupId;
	}

	public void setInvitedGroupId(Integer invitedGroupId) {
		this.invitedGroupId = invitedGroupId;
	}

	public String getInvitedGroupName() {
		return invitedGroupName;
	}

	public void setInvitedGroupName(String invitedGroupName) {
		this.invitedGroupName = invitedGroupName;
	}

	@Override
	public String toString() {
		return "InvitationDetailsDTO [boardOwnerEmail=" + boardOwnerEmail + ", invitedGroupId=" + invitedGroupId
				+ ", invitedGroupName=" + invitedGroupName + "]";
	}

}
