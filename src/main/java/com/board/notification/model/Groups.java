package com.board.notification.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Groups {
	private int groupId;
	private String groupName;
	private Boolean isPublic;
	private Integer createdBy;
	private Date createdDate;
	private Boolean isActive;

	public Groups() {
	}

	public Groups(int groupId, String groupName) {
		this.groupId = groupId;
		this.groupName = groupName;
	}

	public Groups(int groupId, String groupName, Boolean isPublic, Integer createdBy, Date createdDate,
			Boolean isActive) {
		this.groupId = groupId;
		this.createdBy = createdBy;
		this.groupName = groupName;
		this.isPublic = isPublic;
		this.createdDate = createdDate;
		this.isActive = isActive;
	}

	@Id
	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Boolean getIsPublic() {
		return this.isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
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

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

}
