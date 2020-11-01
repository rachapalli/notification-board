package com.board.notification.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class AllFiles {

	@Id
	private Integer fileId;
	private String name;
	private String fileKey;
	private Integer createdBy;
	private Date createdDate;

	public AllFiles(String name, String fileKey, Integer createdBy, Date createdDate) {
		super();
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
