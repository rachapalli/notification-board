package com.board.notification.model.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class GroupNotificationDTO {

	@NotNull(message = "Group id can not be null")
	private Integer groupId;

	@NotNull(message = "Notification type can not be null")
	@Valid
	private NotificationDTO notification;

	private String groupName;

	private Integer createdBy;
	
	private Date createdDate;

	private Boolean isActive;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public NotificationDTO getNotification() {
		return notification;
	}

	public void setNotification(NotificationDTO notification) {
		this.notification = notification;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
