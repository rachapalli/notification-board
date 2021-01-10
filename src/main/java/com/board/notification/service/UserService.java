package com.board.notification.service;

import java.util.List;

import com.board.notification.model.StatusEnum;
import com.board.notification.model.dto.AppUser;
import com.board.notification.model.dto.GroupUsersDTO;
import com.board.notification.model.dto.PermissionDTO;

public interface UserService {

	AppUser getUserByEmail(String email);

	List<AppUser> getAllAppUsers();

	boolean activateUser(String input);

	List<String> getAllActiveUserTypes();

	List<PermissionDTO> getRolePermission(Integer roleId);

	AppUser findUserById(Integer userId);

	AppUser createUser(AppUser appUser);

	AppUser updateUser(AppUser appUser);

	boolean deleteUser(String email);

	Integer updateGroupUser(GroupUsersDTO groupUsersDTO);

	StatusEnum resetPassword(String email);
}
