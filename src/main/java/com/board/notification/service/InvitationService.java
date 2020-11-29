package com.board.notification.service;

import java.util.List;

import com.board.notification.model.Invitation;
import com.board.notification.model.dto.BoardInvitation;
import com.board.notification.model.dto.EmailStatusDTO;

public interface InvitationService {
	public List<Invitation> getAllInvitations();

	public List<EmailStatusDTO> sendBoardInvitations(BoardInvitation boardInvitation);
}
