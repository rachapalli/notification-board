package com.board.notification.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.service.NotificationService;

@RestController
public class NotificationControlller {
	private static final Logger logger = LogManager.getLogger(NotificationControlller.class);

	@Autowired
	private NotificationService notificationService;

	@RequestMapping("/getNotifications")
	public void getNotifications() {
		logger.info("Testing Rest Service . . .");
		notificationService.getNotifications();
	}

}
