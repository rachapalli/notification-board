package com.board.notification.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.InvitationsRepo;
import com.board.notification.dao.InviteeRepo;
import com.board.notification.model.Invitation;
import com.board.notification.model.Invitations;
import com.board.notification.model.Invitee;
import com.board.notification.service.InvitationService;

@Service
public class InvitationServiceImpl implements InvitationService {

	@Autowired
	public InvitationsRepo invitationsRepo;

	@Autowired
	public InviteeRepo inviteeRepo;

	@Override
	public List<Invitation> getAllInvitations() {
		return invitationsRepo.getAllInvitation();
	}

	@Transactional
	@Override
	public Invitation saveInvitation(Invitation invitation) {
		Invitations invitations = new Invitations();
		invitations.setMessage(invitation.getMessage());
		invitations.setCreatedDate(invitation.getCreatedDate());
		Invitations savedInvitations = invitationsRepo.save(invitations);

		Invitee invitee = new Invitee();
		invitee.setInviteeName(invitation.getInviteeName());
		invitee.setEmail(invitation.getEmail());
		invitee.setContactNumber(invitation.getContactNumber());
		invitee.setCreatedBy(invitation.getCreatedBy());
		invitee.setCreatedDate(invitation.getCreatedDate());
		Invitee savdInvitee = inviteeRepo.save(invitee);

		invitationsRepo.saveAllInvitattion(savedInvitations.getInvitationId(), savdInvitee.getInviteeId(),
				invitation.getStatus(), invitation.getCreatedBy());

		return invitation;
	}

}
