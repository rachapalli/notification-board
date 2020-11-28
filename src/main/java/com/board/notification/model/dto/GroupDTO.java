package com.board.notification.model.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GroupDTO {
	private Integer groupId;
	
	@NotBlank(message = "Group Name cannot be null or empty")
	private String groupName;
	
	@NotNull(message = "isPublic cannot be null")
	private Boolean isPublic;
	
	@NotNull(message = "createdBy cannot be null")
	private Integer createdBy;
	
	private Date createdDate;
	private Boolean isActive;

	public GroupDTO() {
	}

	public GroupDTO(Integer groupId, String groupName) {
		this.groupId = groupId;
		this.groupName = groupName;
	}

	public GroupDTO(Integer groupId, String groupName, Boolean isPublic, Integer createdBy, Date createdDate,
			Boolean isActive) {
		this.groupId = groupId;
		this.createdBy = createdBy;
		this.groupName = groupName;
		this.isPublic = isPublic;
		this.createdDate = createdDate;
		this.isActive = isActive;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
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
