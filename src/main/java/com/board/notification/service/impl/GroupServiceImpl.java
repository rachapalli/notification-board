package com.board.notification.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.GroupRepo;
import com.board.notification.model.ActiveStatusEnum;
import com.board.notification.model.Groups;
import com.board.notification.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepo groupRepo;

	@Transactional
	@Override
	public Groups createOrUpdateGroup(Groups group) {
		Groups createdGroup = null;
		if (group.getGroupId() == null) {
			createdGroup = groupRepo.save(group);
			if (createdGroup != null) {
				groupRepo.addGroupUser(group.getCreatedBy(), createdGroup.getGroupId(), group.getCreatedBy(),
						ActiveStatusEnum.ACTIVE.status());
			}
		} else {
			createdGroup = groupRepo.save(group);
		}
		return createdGroup;
	}

	@Override
	public Iterable<Groups> getAllGroups() {
		return groupRepo.findAll();
	}
	
	@Override
	public List<Groups> getUserGroups(String emailId) {
		return groupRepo.getAllUserGroupsByEmail(emailId);
	}

}
