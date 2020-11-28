package com.board.notification.model.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.board.notification.model.NotificationType;

public class NotificationDTO {

	private Integer notificationId;

	@NotNull(message = "Notification type can not be null")
	private NotificationType notificationType;

	@NotBlank(message = "Description can not be Empty/null")
	private String description;

	@Valid
	private MessageDTO message;

	@Valid
	private FileDTO file;

	private Integer createdBy;
	private Date createdDate;

	private Integer updatedBy;
	private Date updatedDate;

	public NotificationDTO() {

	}

	public NotificationDTO(Integer notificationId, NotificationType notificationType, String description, FileDTO file,
			Integer createdBy, Date createdDate, Integer updatedBy, Date updatedDate) {
		super();
		this.notificationId = notificationId;
		this.notificationType = notificationType;
		this.description = description;
		this.file = file;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	public NotificationDTO(Integer notificationId, NotificationType notificationType, String description,
			MessageDTO message, Integer createdBy, Date createdDate, Integer updatedBy, Date updatedDate) {
		super();
		this.notificationId = notificationId;
		this.notificationType = notificationType;
		this.description = description;
		this.message = message;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MessageDTO getMessage() {
		return message;
	}

	public void setMessage(MessageDTO message) {
		this.message = message;
	}

	public FileDTO getFile() {
		return file;
	}

	public void setFile(FileDTO file) {
		this.file = file;
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

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "NotificationDTO [notificationId=" + notificationId + ", notificationType=" + notificationType
				+ ", description=" + description + ", message=" + message + ", file=" + file + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + "]";
	}

}
