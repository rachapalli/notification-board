package com.board.notification.service;

import java.util.List;

import com.board.notification.model.Permission;
import com.board.notification.model.dto.AppUser;

public interface UserService {

	AppUser getUserByEmail(String email);

	List<AppUser> getAllAppUsers();

	boolean activateUser(String input);

	List<String> getAllActiveUserTypes();

	Permission getRolePermission(Integer roleId);

	AppUser findUserById(Integer userId);

	AppUser createUser(AppUser appUser);

	AppUser updateUser(AppUser appUser);

	boolean deleteUser(String email);
}
