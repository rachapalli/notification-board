package com.board.notification.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GroupUsersDTO {
	@NotBlank(message = "userEmail cannot be null or empty")
	private String userEmail;
	private String userName;
	@NotBlank(message = "groupName cannot be null or empty")
	private String groupName;
	@NotNull(message = "isActive cannot be null")
	private Boolean isActive;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "GroupUsersDTO [userEmail=" + userEmail + ", userName=" + userName + ", groupName=" + groupName
				+ ", isActive=" + isActive + "]";
	}

}
