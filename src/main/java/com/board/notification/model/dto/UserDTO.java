package com.board.notification.model.dto;

import java.util.Date;

public class UserDTO {

	private String userName;
	private String email;
	private Date createdDate;
	private Boolean isApproved;

	public UserDTO(String userName, String email, Date dateCreated) {
		super();
		this.userName = userName;
		this.email = email;
		this.createdDate = dateCreated;
	}

	public UserDTO() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	@Override
	public String toString() {
		return "UserDTO [userName=" + userName + ", email=" + email + ", createdDate=" + createdDate + ", isApproved="
				+ isApproved + "]";
	}

}
