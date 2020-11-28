package com.board.notification.model.dto;

import javax.validation.constraints.NotNull;

public class DeleteGroupNotificationDTO {

	@NotNull(message = "Group id can not be null")
	private Integer groupId;

	@NotNull(message = "Notification Id can not be null")
	private Integer notificationId;

	private Integer updatedBy;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

}
