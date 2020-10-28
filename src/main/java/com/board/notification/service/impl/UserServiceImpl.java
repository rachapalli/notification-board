package com.board.notification.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.RolesRepo;
import com.board.notification.dao.UserRepo;
import com.board.notification.exception.DataNotFoundException;
import com.board.notification.model.AppUser;
import com.board.notification.model.Role;
import com.board.notification.model.Roles;
import com.board.notification.model.Users;
import com.board.notification.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepo userRepo;

	@Autowired
	public RolesRepo rolesRepo;

	@Transactional
	@Override
	public AppUser createOrUpdateUser(AppUser appUser) {
		if (appUser.getUserId() != null) {
			if (appUser.getRole() != null && appUser.getRole().getRoleId() != null) {
				Integer userRoleId = userRepo.getUserRole(appUser.getUserId());
				if (userRoleId != null && !userRoleId.equals(appUser.getRole().getRoleId())) {
					userRepo.updateUserRole(appUser.getRole().getRoleId(), appUser.getUserId());
				}
				Users user = new Users();
				BeanUtils.copyProperties(appUser, user);
				userRepo.save(user);
			}
		} else {
			Users user = new Users();
			BeanUtils.copyProperties(appUser, user);

			Optional<Roles> userRole = rolesRepo.findById(appUser.getRole().getRoleId());
			if (userRole.isPresent()) {
				Users savedUser = userRepo.save(user);
				userRepo.saveUserRole(savedUser.getUserId(), appUser.getRole().getRoleId(), appUser.getCreatedBy());
				appUser.setUserId(savedUser.getUserId());
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
					appUser.setRole(new Role(roles.getRoleId(), roles.getRoleName()));
				}
			}
			allUsers.add(appUser);
		}
		return allUsers;
	}

}
