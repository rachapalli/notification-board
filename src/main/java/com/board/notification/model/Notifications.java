package com.board.notification.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Notifications {

	private Integer notificationId;
	private String message;
	private String notificationType;
	private Integer fileId;
	private Integer createdBy;
	private Date createdDate;

	public Notifications() {
	}


	@Id
	public Integer getNotificationId() {
		return this.notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNotificationType() {
		return this.notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
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
