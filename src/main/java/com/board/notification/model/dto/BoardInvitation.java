package com.board.notification.model.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BoardInvitation {
	@NotEmpty(message = "EmailIdList cannot be null or empty")
	private Set<String> emailIdList;

	@NotEmpty(message = "emailBody cannot be null or empty")
	private String emailBody;

	@NotEmpty(message = "emailSubject cannot be null or empty")
	private String emailSubject;

	@NotNull(message = "createdBy cannot be null")
	private Integer createdBy;

	@NotEmpty(message = "Group name cannot be null or empty")
	private String groupName;

	public Set<String> getEmailIdList() {
		return emailIdList;
	}

	public void setEmailIdList(Set<String> emailIdList) {
		this.emailIdList = emailIdList;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "BoardInvitation [emailIdList=" + emailIdList + ", emailBody=" + emailBody + ", emailSubject="
				+ emailSubject + ", createdBy=" + createdBy + ", groupName=" + groupName + "]";
	}

}
