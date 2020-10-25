package com.board.notification.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.notification.dao.UserRepo;
import com.board.notification.model.Users;
import com.board.notification.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepo userRepo;
	
	@Override
	public Users createUpdateUser(Users user) {
		return userRepo.save(user);
	}
	
	@Override
	public Users getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	
}
