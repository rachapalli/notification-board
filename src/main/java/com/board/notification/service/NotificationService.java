package com.board.notification.service;

import java.util.List;

import com.board.notification.model.GroupNotification;
import com.board.notification.model.Notifications;

public interface NotificationService {
	
	List<Notifications> getGroupNotification(String groupName);
	
	GroupNotification saveTextGroupNotification(GroupNotification groupNotification);
	
	List<GroupNotification> getUserGroupNotifications(Integer userId);

}
