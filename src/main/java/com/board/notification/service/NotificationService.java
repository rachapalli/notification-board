package com.board.notification.service;

import java.util.List;

import com.board.notification.model.dto.DeleteGroupNotificationDTO;
import com.board.notification.model.dto.GroupNotificationDTO;
import com.board.notification.model.dto.GroupNotificationSearchDTO;
import com.board.notification.model.dto.NotificationDTO;

public interface NotificationService {

	List<GroupNotificationDTO> getGroupNotification(Integer groupId);

	GroupNotificationDTO saveGroupNotification(GroupNotificationDTO groupNotification);

	NotificationDTO updateNotification(NotificationDTO notificationDTO);

	void deleteNotification(DeleteGroupNotificationDTO deleteGroupNotification);

	List<GroupNotificationDTO> getAllUserGroupNotifications(GroupNotificationSearchDTO groupNotificationSearchDTO);

}
