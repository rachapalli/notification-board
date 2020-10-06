package com.board.notification.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.board.notification.dao.NotificationDao;

@Repository
public class NotificationDaoImpl implements NotificationDao {
	private static final Logger logger = LogManager.getLogger(NotificationDaoImpl.class);

	@Override
	public void getNotifications() {
		logger.info("Testing Dao Impl ");

	}

}
