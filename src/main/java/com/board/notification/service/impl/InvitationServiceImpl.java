package com.board.notification.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.notification.dao.InvitationsRepo;
import com.board.notification.dao.InviteeRepo;
import com.board.notification.dao.UserRepo;
import com.board.notification.exception.InvalidRequestException;
import com.board.notification.model.ActiveStatusEnum;
import com.board.notification.model.Invitation;
import com.board.notification.model.Invitations;
import com.board.notification.model.Invitee;
import com.board.notification.model.Users;
import com.board.notification.model.dto.BoardInvitation;
import com.board.notification.model.dto.EmailDTO;
import com.board.notification.model.dto.EmailStatusDTO;
import com.board.notification.model.dto.GroupDTO;
import com.board.notification.model.dto.InvitationDTO;
import com.board.notification.model.dto.InvitationDetailsDTO;
import com.board.notification.service.EmailService;
import com.board.notification.service.GroupService;
import com.board.notification.service.InvitationService;
import com.board.notification.utils.NotificationConstants;
import com.board.notification.utils.NotificationUtils;

@Service
public class InvitationServiceImpl implements InvitationService {

	@Autowired
	private InvitationsRepo invitationsRepo;

	@Autowired
	private InviteeRepo inviteeRepo;

	@Autowired
	private GroupService groupService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepo userRepo;

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
			GroupDTO groupDTO = groupService.findByGroupName(boardInvitation.getGroupName());
			invitationsRepo.saveAllInvitation(savedInvitation.getInvitationId(), savdInvitee.getInviteeId(),
					emailStatusDTO.getStatus().toString(), emailStatusDTO.getMessage(), boardInvitation.getCreatedBy(),
					NotificationUtils.getUKTime(), groupDTO.getGroupId());
			Users invitedUser = userRepo.findByEmail(emailStatusDTO.getEmail());
			if (invitedUser != null) {
				groupService.addGroupUser(invitedUser.getUserId(), groupDTO.getGroupId(),
						boardInvitation.getCreatedBy(), ActiveStatusEnum.ACTIVE.statusFlag());
			}
		}
		status = true;
		return status;
	}
	
	@Override
	public List<EmailStatusDTO> sendBoardInvitations(BoardInvitation boardInvitation) {
		Set<String> emailIdList = boardInvitation.getEmailIdList();
		List<EmailStatusDTO> emailStatusDTOs = new ArrayList<>(emailIdList.size());
		for (String emailId : emailIdList) {
			emailStatusDTOs.add(emailService.sendHtmlEmail(
					new EmailDTO(emailId, boardInvitation.getEmailSubject(), boardInvitation.getEmailBody())));
		}
		persistInvitation(boardInvitation, emailStatusDTOs);
		return emailStatusDTOs;
	}

	@Override
	public List<InvitationDetailsDTO> getUserInvitedGroupDetails(String emailId) {
		if (emailId == null || emailId.isEmpty()) {
			throw new InvalidRequestException("emailId " + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		return invitationsRepo.getAllInvitedBoardDetails(emailId);
	}
	
	@Override
	public Collection<InvitationDTO> getUserCreatedInvitations(String userEmail) {
		List<InvitationDTO> userCreatedInvitations = invitationsRepo.getUserCreatedInvitations(userEmail);
		if (!userCreatedInvitations.isEmpty()) {
			Map<Integer, InvitationDTO> mapInvitations = new HashMap<>();
			InvitationDTO tempInvitation = null;
			for (InvitationDTO invitationDTO : userCreatedInvitations) {
				tempInvitation = mapInvitations.get(invitationDTO.getInvitationId());
				if (tempInvitation == null) {
					mapInvitations.put(invitationDTO.getInvitationId(), invitationDTO);
				} else {
					tempInvitation.setInviteeEmail(tempInvitation.getInviteeEmail() + "," + invitationDTO.getInviteeEmail());
				}
			} 
			return mapInvitations.values();
		}
		return userCreatedInvitations;
	}
	
	
}
