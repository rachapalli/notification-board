package com.board.notification.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.GroupRepo;
import com.board.notification.dao.InvitationsRepo;
import com.board.notification.dao.InviteeRepo;
import com.board.notification.model.Invitation;
import com.board.notification.model.Invitations;
import com.board.notification.model.Invitee;
import com.board.notification.model.dto.BoardInvitation;
import com.board.notification.model.dto.EmailDTO;
import com.board.notification.model.dto.EmailStatusDTO;
import com.board.notification.service.EmailService;
import com.board.notification.service.InvitationService;
import com.board.notification.utils.NotificationUtils;

@Service
public class InvitationServiceImpl implements InvitationService {

	@Autowired
	public InvitationsRepo invitationsRepo;

	@Autowired
	public InviteeRepo inviteeRepo;

	@Autowired
	public GroupRepo groupRepo;

	@Autowired
	public EmailService emailService;

	@Override
	public List<Invitation> getAllInvitations() {
		return invitationsRepo.getAllInvitation();
	}

	@Transactional
	public boolean persistInvitation(BoardInvitation boardInvitation, List<EmailStatusDTO> emailStatusDTOs) {
		boolean status = false;
		Invitations savedInvitation = invitationsRepo
				.save(new Invitations(boardInvitation.getEmailSubject(), boardInvitation.getEmailBody()));
		Invitee savdInvitee = null;
		for (EmailStatusDTO emailStatusDTO : emailStatusDTOs) {
			savdInvitee = inviteeRepo.save(new Invitee(emailStatusDTO.getEmail()));
			invitationsRepo.saveAllInvitation(savedInvitation.getInvitationId(), savdInvitee.getInviteeId(),
					emailStatusDTO.getStatus().toString(), emailStatusDTO.getMessage(), boardInvitation.getCreatedBy(),
					NotificationUtils.getUKTime());
		}
		status = true;
		return status;
	}
	
	@Override
	public List<EmailStatusDTO> sendBoardInvitations(BoardInvitation boardInvitation) {
		Set<String> emailIdList = boardInvitation.getEmailIdList();
		List<EmailStatusDTO> emailStatusDTOs = new ArrayList<>(emailIdList.size());
		for (String emailId : emailIdList) {
			emailStatusDTOs.add(emailService.sendEmail(
					new EmailDTO(emailId, boardInvitation.getEmailSubject(), boardInvitation.getEmailBody())));
		}
		persistInvitation(boardInvitation, emailStatusDTOs);
		return emailStatusDTOs;
	}

}
