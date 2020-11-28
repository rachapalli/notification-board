package com.board.notification.service;

import java.util.List;

import com.board.notification.model.dto.GroupDTO;


public interface GroupService {

	List<GroupDTO> getAllGroups();

	List<GroupDTO> getUserGroups(String emailId);

	GroupDTO deleteGroup(GroupDTO groupDTO);

	GroupDTO updateGroup(GroupDTO groupDTO);

	GroupDTO createGroup(GroupDTO groupDTO);
}
