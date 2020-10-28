package com.board.notification.service;

import com.board.notification.model.Groups;


public interface GroupService {

	Groups createOrUpdateGroup(Groups group);

	Iterable<Groups> getAllGroups();
}
