package com.board.notification.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.board.notification.model.dto.PermissionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserSecurityDetails implements UserDetails {

	private static final long serialVersionUID = 8204034264655300422L;
	private Integer id;
	private String username;
	private String password;
	private String email;
	private Date lastPasswordReset;
	private Collection<? extends GrantedAuthority> authorities;
	private Boolean isTempPwd;
	private Boolean accountNonExpired = true;
	private Boolean accountNonLocked = true;
	private Boolean credentialsNonExpired = true;
	private Boolean enabled = true;
	private List<PermissionDTO> permissions = null;

	public UserSecurityDetails() {
		super();
	}

	/**
	 * Parameterized constructor to initialized the fields
	 * 
	 * @param id                user id
	 * @param username          username
	 * @param password          password
	 * @param email             email
	 * @param lastPasswordReset last password
	 * @param authorities       authrorities or role information for permission
	 */
	public UserSecurityDetails(Integer id, String username, String password, String email, Date lastPasswordReset,
			Collection<? extends GrantedAuthority> authorities) {
		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
		this.setLastPasswordReset(lastPasswordReset);
		this.setAuthorities(authorities);
	}

	/**
	 * Parameterized constructor to initialized the fields
	 * 
	 * @param id                user id
	 * @param username          username
	 * @param password          password
	 * @param email             email
	 * @param lastPasswordReset last password
	 * @param permissions       authrorities or role information for permission
	 * @param isTempPwd
	 */
	public UserSecurityDetails(Integer id, String username, String password, String email, Date lastPasswordReset,
			List<PermissionDTO> permissions, Boolean isTempPwd) {
		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
		this.setLastPasswordReset(lastPasswordReset);
		this.setPermissions(permissions);
		this.setIsTempPwd(isTempPwd);
	}

	/**
	 * @return returns user id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @param id accepts user id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return returns usename
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username accepts username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return returns password
	 */
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password accepts password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return returns user email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email accepts user email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return returns last password date
	 */
	@JsonIgnore
	public Date getLastPasswordReset() {
		return this.lastPasswordReset;
	}

	/**
	 * @param id accepts last password date
	 */
	public void setLastPasswordReset(Date lastPasswordReset) {
		this.lastPasswordReset = lastPasswordReset;
	}

	/**
	 * @return returns authorities or roles
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	/**
	 * @param authorities accepts authorities value
	 */
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return returns account non expired
	 */
	@JsonIgnore
	public Boolean getAccountNonExpired() {
		return this.accountNonExpired;
	}

	/**
	 * @param accountNonExpired accepts account non expired boolean value
	 */
	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @return returns true or false after checking the account expiration
	 */
	public boolean isAccountNonExpired() {
		return this.getAccountNonExpired();
	}

	/**
	 * @return returns true or false after checking the account lock info
	 */
	@JsonIgnore
	public Boolean getAccountNonLocked() {
		return this.accountNonLocked;
	}

	/**
	 * @param accountNonLocked sets the account lock status to true
	 */
	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @return returns true or false after checking the account lock info
	 */
	public boolean isAccountNonLocked() {
		return this.getAccountNonLocked();
	}

	/**
	 * @return returns true or false after checking the credential expiration info
	 */
	@JsonIgnore
	public Boolean getCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	/**
	 * @param credentialsNonExpired set true or false after checking the credential
	 *                              expiration info
	 */
	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @return returns true or false after checking the credential expiration info
	 */
	public boolean isCredentialsNonExpired() {
		return this.getCredentialsNonExpired();
	}

	/**
	 * @return returns user account info based on whether it is enabled or not
	 */
	@JsonIgnore
	public Boolean getEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled sets user account info based on whether it is enabled or not
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return returns user account info based on whether it is enabled or not
	 */
	public boolean isEnabled() {
		return this.getEnabled();
	}

	public List<PermissionDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}

	public Boolean getIsTempPwd() {
		return isTempPwd;
	}

	public void setIsTempPwd(Boolean isTempPwd) {
		this.isTempPwd = isTempPwd;
	}

}
