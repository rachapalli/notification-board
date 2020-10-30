package com.board.notification.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.GroupRepo;
import com.board.notification.dao.NotificationsRepo;
import com.board.notification.exception.DataNotFoundException;
import com.board.notification.exception.InvalidRequestException;
import com.board.notification.model.GroupNotification;
import com.board.notification.model.Groups;
import com.board.notification.model.Notifications;
import com.board.notification.service.NotificationService;
import com.board.notification.utils.NotificationConstants;

@Service
public class NotificationServiceImpl implements NotificationService {
	private static final Logger logger = LogManager.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationsRepo notificationsRepo;

	@Autowired
	private GroupRepo groupRepo;

	@Override
	public List<Notifications> getGroupNotification(String groupName) {
		if (groupName == null || groupName.isEmpty()) {
			throw new InvalidRequestException(NotificationConstants.REQUIRED_MSG + "groupName");
		}
		Groups group = groupRepo.findByGroupName(groupName);
		if (group == null) {
			throw new DataNotFoundException("Group not found with name:" + groupName);
		}
		return notificationsRepo.getGroupNotifications(group.getGroupId());
	}

	@Transactional
	@Override
	public GroupNotification saveTextGroupNotification(GroupNotification groupNotification) {
		if (groupNotification.getGroupId() == null) {
			throw new InvalidRequestException(NotificationConstants.REQUIRED_MSG + "groupName");
		}
		Optional<Groups> optGroup = groupRepo.findById(groupNotification.getGroupId());
		if (!optGroup.isPresent()) {
			throw new DataNotFoundException("Group not found with id:" + groupNotification.getGroupId());
		}

		Groups group = optGroup.get();
		Notifications notifications = new Notifications();
		BeanUtils.copyProperties(groupNotification, notifications);
		Notifications savedNotification = notificationsRepo.save(notifications);
		notificationsRepo.saveGroupNotification(group.getGroupId(), savedNotification.getNotificationId(),
				groupNotification.getCreatedBy());
		groupNotification.setNotificationId(savedNotification.getNotificationId());
		groupNotification.setGroupName(group.getGroupName());
		return groupNotification;
	}

	@Override
	public List<GroupNotification> getUserGroupNotifications(Integer userId) {
		return notificationsRepo.getGroupUserNotifications(userId);
	}

}
