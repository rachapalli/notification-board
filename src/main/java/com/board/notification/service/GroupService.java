package com.board.notification.service;

import java.util.List;

import com.board.notification.model.Groups;


public interface GroupService {

	Groups createOrUpdateGroup(Groups group);

	Iterable<Groups> getAllGroups();

	List<Groups> getUserGroups(String emailId);
}
