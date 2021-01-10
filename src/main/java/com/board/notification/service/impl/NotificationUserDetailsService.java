package com.board.notification.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.board.notification.LoggingAspect;
import com.board.notification.exception.DataNotFoundException;
import com.board.notification.model.UserSecurityDetails;
import com.board.notification.model.dto.AppUser;
import com.board.notification.service.UserService;
import com.board.notification.utils.NotificationUtils;

@Service
public class NotificationUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

	@Autowired
	private UserService userServiceImpl;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userServiceImpl.getUserByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No User found with username '%s'.", username));
		} if (user.getIsActive() == null || !user.getIsActive()) {
			throw new DataNotFoundException("Inactive User");
		} else {
			try {
				return new UserSecurityDetails(user.getUserId(), user.getEmail(),
						NotificationUtils.decodeString(user.getPassword()), user.getEmail(), null, user.getPermissions(), user.getIsTempPwd());
			} catch (Exception e) {
				LOGGER.error("Exception in getting user by username : ", e);
			}
		}
		return null;
	}

}
