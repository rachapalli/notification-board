package com.board.notification.dao;

import org.springframework.data.repository.CrudRepository;

import com.board.notification.model.NotificationMessage;

public interface NotificationMessageRepo extends CrudRepository<NotificationMessage, Integer> {

}
