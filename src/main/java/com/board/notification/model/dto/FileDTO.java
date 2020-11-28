package com.board.notification.model.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class FileDTO {
	@NotNull(message = "File Id can not be null")
	private Integer fileId;
	private String name;
	private String fileKey;
	private Integer createdBy;
	private Date createdDate;

	public FileDTO(String fileKey, String name, Integer createdBy, Date createdDate) {
		super();
		this.fileKey = fileKey;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}
	
	public FileDTO() {
		super();
	}
	
	public FileDTO(Integer fileId, String fileKey, Integer createdBy, Date createdDate) {
		super();
		this.fileId = fileId;
		this.fileKey = fileKey;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "FileDTO [fileId=" + fileId + ", name=" + name + ", fileKey=" + fileKey + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + "]";
	}

}