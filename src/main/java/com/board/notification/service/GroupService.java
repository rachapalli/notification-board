package com.board.notification.service;

import java.util.List;

import com.board.notification.model.dto.GroupDTO;
import com.board.notification.model.dto.GroupUsersDTO;


public interface GroupService {

	List<GroupDTO> getAllGroups();

	List<GroupDTO> getUserGroups(String emailId);

	GroupDTO deleteGroup(GroupDTO groupDTO);

	GroupDTO updateGroup(GroupDTO groupDTO);

	GroupDTO createGroup(GroupDTO groupDTO);

	List<GroupDTO> getOwnerGroups(String emailId);

	GroupDTO findByGroupName(String groupName);

	List<GroupUsersDTO> getGroupUsers(String email);

	void addGroupUser(Integer userId, Integer groupId, Integer createdBy, Boolean isActive);
}
