package com.board.notification.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Users {

	@Id
	private Integer userId;
	private String userName;
	private String password;
	private String email;
	private String alternateEmail;
	private String contactNumber;
	private Date createdDate;
	private Date updatedDate;
	private Boolean isActive;
	private Integer roleId;
	private Boolean isTempPwd;
	private Boolean isApproved;

	public Users() {
	}

	public Users(Integer userId, String userName, String password, String email, String contactNumber) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.contactNumber = contactNumber;
	}

	public Users(Integer userId, String userName, String password, String email, String contactNumber, Date createdDate,
			Date updatedDate, Boolean isActive) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.contactNumber = contactNumber;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.isActive = isActive;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Boolean getIsTempPwd() {
		return isTempPwd;
	}

	public void setIsTempPwd(Boolean isTempPwd) {
		this.isTempPwd = isTempPwd;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

}