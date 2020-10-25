package com.board.notification.service;

import com.board.notification.model.Users;

public interface UserService {
	Users createUpdateUser(Users user);

	Users getUserByEmail(String email);
}
