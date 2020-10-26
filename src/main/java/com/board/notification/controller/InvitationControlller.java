package com.board.notification.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.model.Invitation;
import com.board.notification.model.Invitations;
import com.board.notification.model.Notifications;
import com.board.notification.service.InvitationService;
import com.board.notification.service.NotificationService;

@RestController
@RequestMapping("/invitation/")
public class InvitationControlller {
	private static final Logger logger = LogManager.getLogger(InvitationControlller.class);

	@Autowired
	private InvitationService invitationService;

	@GetMapping("/")
	public List<Invitation> getAllInvitations() {
		return invitationService.getAllInvitations();
	}

	@PostMapping("/")
	public Invitation sendInvitation(Invitation invitation) {
		return invitationService.saveInvitation(invitation);
	}

}
