package com.board.notification.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.exception.InvalidRequestException;
import com.board.notification.model.Invitation;
import com.board.notification.model.dto.BoardInvitation;
import com.board.notification.model.dto.EmailStatusDTO;
import com.board.notification.model.dto.InvitationDTO;
import com.board.notification.service.InvitationService;
import com.board.notification.utils.NotificationConstants;
import com.board.notification.utils.NotificationUtils;

@RestController
@RequestMapping("/invitation")
public class InvitationControlller {

	@Autowired
	private InvitationService invitationService;

	@GetMapping("/list")
	public List<Invitation> getAllInvitations() {
		return invitationService.getAllInvitations();
	}

	@PostMapping("/sendBoardInvitation")
	public List<EmailStatusDTO> sendInvitation(@Valid @RequestBody BoardInvitation boardInvitation) {
		return invitationService.sendBoardInvitations(boardInvitation);
	}
	
	@PostMapping("/user-invitations")
	public List<InvitationDTO> getUserInvitations(@RequestBody Map<String, String> userInput) {
		if (userInput == null || userInput.isEmpty() || userInput.get(NotificationConstants.KEY_EMAIL) == null) {
			throw new InvalidRequestException(
					NotificationConstants.KEY_EMAIL + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		if (!NotificationUtils.isValidEmail(userInput.get(NotificationConstants.KEY_EMAIL))) {
			throw new InvalidRequestException(NotificationConstants.MSG_INVALID_EMAIL);
		}
		return invitationService.getUserCreatedInvitations(userInput.get(NotificationConstants.KEY_EMAIL));
	}

}
