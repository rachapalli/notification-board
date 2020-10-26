package com.board.notification.service;

import java.util.List;

import com.board.notification.model.Invitation;

public interface InvitationService {
	public List<Invitation> getAllInvitations();

	public Invitation saveInvitation(Invitation invitation);
}
