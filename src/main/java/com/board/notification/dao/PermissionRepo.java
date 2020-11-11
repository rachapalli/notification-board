package com.board.notification.dao;

import org.springframework.data.repository.CrudRepository;

import com.board.notification.model.Permission;

public interface PermissionRepo extends CrudRepository<Permission, Integer> {
	public Permission findByRoleId(Integer roleId);
}
