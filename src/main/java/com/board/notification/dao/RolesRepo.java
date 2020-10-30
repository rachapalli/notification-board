package com.board.notification.dao;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.Roles;

public interface RolesRepo extends CrudRepository<Roles, Integer> {

	@Query(value = "select * from roles where role_name=:roleName")
	public Roles findByRoleName(@Param("roleName") String roleName);

	@Query(value = "select role_name from roles where is_active=1;")
	public List<String> getAllActiveRoles();
}
