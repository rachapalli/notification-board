package com.board.notification.service;

import java.util.List;

import com.board.notification.model.Notifications;

public interface NotificationService {
	
	List<Notifications> getGroupNotification(String groupName);
	
	Notifications saveTextGroupNotification(String groupName, Notifications notification);

}
