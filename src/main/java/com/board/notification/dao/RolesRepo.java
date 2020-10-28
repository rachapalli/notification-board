package com.board.notification.dao;

import org.springframework.data.repository.CrudRepository;

import com.board.notification.model.Roles;

public interface RolesRepo extends CrudRepository<Roles, Integer> {
	
}
