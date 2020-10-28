package com.board.notification.dao;

import org.springframework.data.repository.CrudRepository;

import com.board.notification.model.Invitee;

public interface InviteeRepo extends CrudRepository<Invitee, Integer> {
}
