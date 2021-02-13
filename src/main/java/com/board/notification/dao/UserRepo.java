package com.board.notification.dao;

import java.util.List;

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

	@Query(value = "select * from users where role_id=:roleId order by created_date desc")
	public List<Users> findByUserRole(Integer roleId);
	
	@Query(value = "select r.role_name from users u, roles r where u.role_id = r.role_id and u.email= :email")
	public String getUserRoleNameByEmail(String email);
	
	@Query(value = "select u.email from users u, roles r where u.role_id = r.role_id and r.role_name= 'Product Owner'")
	public String getProductOwnerEmail();

}
