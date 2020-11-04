package com.board.notification.dao;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.board.notification.model.Users;

public interface UserRepo extends CrudRepository<Users, Integer> {

	@Query(value = "select * from users where email=:email")
	public Users findByEmail(String email);
}
