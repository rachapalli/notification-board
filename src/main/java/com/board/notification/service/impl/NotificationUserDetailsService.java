package com.board.notification.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.board.notification.model.Users;
import com.board.notification.service.UserService;

@Service
public class NotificationUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userServiceImpl;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userServiceImpl.getUserByEmail(username);
		if (null != user) {
			return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
		}
		return null;
	}

}
