package com.board.notification.model.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.board.notification.model.UserTypeEnum;

public class AppUser {
	private Integer userId;

	@NotBlank(message = "Name is mandatory")
	private String userName;

	@NotBlank(message = "Password is mandatory")
	private String password;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Invalid Email Id")
	private String email;

	private String alternateEmail;

	@NotBlank(message = "Contact Number is mandatory")
	private String contactNumber;

	@NotNull(message = "User Type is mandatory")
	private UserTypeEnum userType;

	private String groupName;

	private Date createdDate;
	private Date updatedDate;

	private List<PermissionDTO> permissions;
	private Boolean isActive;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<PermissionDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

    public String getAuthorities() {
        return null;
    }
    
	@Override
	public String toString() {
		return "AppUser [userId=" + userId + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", alternateEmail=" + alternateEmail + ", contactNumber=" + contactNumber + ", userType=" + userType
				+ ", groupName=" + groupName + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ ", permissions=" + permissions + ", isActive=" + isActive + ", getUserId()=" + getUserId()
				+ ", getUserName()=" + getUserName() + ", getPassword()=" + getPassword() + ", getEmail()=" + getEmail()
				+ ", getAlternateEmail()=" + getAlternateEmail() + ", getContactNumber()=" + getContactNumber()
				+ ", getUserType()=" + getUserType() + ", getGroupName()=" + getGroupName() + ", getCreatedDate()="
				+ getCreatedDate() + ", getUpdatedDate()=" + getUpdatedDate() + ", getPermissions()=" + getPermissions()
				+ ", getIsActive()=" + getIsActive() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
