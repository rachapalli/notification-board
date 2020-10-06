package com.board.notification.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.notification.dao.NotificationDao;
import com.board.notification.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	private static final Logger logger = LogManager.getLogger(NotificationServiceImpl.class);
	@Autowired
	private NotificationDao notificationDao;

	@Override
	public void getNotifications() {
		logger.info("Testing Service . . . ");
		notificationDao.getNotifications();
	}

}
