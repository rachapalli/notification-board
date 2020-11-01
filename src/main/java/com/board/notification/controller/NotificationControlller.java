package com.board.notification.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.board.notification.model.GroupNotification;
import com.board.notification.model.Notifications;
import com.board.notification.model.Users;
import com.board.notification.service.NotificationService;
import com.board.notification.service.UserService;

@RestController
@RequestMapping("/notification")
public class NotificationControlller {
	private static final Logger logger = LogManager.getLogger(NotificationControlller.class);

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserService userService;

	@GetMapping("/getNotifications/{groupName}")
	public List<GroupNotification> getNotifications(@PathVariable(name = "groupName") String groupName) {
		return notificationService.getGroupNotification(groupName);
	}

	@PostMapping("/create")
	public GroupNotification saveGroupNotification(@RequestBody GroupNotification groupNotification) {
		return notificationService.saveTextGroupNotification(groupNotification);
	}

	@GetMapping("/test")
	public ResponseEntity<?> testService() {
		return ResponseEntity.ok(new AuthenticationResponse(null, "Notification server is up and running"));
	}

	@PostMapping("/getUserGroupNotifications")
	public List<GroupNotification> getUserGroupNotifications(@RequestBody Map<String, String> input) {
		if (input != null && !input.isEmpty() && input.get("email") != null) {
			Users user = userService.getUserByEmail(input.get("email"));
			if (user == null) {
				throw new InvalidRequestException("User not found");
			}
			return notificationService.getUserGroupNotifications(user.getUserId());
		} else {
			throw new InvalidRequestException("Email is mandatory");
		}
	}
}
