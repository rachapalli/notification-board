package com.board.notification.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.GroupRepo;
import com.board.notification.exception.AlreadyExistsException;
import com.board.notification.exception.DataNotFoundException;
import com.board.notification.exception.InvalidRequestException;
import com.board.notification.model.ActiveStatusEnum;
import com.board.notification.model.Groups;
import com.board.notification.model.dto.GroupDTO;
import com.board.notification.model.dto.NotificationConverter;
import com.board.notification.service.GroupService;
import com.board.notification.utils.NotificationConstants;
import com.board.notification.utils.NotificationUtils;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepo groupRepo;

	@Transactional
	@Override
	public GroupDTO createGroup(GroupDTO groupDTO) {
		if (groupDTO.getGroupId() != null) {
			throw new InvalidRequestException("Group Cannot be updated");
		}
		Groups createdGroup = null;
		validateGroup(groupDTO);
		groupDTO.setCreatedDate(NotificationUtils.getUKTime());
		groupDTO.setIsActive(ActiveStatusEnum.ACTIVE.statusFlag());
		Groups groups = new Groups();
		BeanUtils.copyProperties(groupDTO, groups);
		createdGroup = groupRepo.save(groups);
		if (createdGroup != null) {
			groupRepo.addGroupUser(groupDTO.getCreatedBy(), createdGroup.getGroupId(), groupDTO.getCreatedBy(),
						NotificationUtils.getUKTime(), ActiveStatusEnum.ACTIVE.status());
		}
		groupDTO.setGroupId(createdGroup.getGroupId());
		return groupDTO;
	}

	@Override
	@Transactional
	public GroupDTO updateGroup(GroupDTO groupDTO) {
		if (groupDTO.getGroupId() == null) {
			throw new InvalidRequestException("To update group GroupId" + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		Optional<Groups> groupOptional = groupRepo.findById(groupDTO.getGroupId());
		if (groupOptional.isPresent()) {
			Groups groups = groupOptional.get();
			if (!groupDTO.getGroupName().equals(groups.getGroupName())) {
				validateGroup(groupDTO);
			}
			groups.setGroupName(groupDTO.getGroupName());
			groups.setIsPublic(groupDTO.getIsPublic());
			Groups savedGroup = groupRepo.save(groups);
			groupDTO.setCreatedDate(savedGroup.getCreatedDate());
			groupDTO.setCreatedBy(savedGroup.getCreatedBy());
		} else {
			throw new DataNotFoundException("Group id" + NotificationConstants.MSG_NOT_FOUND);
		}
		return groupDTO;
	}

	@Transactional
	@Override
	public GroupDTO deleteGroup(GroupDTO groupDTO) {
		if (groupDTO.getGroupId() == null) {
			throw new InvalidRequestException("To update group GroupId" + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		Optional<Groups> groupOptional = groupRepo.findById(groupDTO.getGroupId());
		if (groupOptional.isPresent()) {
			Groups groups = groupOptional.get();
			groups.setIsActive(ActiveStatusEnum.INACTIVE.statusFlag());
			groupRepo.save(groups);
		} else {
			throw new DataNotFoundException("Group id" + NotificationConstants.MSG_NOT_FOUND);
		}
		return groupDTO;
	}

	private boolean validateGroup(GroupDTO group) {
		if (groupRepo.findByGroupName(group.getGroupName()) != null) {
			throw new AlreadyExistsException(NotificationConstants.MSG_GROUP_EXISTS);
		}
		return false;
	}

	@Override
	public List<GroupDTO> getAllGroups() {
		return NotificationConverter.toGroupDTOs(groupRepo.findAll());
	}

	@Override
	public List<GroupDTO> getUserGroups(String emailId) {
		return NotificationConverter.toGroupDTOs(groupRepo.getAllUserGroupsByEmail(emailId));
	}

}
