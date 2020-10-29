package com.board.notification.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.model.AuthenticationResponse;
import com.board.notification.model.Notifications;
import com.board.notification.service.NotificationService;

@RestController
public class NotificationControlller {
	private static final Logger logger = LogManager.getLogger(NotificationControlller.class);

	@Autowired
	private NotificationService notificationService;

	@RequestMapping("/getNotifications/{groupName}")
	public List<Notifications> getNotifications(@PathVariable(name = "groupName") String groupName) {
		return notificationService.getGroupNotification(groupName);
	}

	@RequestMapping("/saveGroupNotification/{groupName}")
	public Notifications saveGroupNotification(@PathVariable(name = "groupName") String groupName,
			@RequestBody Notifications notification) {
		return notificationService.saveTextGroupNotification(groupName, notification);
	}

	@GetMapping("/test")
	public ResponseEntity<?> testService() {
		return ResponseEntity.ok(new AuthenticationResponse(null, "Notification server is up and running"));
	}

}
