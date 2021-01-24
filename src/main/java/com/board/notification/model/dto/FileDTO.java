package com.board.notification.model.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class FileDTO {
	@NotNull(message = "File Id can not be null")
	private Integer fileId;
	private String fileKey;
	private Integer createdBy;
	private Date createdDate;

	public FileDTO(String fileKey, Integer createdBy, Date createdDate) {
		super();
		this.fileKey = fileKey;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public FileDTO(String fileKey, Date createdDate) {
		super();
		this.fileKey = fileKey;
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

	@Override
	public String toString() {
		return "FileDTO [fileId=" + fileId + ", fileKey=" + fileKey + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + "]";
	}

}