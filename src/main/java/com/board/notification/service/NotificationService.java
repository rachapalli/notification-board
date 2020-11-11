package com.board.notification.service;

import java.util.List;

import com.board.notification.model.GroupNotification;

public interface NotificationService {
	
	List<GroupNotification> getGroupNotification(String groupName);
	
	GroupNotification saveGroupNotification(GroupNotification groupNotification);
	
	List<GroupNotification> getUserGroupNotifications(Integer userId);

}
