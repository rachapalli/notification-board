package com.board.notification.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.exception.InvalidRequestException;
import com.board.notification.model.AuthenticationResponse;
import com.board.notification.model.dto.CommonResponse;
import com.board.notification.model.dto.DeleteGroupNotificationDTO;
import com.board.notification.model.dto.GroupNotificationDTO;
import com.board.notification.model.dto.NotificationDTO;
import com.board.notification.service.NotificationService;
import com.board.notification.utils.NotificationConstants;

@RestController
@RequestMapping("/notification")
public class NotificationControlller {

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/getNotifications/{groupName}")
	public List<GroupNotificationDTO> getNotifications(@PathVariable(name = "groupName") String groupName) {
		return notificationService.getGroupNotification(groupName);
	}

	@PostMapping("/create")
	public ResponseEntity<?> saveGroupNotification(@Valid @RequestBody GroupNotificationDTO groupNotification) {
		GroupNotificationDTO savedGroupNotification = notificationService.saveGroupNotification(groupNotification);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_CREATE_SUCCESS, savedGroupNotification));
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateNotification(@Valid @RequestBody NotificationDTO notification) {
		NotificationDTO updateNotification = notificationService.updateNotification(notification);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_UPDATE_SUCCESS, updateNotification));
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteNotification(@Valid @RequestBody DeleteGroupNotificationDTO deleteGroupNotification) {
		notificationService.deleteNotification(deleteGroupNotification);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_DELETE_SUCCESS));
	}

	@GetMapping("/test")
	public ResponseEntity<?> testService() {
		return ResponseEntity.ok(new AuthenticationResponse(null, "Notification server is up and running"));
	}

	@PostMapping("/getUserGroupNotifications")
	public List<GroupNotificationDTO> getUserGroupNotifications(@RequestBody Map<String, String> input) {
		if (input == null || input.isEmpty() || input.get(NotificationConstants.KEY_EMAIL) == null) {
			throw new InvalidRequestException("Email is mandatory");
		}
		return notificationService.getUserGroupNotifications(input.get(NotificationConstants.KEY_EMAIL));
	}
}
