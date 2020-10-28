package com.board.notification.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.notification.dao.GroupRepo;
import com.board.notification.model.Groups;
import com.board.notification.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepo groupRepo;

	@Override
	public Groups createOrUpdateGroup(Groups group) {
		return groupRepo.save(group);
	}

	@Override
	public Iterable<Groups> getAllGroups() {
		return groupRepo.findAll();
	}

}
