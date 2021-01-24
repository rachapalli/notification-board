package com.board.notification.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.GroupRepo;
import com.board.notification.dao.NotificationMessageRepo;
import com.board.notification.dao.NotificationsRepo;
import com.board.notification.exception.DataNotFoundException;
import com.board.notification.exception.InvalidRequestException;
import com.board.notification.exception.NotificationException;
import com.board.notification.model.ActiveStatusEnum;
import com.board.notification.model.FileGroupNotification;
import com.board.notification.model.Groups;
import com.board.notification.model.MessageGroupNotification;
import com.board.notification.model.NotificationMessage;
import com.board.notification.model.NotificationType;
import com.board.notification.model.Notifications;
import com.board.notification.model.UserTypeEnum;
import com.board.notification.model.dto.AppUser;
import com.board.notification.model.dto.DeleteGroupNotificationDTO;
import com.board.notification.model.dto.FileDTO;
import com.board.notification.model.dto.GroupNotificationDTO;
import com.board.notification.model.dto.GroupNotificationSearchDTO;
import com.board.notification.model.dto.MessageDTO;
import com.board.notification.model.dto.NotificationConverter;
import com.board.notification.model.dto.NotificationDTO;
import com.board.notification.service.NotificationService;
import com.board.notification.service.UserService;
import com.board.notification.utils.NotificationConstants;
import com.board.notification.utils.NotificationUtils;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationsRepo notificationsRepo;

	@Autowired
	private NotificationMessageRepo notificationMessageRepo;

	@Autowired
	private GroupRepo groupRepo;

	@Autowired
	private UserService userService;

	@Override
	public List<GroupNotificationDTO> getGroupNotification(Integer groupId) {
		List<FileGroupNotification> fileGroupNotifications = notificationsRepo
				.getFileGroupNotifications(groupId);
		List<MessageGroupNotification> messageGroupNotifications = notificationsRepo
				.getMessageGroupNotifications(groupId);

		List<GroupNotificationDTO> groupNotifications = NotificationConverter
				.toGroupNotifications(messageGroupNotifications);
		groupNotifications.addAll(NotificationConverter.toGroupNotificationdDtos(fileGroupNotifications));

		return groupNotifications;
	}

	@Transactional
	@Override
	public GroupNotificationDTO saveGroupNotification(GroupNotificationDTO groupNotification) {
		if (groupNotification.getGroupId() == null) {
			throw new InvalidRequestException(NotificationConstants.REQUIRED_MSG + "groupId");
		}
		Optional<Groups> optGroup = groupRepo.findById(groupNotification.getGroupId());
		if (!optGroup.isPresent()) {
			throw new DataNotFoundException("Group not found with id:" + groupNotification.getGroupId());
		}

		Groups group = optGroup.get();
		Notifications notifications = new Notifications();
		groupNotification.setCreatedDate(NotificationUtils.getUKTime());
		groupNotification.setIsActive(ActiveStatusEnum.ACTIVE.statusFlag());

		NotificationDTO notificationDTO = groupNotification.getNotification();
		notifications.setDescription(notificationDTO.getDescription());
		notifications.setNtype(notificationDTO.getNotificationType().toString());
		notifications.setCreatedBy(groupNotification.getCreatedBy());
		notifications.setCreatedDate(groupNotification.getCreatedDate());

		if (NotificationType.TEXT.equals(notificationDTO.getNotificationType())) {
			MessageDTO message = notificationDTO.getMessage();
			if (message == null || message.getMessage() == null || message.getMessage().isEmpty()) {
				throw new InvalidRequestException(NotificationConstants.REQUIRED_MSG + "message");
			}
			NotificationMessage notificationMessage = new NotificationMessage();
			notificationMessage.setCreatedBy(groupNotification.getCreatedBy());
			notificationMessage.setCreatedDate(groupNotification.getCreatedDate());
			BeanUtils.copyProperties(message, notificationMessage);
			notificationMessage = notificationMessageRepo.save(notificationMessage);
			message.setMessageId(notificationMessage.getMessageId());
			notifications.setMessageId(notificationMessage.getMessageId());
		} else 

		if (NotificationType.FILE.equals(notificationDTO.getNotificationType())) {
			FileDTO file = notificationDTO.getFile();
			if (file == null || file.getFileId() == null ) {
				throw new InvalidRequestException(NotificationConstants.REQUIRED_MSG + "file/fileId");
			}
			notifications.setFileId(file.getFileId());
		}

		Notifications savedNotification = notificationsRepo.save(notifications);
		notificationDTO.setNotificationId(savedNotification.getNotificationId());
		notificationsRepo.saveGroupNotification(group.getGroupId(), savedNotification.getNotificationId(),
				groupNotification.getCreatedBy(), NotificationUtils.getUKTime());
		groupNotification.setGroupName(group.getGroupName());
		return groupNotification;
	}

	@Override
	@Transactional
	public NotificationDTO updateNotification(NotificationDTO notificationDTO) {
		if (notificationDTO.getNotificationId() == null) {
			throw new InvalidRequestException(NotificationConstants.REQUIRED_MSG + "notificationId");
		}

		Optional<Notifications> notiOptional = notificationsRepo.findById(notificationDTO.getNotificationId());
		Notifications notifications = null;
		if (notiOptional.isPresent()) {
			notifications = notiOptional.get();
			if (!notificationDTO.getNotificationType().toString().equals(notifications.getNtype())) {
				throw new InvalidRequestException("Notification type can't be updated");
			}
			notifications.setNtype(notificationDTO.getNotificationType().toString());
			notifications.setUpdatedBy(notificationDTO.getUpdatedBy());
			notifications.setUpdatedDate(NotificationUtils.getUKTime());
			notifications.setDescription(notificationDTO.getDescription());
		} else {
			throw new DataNotFoundException("Notification id" + NotificationConstants.MSG_NOT_FOUND);
		}

		if (NotificationType.TEXT.equals(notificationDTO.getNotificationType())) {
			MessageDTO messageDTO = notificationDTO.getMessage();
			if (messageDTO == null || messageDTO.getMessage() == null || messageDTO.getMessage().isEmpty()
					|| messageDTO.getMessageId() == null) {
				throw new InvalidRequestException(
						"For a Notification type TEXT message/message id" + NotificationConstants.MSG_NOT_NULL_EMPTY);
			}
			Optional<NotificationMessage> notMsgOptional = notificationMessageRepo.findById(messageDTO.getMessageId());
			if (notMsgOptional.isPresent()) {
				NotificationMessage notificationMessage = notMsgOptional.get();
				notificationMessage.setMessage(messageDTO.getMessage());
				notificationMessageRepo.save(notificationMessage);
			} else {
				throw new DataNotFoundException("Message id" + NotificationConstants.MSG_NOT_FOUND);
			}
		}
		
		if (NotificationType.FILE.equals(notificationDTO.getNotificationType())) {
			 FileDTO file = notificationDTO.getFile();
			 if (file == null || file.getFileId() == null) {
				 throw new InvalidRequestException(
							"For a Notification type FILE file/file id" + NotificationConstants.MSG_NOT_NULL_EMPTY);
			 }
			notifications.setFileId(file.getFileId());
		}
		notificationsRepo.save(notifications);
		notificationsRepo.enableGroupNotification(notifications.getNotificationId());
		return notificationDTO;
	}
	
	@Override
	public List<GroupNotificationDTO> getAllUserGroupNotifications(GroupNotificationSearchDTO groupNotificationSearchDTO) {
		AppUser user = userService.getUserByEmail(groupNotificationSearchDTO.getEmail());
		if (user == null) {
			throw new DataNotFoundException(NotificationConstants.INVALID_USER_EMAIL);
		}
		List<GroupNotificationDTO> groupUserNotifications = Collections.emptyList() ;
		if (UserTypeEnum.MEMBER.equals(user.getUserType())) {
			groupUserNotifications = getUserGroupNotifications(groupNotificationSearchDTO, user.getUserId());
		} if (UserTypeEnum.BOARD_OWNER.equals(user.getUserType()) && user.getIsApproved()) {
			groupUserNotifications = getUserCreatedGroupNotifications(groupNotificationSearchDTO, user.getUserId());
		}
		return groupUserNotifications;
	}
	
	public List<GroupNotificationDTO> getUserCreatedGroupNotifications(GroupNotificationSearchDTO groupNotificationSearchDTO, Integer userId) {
		List<GroupNotificationDTO> groupUserNotifications = null;
		if (groupNotificationSearchDTO.getGroupName() != null && !groupNotificationSearchDTO.getGroupName().isEmpty()
				&& groupNotificationSearchDTO.getNotificationType() != null) {
			if (NotificationType.TEXT.equals(groupNotificationSearchDTO.getNotificationType())) {
				List<MessageGroupNotification> userMessageGroupNotifications = notificationsRepo
						.getUserCreatedMessageGroupNotificationsByGroup(userId, groupNotificationSearchDTO.getGroupName());
				groupUserNotifications = NotificationConverter.toGroupNotifications(userMessageGroupNotifications);
				
			} else if (NotificationType.FILE.equals(groupNotificationSearchDTO.getNotificationType())) {
				List<FileGroupNotification> userFileGroupNotifications = notificationsRepo
						.getUserCreatedFileGroupNotificationsByGroup(userId, groupNotificationSearchDTO.getGroupName());
				groupUserNotifications = NotificationConverter.toGroupNotificationdDtos(userFileGroupNotifications);
			}
		} else if (groupNotificationSearchDTO.getGroupName() != null && !groupNotificationSearchDTO.getGroupName().isEmpty()) {
			List<MessageGroupNotification> userMessageGroupNotifications = notificationsRepo
					.getUserCreatedMessageGroupNotificationsByGroup(userId, groupNotificationSearchDTO.getGroupName());
			List<FileGroupNotification> userFileGroupNotifications = notificationsRepo
					.getUserCreatedFileGroupNotificationsByGroup(userId, groupNotificationSearchDTO.getGroupName());
			groupUserNotifications = NotificationConverter.toGroupNotifications(userMessageGroupNotifications);
			groupUserNotifications.addAll(NotificationConverter.toGroupNotificationdDtos(userFileGroupNotifications));
		} else if (groupNotificationSearchDTO.getNotificationType() != null) {
			if (NotificationType.TEXT.equals(groupNotificationSearchDTO.getNotificationType())) {
				List<MessageGroupNotification> userMessageGroupNotifications = notificationsRepo
						.getUserCreatedMessageGroupNotifications(userId);
				groupUserNotifications = NotificationConverter.toGroupNotifications(userMessageGroupNotifications);
				
			} else if (NotificationType.FILE.equals(groupNotificationSearchDTO.getNotificationType())) {
				List<FileGroupNotification> userFileGroupNotifications = notificationsRepo
						.getUserCreatedFileGroupNotifications(userId);
				groupUserNotifications = NotificationConverter.toGroupNotificationdDtos(userFileGroupNotifications);
			} 
		} else {
			List<MessageGroupNotification> userMessageGroupNotifications = notificationsRepo
					.getUserCreatedMessageGroupNotifications(userId);
			List<FileGroupNotification> userFileGroupNotifications = notificationsRepo
					.getUserCreatedFileGroupNotifications(userId);
			groupUserNotifications = NotificationConverter.toGroupNotifications(userMessageGroupNotifications);
			groupUserNotifications.addAll(NotificationConverter.toGroupNotificationdDtos(userFileGroupNotifications));
		}
		return groupUserNotifications;
	}
	
	
	public List<GroupNotificationDTO> getUserGroupNotifications(GroupNotificationSearchDTO groupNotificationSearchDTO, Integer userId) {
		List<GroupNotificationDTO> groupUserNotifications = null;
		if (groupNotificationSearchDTO.getGroupName() != null && !groupNotificationSearchDTO.getGroupName().isEmpty()
				&& groupNotificationSearchDTO.getNotificationType() != null) {
			if (NotificationType.TEXT.equals(groupNotificationSearchDTO.getNotificationType())) {
				List<MessageGroupNotification> userMessageGroupNotifications = notificationsRepo
						.getUserMessageGroupNotificationsByGroup(userId, groupNotificationSearchDTO.getGroupName());
				groupUserNotifications = NotificationConverter.toGroupNotifications(userMessageGroupNotifications);
				
			} else if (NotificationType.FILE.equals(groupNotificationSearchDTO.getNotificationType())) {
				List<FileGroupNotification> userFileGroupNotifications = notificationsRepo
						.getUserFileGroupNotificationsByGroup(userId, groupNotificationSearchDTO.getGroupName());
				groupUserNotifications = NotificationConverter.toGroupNotificationdDtos(userFileGroupNotifications);
			}
		} else if (groupNotificationSearchDTO.getGroupName() != null && !groupNotificationSearchDTO.getGroupName().isEmpty()) {
			List<MessageGroupNotification> userMessageGroupNotifications = notificationsRepo
					.getUserMessageGroupNotificationsByGroup(userId, groupNotificationSearchDTO.getGroupName());
			List<FileGroupNotification> userFileGroupNotifications = notificationsRepo
					.getUserFileGroupNotificationsByGroup(userId, groupNotificationSearchDTO.getGroupName());
			groupUserNotifications = NotificationConverter.toGroupNotifications(userMessageGroupNotifications);
			groupUserNotifications.addAll(NotificationConverter.toGroupNotificationdDtos(userFileGroupNotifications));
			
		} else if (groupNotificationSearchDTO.getNotificationType() != null) {
			if (NotificationType.TEXT.equals(groupNotificationSearchDTO.getNotificationType())) {
				List<MessageGroupNotification> userMessageGroupNotifications = notificationsRepo.getUserMessageGroupNotifications(userId);
				groupUserNotifications = NotificationConverter.toGroupNotifications(userMessageGroupNotifications);
			} else if (NotificationType.FILE.equals(groupNotificationSearchDTO.getNotificationType())) {
				List<FileGroupNotification> userFileGroupNotifications = notificationsRepo.getUserFileGroupNotifications(userId);
				groupUserNotifications = NotificationConverter.toGroupNotificationdDtos(userFileGroupNotifications);
			} 
			
		} else {
			List<MessageGroupNotification> userMessageGroupNotifications = notificationsRepo.getUserMessageGroupNotifications(userId);
			List<FileGroupNotification> userFileGroupNotifications = notificationsRepo.getUserFileGroupNotifications(userId);
			groupUserNotifications = NotificationConverter.toGroupNotifications(userMessageGroupNotifications);
			groupUserNotifications.addAll(NotificationConverter.toGroupNotificationdDtos(userFileGroupNotifications));
		}
		return groupUserNotifications;
	}
	
	@Override
	@Transactional
	public void deleteNotification(DeleteGroupNotificationDTO deleteGroupNotification) {
		Integer updatedCount = notificationsRepo.deleteGroupNotification(deleteGroupNotification.getGroupId(),
				deleteGroupNotification.getNotificationId());
		if (updatedCount == 0) {
			throw new NotificationException("Unable to delete the Notification");
		}
	}
}
