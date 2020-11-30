package com.board.notification.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.GroupRepo;
import com.board.notification.dao.PermissionRepo;
import com.board.notification.dao.RolesRepo;
import com.board.notification.dao.UserRepo;
import com.board.notification.exception.AlreadyExistsException;
import com.board.notification.exception.DataNotFoundException;
import com.board.notification.exception.InvalidRequestException;
import com.board.notification.model.ActiveStatusEnum;
import com.board.notification.model.Groups;
import com.board.notification.model.Roles;
import com.board.notification.model.UserTypeEnum;
import com.board.notification.model.Users;
import com.board.notification.model.dto.AppUser;
import com.board.notification.model.dto.EmailDTO;
import com.board.notification.model.dto.PermissionDTO;
import com.board.notification.service.EmailService;
import com.board.notification.service.UserService;
import com.board.notification.utils.NotificationConstants;
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

	@Autowired
	private GroupRepo groupRepo;
	
	@Autowired
	private Environment env;
	
	@Override
	public AppUser createUser(AppUser appUser) {
		if (appUser.getUserId() != null) {
			throw new InvalidRequestException("User cannot be updated here");
		}
		validateUser(appUser);
		Users user = new Users();
		BeanUtils.copyProperties(appUser, user);
		user.setCreatedDate(NotificationUtils.getUKTime());
		Roles userRole = rolesRepo.findByRoleName(appUser.getUserType().toString());
		if (userRole == null) {
			throw new DataNotFoundException("Role " + appUser.getUserType() + NotificationConstants.MSG_NOT_FOUND);
		}

		EmailDTO emailDTO = new EmailDTO();
		
		if (UserTypeEnum.MEMBER.equals(appUser.getUserType())) {
			if (appUser.getGroupName() == null || appUser.getGroupName().isEmpty()) {
				throw new InvalidRequestException("For User type Member group name is mandatory");
			}
			Groups group = groupRepo.findByGroupName(appUser.getGroupName());
			if (group == null) {
				throw new InvalidRequestException(
						"Group " + appUser.getGroupName() + NotificationConstants.MSG_NOT_FOUND);
			}
			AppUser groupOwner = findUserById(group.getCreatedBy());
			emailDTO.setEmail(groupOwner.getEmail());
			emailDTO.setMessage(prepareUserRegBody(appUser.getUserName(), group.getGroupName(), appUser.getEmail()));
		} else 
		
		if (UserTypeEnum.BOARD_OWNER.equals(appUser.getUserType())) {
			emailDTO.setEmail(env.getProperty(NotificationConstants.DB_PROP_ADMIN_USER_EMAIL_ID));
			emailDTO.setMessage(prepareUserRegBody(appUser.getUserName(), "", appUser.getEmail()));
		}
		emailDTO.setSubject(env.getProperty(NotificationConstants.DB_PROP_USER_REGI_EMAIL_SUBJECT));
				
		user.setIsActive(ActiveStatusEnum.INACTIVE.statusFlag());
		user.setRoleId(userRole.getRoleId());
		Users savedUser = userRepo.save(user);
		appUser.setUserId(savedUser.getUserId());
		emailService.sendEmail(emailDTO);
		return appUser;
	}
	
	@Override
	public AppUser updateUser(AppUser appUser) {
		if (appUser.getUserId() == null) {
			throw new InvalidRequestException("User id required to update user");
		}
		Optional<Users> userOptional = userRepo.findById(appUser.getUserId());
		if (!userOptional.isPresent()) {
			throw new DataNotFoundException("User " + NotificationConstants.MSG_NOT_FOUND);
		}
		Users dbUser = userOptional.get();
		if (!dbUser.getEmail().equals(appUser.getEmail())) {
			validateUser(appUser);
		}
		dbUser.setEmail(appUser.getEmail());
		dbUser.setAlternateEmail(appUser.getAlternateEmail());
		dbUser.setUserName(appUser.getUserName());
		dbUser.setPassword(appUser.getPassword());
		dbUser.setContactNumber(appUser.getContactNumber());
		dbUser.setUpdatedDate(NotificationUtils.getUKTime());
		userRepo.save(dbUser);
		return appUser;
	}
	
	@Override
	public boolean deleteUser(String email) {
		boolean status = false;
		Users user = userRepo.findByEmail(email);
		if (user == null) {
			throw new DataNotFoundException("User " + NotificationConstants.MSG_NOT_FOUND);
		}
		user.setIsActive(ActiveStatusEnum.INACTIVE.statusFlag());
		user.setUpdatedDate(NotificationUtils.getUKTime());
		userRepo.save(user);
		status = true;
		return status;
	}

	private boolean validateUser(AppUser appUser) throws AlreadyExistsException {
		Users user = userRepo.findByEmail(appUser.getEmail());
		if (user != null) {
			throw new AlreadyExistsException(NotificationConstants.MSG_EMAIL_EXISTS);
		}
		if (UserTypeEnum.ADMIN.equals(appUser.getUserType())) {
			throw new InvalidRequestException("User type Admin cannot be updated or created");
		}
		return false;
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

	@Override
	public List<String> getAllActiveUserTypes() {
		return rolesRepo.getAllActiveRoles();
	}

	@Override
	public List<PermissionDTO> getRolePermission(Integer roleId) {
		return permissionRepo.findRolePermissionsByRoleId(roleId);
	}
	
	@Override
	public AppUser findUserById(Integer userId) {
		if (userId == null) {
			throw new InvalidRequestException("User Id " + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		Optional<Users> userOptional = userRepo.findById(userId);
		if (!userOptional.isPresent()) {
			throw new DataNotFoundException("User " + NotificationConstants.MSG_NOT_FOUND);
		}
		AppUser appUser = new AppUser();
		BeanUtils.copyProperties(userOptional.get(), appUser);
		return appUser;
	}
	
	private String prepareUserRegBody(String userName, String groupName, String email) {
		String message = new String(env.getProperty(NotificationConstants.DB_PROP_USER_REGI_EMAIL_BODY));
		message = message.replace(NotificationConstants.PH_BNAME, groupName)
				.replace(NotificationConstants.PH_USER_APPR_LINK,
						env.getProperty(NotificationConstants.DB_PROP_USER_REGI_APPR_LINK_)
								+ NotificationUtils.encodeString(email))
				.replace(NotificationConstants.PH_USER_NAME, userName);
		return message;
	}

}
