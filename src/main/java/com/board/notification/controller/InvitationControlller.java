package com.board.notification.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.model.Invitation;
import com.board.notification.model.StatusEnum;
import com.board.notification.service.EmailService;
import com.board.notification.service.InvitationService;

@RestController
@RequestMapping("/invitation")
public class InvitationControlller {
	private static final Logger logger = LogManager.getLogger(InvitationControlller.class);

	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private EmailService emailService;

	@GetMapping("/list")
	public List<Invitation> getAllInvitations() {
		return invitationService.getAllInvitations();
	}

	@PostMapping("/send")
	public Invitation sendInvitation(@RequestBody Invitation invitation) {
		StatusEnum emailStatus = emailService.sendEmail(invitation);
		invitation.setStatus(emailStatus.status());
		return invitationService.saveInvitation(invitation);
	}

}
