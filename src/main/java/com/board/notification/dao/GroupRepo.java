package com.board.notification.dao;

import org.springframework.data.repository.CrudRepository;

import com.board.notification.model.Groups;

public interface GroupRepo extends CrudRepository<Groups, Integer> {
	public Groups findByGroupName(String groupName);
}
