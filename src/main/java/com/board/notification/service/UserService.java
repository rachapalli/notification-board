package com.board.notification.service;

import java.util.List;

import com.board.notification.model.AppUser;
import com.board.notification.model.Permission;

public interface UserService {
	AppUser createOrUpdateUser(AppUser appUser);

	AppUser getUserByEmail(String email);

	List<AppUser> getAllAppUsers();
	
	boolean activateUser(String input);

	List<String> getAllActiveUserTypes();

	Permission getRolePermission(Integer roleId);
}