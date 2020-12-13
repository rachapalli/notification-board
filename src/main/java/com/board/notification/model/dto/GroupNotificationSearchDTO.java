package com.board.notification.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.board.notification.model.NotificationType;

public class GroupNotificationSearchDTO {
	@Email(message = "Invalid email")
	@NotBlank(message = "email cannot be null or empty")
	private String email;
	private NotificationType notificationType;
	private String groupName;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
