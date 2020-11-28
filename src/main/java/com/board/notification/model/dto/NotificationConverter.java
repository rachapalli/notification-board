package com.board.notification.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.board.notification.model.FileGroupNotification;
import com.board.notification.model.MessageGroupNotification;
import com.board.notification.model.NotificationType;

public class NotificationConverter {

	public static GroupNotificationDTO toGroupNotificationDTO(MessageGroupNotification messageNotification) {
		MessageDTO messageDTO = new MessageDTO(messageNotification.getMessageId(), messageNotification.getMessage());
		NotificationDTO notificationDTO = new NotificationDTO(messageNotification.getNotificationId(),
				NotificationType.decode(messageNotification.getNtype()), messageNotification.getDescription(),
				messageDTO, messageNotification.getCreatedBy(), messageNotification.getCreatedDate(),
				messageNotification.getUpdatedBy(), messageNotification.getUpdatedDate());
		GroupNotificationDTO groupNotification = new GroupNotificationDTO();
		groupNotification.setGroupId(messageNotification.getGroupId());
		groupNotification.setGroupName(messageNotification.getGroupName());
		groupNotification.setNotification(notificationDTO);
		groupNotification.setCreatedBy(messageNotification.getCreatedBy());
		groupNotification.setCreatedDate(messageNotification.getCreatedDate());
		groupNotification.setIsActive(messageNotification.getIsActive());
		return groupNotification;
	}

	public static GroupNotificationDTO toGroupNotificationDTO(FileGroupNotification fileNotification) {
		FileDTO fileDTO = new FileDTO(fileNotification.getFileId(), fileNotification.getFileKey(),
				fileNotification.getCreatedBy(), fileNotification.getCreatedDate());
		NotificationDTO notificationDTO = new NotificationDTO(fileNotification.getNotificationId(),
				NotificationType.decode(fileNotification.getNtype()), fileNotification.getDescription(), fileDTO,
				fileNotification.getCreatedBy(), fileNotification.getCreatedDate(), fileNotification.getUpdatedBy(),
				fileNotification.getUpdatedDate());
		GroupNotificationDTO groupNotification = new GroupNotificationDTO();
		groupNotification.setGroupId(fileNotification.getGroupId());
		groupNotification.setGroupName(fileNotification.getGroupName());
		groupNotification.setNotification(notificationDTO);
		groupNotification.setCreatedBy(fileNotification.getCreatedBy());
		groupNotification.setCreatedDate(fileNotification.getCreatedDate());
		groupNotification.setIsActive(fileNotification.getIsActive());
		return groupNotification;
	}

	public static List<GroupNotificationDTO> toGroupNotifications(List<MessageGroupNotification> messageNotifications) {
		List<GroupNotificationDTO> groupNotifications = new ArrayList<>();
		if (messageNotifications != null) {
			for (MessageGroupNotification messageGroupNotification : messageNotifications) {
				groupNotifications.add(toGroupNotificationDTO(messageGroupNotification));
			}
		}
		return groupNotifications;
	}

	public static List<GroupNotificationDTO> toGroupNotificationdDtos(List<FileGroupNotification> fileNotifications) {
		List<GroupNotificationDTO> groupNotifications = new ArrayList<>();
		if (fileNotifications != null) {
			for (FileGroupNotification fileNotification : fileNotifications) {
				groupNotifications.add(toGroupNotificationDTO(fileNotification));
			}
		}
		return groupNotifications;
	}
}
