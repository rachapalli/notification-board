package com.board.notification.dao;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.Users;

public interface UserRepo extends CrudRepository<Users, Integer> {

	@Query(value = "select * from users where email=:email")
	public Users findByEmail(String email);

	@Query(value = "select role_id from user_roles where user_id=:userId")
	public Integer getUserRole(@Param("userId") Integer userId);

	@Modifying
	@Query(value = "insert into user_roles (user_id, role_id, created_by, created_date, is_active) values (:userId, :roleId, :createdBy, now(), 1)")
	public Integer saveUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId,
			@Param("createdBy") Integer createdBy);

	@Modifying
	@Query(value = "update user_roles set role_id = :roleId where user_id=:userId")
	public Integer updateUserRole(@Param("roleId") Integer roleId, @Param("userId") Integer userId);
}
