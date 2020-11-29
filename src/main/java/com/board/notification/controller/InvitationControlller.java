package com.board.notification.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.model.Invitation;
import com.board.notification.model.dto.BoardInvitation;
import com.board.notification.model.dto.EmailStatusDTO;
import com.board.notification.service.InvitationService;

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

}
