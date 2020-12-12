package com.board.notification.service;

import java.util.List;

import com.board.notification.model.NotificationType;
import com.board.notification.model.dto.DeleteGroupNotificationDTO;
import com.board.notification.model.dto.GroupNotificationDTO;
import com.board.notification.model.dto.NotificationDTO;

public interface NotificationService {

	List<GroupNotificationDTO> getGroupNotification(Integer groupId);

	GroupNotificationDTO saveGroupNotification(GroupNotificationDTO groupNotification);

	List<GroupNotificationDTO> getUserGroupNotifications(String userEmail, NotificationType notificationType);

	NotificationDTO updateNotification(NotificationDTO notificationDTO);

	void deleteNotification(DeleteGroupNotificationDTO deleteGroupNotification);

}
