package com.board.notification.dao;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.Users;

public interface UserRepo extends CrudRepository<Users, Integer> {

	@Query(value = "select * from users where email=:email")
	public Users findByEmail(String email);

	@Modifying
	@Query(value = "update user_groups set is_active = :isActive where user_id = :userId and group_id = :groupId")
	public Integer updateGroupUser(@Param("userId") Integer userId, @Param("groupId") Integer groupId,
			@Param("isActive") Integer isActive);
	
}
