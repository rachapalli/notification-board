package com.board.notification.dao;

import org.springframework.data.repository.CrudRepository;

import com.board.notification.model.Users;

public interface UserRepo extends CrudRepository<Users, Long> {
	public Users findByEmail(String email);
}
