package com.board.notification.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.RolesRepo;
import com.board.notification.dao.UserRepo;
import com.board.notification.exception.DataNotFoundException;
import com.board.notification.model.ActiveStatusEnum;
import com.board.notification.model.AppUser;
import com.board.notification.model.Invitation;
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

	@Transactional
	@Override
	public AppUser createOrUpdateUser(AppUser appUser) {
		if (appUser.getUserId() != null) {
			if (appUser.getUserType() != null) {
				Integer userRoleId = userRepo.getUserRole(appUser.getUserId());
				Roles userRole = rolesRepo.findByRoleName(appUser.getUserType().toString());
				if (userRoleId != null && !userRoleId.equals(userRole.getRoleId())) {
					userRepo.updateUserRole(userRole.getRoleId(), appUser.getUserId());
				}
				Users user = new Users();
				BeanUtils.copyProperties(appUser, user);
				userRepo.save(user);
			}
		} else {
			Users user = new Users();
			BeanUtils.copyProperties(appUser, user);
			Roles userRole = rolesRepo.findByRoleName(appUser.getUserType().toString());
			if (userRole != null) {
				user.setIsActive(ActiveStatusEnum.INACTIVE.statusFlag());
				Users savedUser = userRepo.save(user);
				userRepo.saveUserRole(savedUser.getUserId(), userRole.getRoleId(), appUser.getCreatedBy(), new Date());
				appUser.setUserId(savedUser.getUserId());
				sendActivationEmail(appUser);
			} else {
				new DataNotFoundException("Role not found");
			}
		}
		return appUser;
	}

	@Override
	public Users getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public List<AppUser> getAllAppUsers() {
		List<AppUser> allUsers = new ArrayList<AppUser>();
		Iterable<Users> users = userRepo.findAll();
		for (Users user : users) {
			AppUser appUser = new AppUser();
			BeanUtils.copyProperties(user, appUser);
			Integer userRoleId = userRepo.getUserRole(user.getUserId());
			if (userRoleId != null) {
				Optional<Roles> optRole = rolesRepo.findById(userRoleId);
				if (optRole.isPresent()) {
					Roles roles = optRole.get();
					appUser.setUserType(UserTypeEnum.decode( roles.getRoleName()));
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

}
