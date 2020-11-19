package com.board.notification.service;

import java.util.List;

import com.board.notification.exception.AlreadyExistsException;
import com.board.notification.model.Groups;


public interface GroupService {

	Groups createOrUpdateGroup(Groups group) throws AlreadyExistsException;

	Iterable<Groups> getAllGroups();

	List<Groups> getUserGroups(String emailId);
}
