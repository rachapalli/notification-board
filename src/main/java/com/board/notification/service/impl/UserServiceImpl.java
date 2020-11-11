package com.board.notification.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.PermissionRepo;
import com.board.notification.dao.RolesRepo;
import com.board.notification.dao.UserRepo;
import com.board.notification.exception.DataNotFoundException;
import com.board.notification.model.ActiveStatusEnum;
import com.board.notification.model.AppUser;
import com.board.notification.model.Invitation;
import com.board.notification.model.Permission;
import com.board.notification.model.Roles;
import com.board.notification.model.StatusEnum;
import com.board.notification.model.UserTypeEnum;
import com.board.notification.model.Users;
import com.board.notification.service.EmailService;
import com.board.notification.service.UserService;
import com.board.notification.utils.NotificationUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RolesRepo rolesRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PermissionRepo permissionRepo;

	@Transactional
	@Override
	public AppUser createOrUpdateUser(AppUser appUser) {
		if (appUser.getUserId() != null) {
			if (appUser.getUserType() != null) {
				Roles role = rolesRepo.findByRoleName(appUser.getUserType().toString());
				Users user = new Users();
				BeanUtils.copyProperties(appUser, user);
				user.setRoleId(role.getRoleId());
				userRepo.save(user);
			}
		} else {
			Users user = new Users();
			BeanUtils.copyProperties(appUser, user);
			Roles userRole = rolesRepo.findByRoleName(appUser.getUserType().toString());
			if (userRole != null) {
				user.setIsActive(ActiveStatusEnum.INACTIVE.statusFlag());
				user.setRoleId(userRole.getRoleId());
				Users savedUser = userRepo.save(user);
				appUser.setUserId(savedUser.getUserId());
				sendActivationEmail(appUser);
			} else {
				new DataNotFoundException("Role not found");
			}
		}
		return appUser;
	}

	@Override
	public AppUser getUserByEmail(String email) {
		Users user = userRepo.findByEmail(email);
		AppUser appUser = null;
		if (user != null) {
			appUser = new AppUser();
			BeanUtils.copyProperties(user, appUser);
			Optional<Roles> optRole = rolesRepo.findById(user.getRoleId());
			if (optRole.isPresent()) {
				appUser.setUserType(UserTypeEnum.decode(optRole.get().getRoleName()));
				appUser.setPermissions(getRolePermission(user.getRoleId()));
			}
		} else {
			throw new DataNotFoundException("User not found.");
		}
		return appUser;
	}

	@Override
	public List<AppUser> getAllAppUsers() {
		List<AppUser> allUsers = new ArrayList<AppUser>();
		Iterable<Users> users = userRepo.findAll();
		for (Users user : users) {
			AppUser appUser = new AppUser();
			BeanUtils.copyProperties(user, appUser);
			if (user.getRoleId() != null) {
				Optional<Roles> optRole = rolesRepo.findById(user.getRoleId());
				if (optRole.isPresent()) {
					appUser.setUserType(UserTypeEnum.decode(optRole.get().getRoleName()));
					appUser.setPermissions(getRolePermission(user.getRoleId()));
				}
			}
			allUsers.add(appUser);
		}
		return allUsers;
	}

	@Transactional
	@Override
	public boolean activateUser(String input) {
		boolean status = false;
		if (input != null && !input.isEmpty()) {
			String userId = NotificationUtils.decodeString(input);
			Users user = userRepo.findByEmail(userId);
			if (user != null) {
				user.setIsActive(ActiveStatusEnum.ACTIVE.statusFlag());
				userRepo.save(user);
				status = true;
			}
		}
		return status;
	}

	private boolean sendActivationEmail(AppUser user) {
		boolean status = false;
		StringBuilder message = new StringBuilder();
		message.append("Welcome ").append(user.getUserName()).append("\n\n")
				.append("Please click on below link to activate").append("\n\n")
				.append("http://127.0.0.1:8080/user/activate?key=")
				.append(NotificationUtils.encodeString(user.getEmail()));
		Invitation invitation = new Invitation();
		invitation.setEmail(user.getEmail());
		invitation.setMessage(message.toString());
		invitation.setSubject("Finish setting up your new Account");
		StatusEnum statusEnum = emailService.sendEmail(invitation);
		status = StatusEnum.SUCCESS.equals(statusEnum);
		return status;
	}
	
	@Override
	public List<String> getAllActiveUserTypes() {
		return rolesRepo.getAllActiveRoles();
	}
	
	@Override
	public Permission getRolePermission(Integer roleId) {
		return permissionRepo.findByRoleId(roleId);
	}

}
