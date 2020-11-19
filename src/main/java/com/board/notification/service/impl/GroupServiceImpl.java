package com.board.notification.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.GroupRepo;
import com.board.notification.exception.AlreadyExistsException;
import com.board.notification.model.ActiveStatusEnum;
import com.board.notification.model.Groups;
import com.board.notification.service.GroupService;
import com.board.notification.utils.NotificationConstants;
import com.board.notification.utils.NotificationUtils;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepo groupRepo;

	@Transactional
	@Override
	public Groups createOrUpdateGroup(Groups group) throws AlreadyExistsException {
		Groups createdGroup = null;
		if (group.getGroupId() == null) {
			validateGroup(group);
			createdGroup = groupRepo.save(group);
			if (createdGroup != null) {
				groupRepo.addGroupUser(group.getCreatedBy(), createdGroup.getGroupId(), group.getCreatedBy(),
						NotificationUtils.getUKTime(), ActiveStatusEnum.ACTIVE.status());
			}
		} else {
			createdGroup = groupRepo.save(group);
		}
		return createdGroup;
	}
	
	private boolean validateGroup(Groups group) throws AlreadyExistsException {
		if (groupRepo.findByGroupName(group.getGroupName()) != null) {
			throw new AlreadyExistsException(NotificationConstants.MSG_GROUP_EXISTS);
		}
		return false;
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
