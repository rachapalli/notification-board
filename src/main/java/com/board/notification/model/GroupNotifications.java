package com.board.notification.model;

import java.util.Date;

public class GroupNotifications {

	private Integer groupId;
	private Integer notificationId;
	private Integer createdBy;
	private Date createdDate;
	private Boolean isActive;

	public GroupNotifications() {
	}

	public GroupNotifications(Integer groupId, Integer notificationId, Integer createdBy, Date createdDate,
			Boolean isActive) {
		this.groupId = groupId;
		this.notificationId = notificationId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.isActive = isActive;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getNotificationId() {
		return this.notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GroupNotifications))
			return false;
		GroupNotifications castOther = (GroupNotifications) other;

		return ((this.getGroupId() == castOther.getGroupId()) || (this.getGroupId() != null
				&& castOther.getGroupId() != null && this.getGroupId().equals(castOther.getGroupId())))
				&& ((this.getNotificationId() == castOther.getNotificationId())
						|| (this.getNotificationId() != null && castOther.getNotificationId() != null
								&& this.getNotificationId().equals(castOther.getNotificationId())))
				&& ((this.getCreatedBy() == castOther.getCreatedBy()) || (this.getCreatedBy() != null
						&& castOther.getCreatedBy() != null && this.getCreatedBy().equals(castOther.getCreatedBy())))
				&& ((this.getCreatedDate() == castOther.getCreatedDate())
						|| (this.getCreatedDate() != null && castOther.getCreatedDate() != null
								&& this.getCreatedDate().equals(castOther.getCreatedDate())))
				&& ((this.getIsActive() == castOther.getIsActive()) || (this.getIsActive() != null
						&& castOther.getIsActive() != null && this.getIsActive().equals(castOther.getIsActive())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getGroupId() == null ? 0 : this.getGroupId().hashCode());
		result = 37 * result + (getNotificationId() == null ? 0 : this.getNotificationId().hashCode());
		result = 37 * result + (getCreatedBy() == null ? 0 : this.getCreatedBy().hashCode());
		result = 37 * result + (getCreatedDate() == null ? 0 : this.getCreatedDate().hashCode());
		result = 37 * result + (getIsActive() == null ? 0 : this.getIsActive().hashCode());
		return result;
	}

}
