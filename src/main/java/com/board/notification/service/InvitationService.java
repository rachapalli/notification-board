package com.board.notification.service;

import java.util.List;

import com.board.notification.model.Invitation;
import com.board.notification.model.dto.BoardInvitation;
import com.board.notification.model.dto.EmailStatusDTO;
import com.board.notification.model.dto.InvitationDTO;
import com.board.notification.model.dto.InvitationDetailsDTO;

public interface InvitationService {
	public List<Invitation> getAllInvitations();

	public List<EmailStatusDTO> sendBoardInvitations(BoardInvitation boardInvitation);

	List<InvitationDetailsDTO> getUserInvitedGroupDetails(String emailId);

	List<InvitationDTO> getUserCreatedInvitations(String userEmail);
}
