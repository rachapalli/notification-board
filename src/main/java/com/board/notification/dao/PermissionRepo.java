package com.board.notification.dao;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.Permission;
import com.board.notification.model.dto.PermissionDTO;

public interface PermissionRepo extends CrudRepository<Permission, Integer> {

	@Query(value = "select name, is_create, is_delete, is_view, is_edit from permission p, component c where p.component_id=c.component_id and p.role_id=:roleId")
	public List<PermissionDTO> findRolePermissionsByRoleId(@Param("roleId") Integer roleId);

}
