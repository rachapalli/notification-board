package com.board.notification.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.Invitation;
import com.board.notification.model.Invitations;
import com.board.notification.model.dto.InvitationDTO;
import com.board.notification.model.dto.InvitationDetailsDTO;

public interface InvitationsRepo extends CrudRepository<Invitations, Integer> {
	@Query(value = "select ite.invitee_name, ite.email,its.subject, its.message, ai.status, ai.status_msg, ai.created_by, ai.created_date, u.user_name from all_invitations ai, invitee ite, invitations its, users u where ai.invitation_id=its.invitation_id and ai.invitee_id=ite.invitee_id and u.user_id=ai.created_by")
	public List<Invitation> getAllInvitation();
	
	@Query(value = "select ite.email as invitee_email ,its.subject, its.message, ai.status, ai.status_msg, ai.created_date, g.group_name from all_invitations ai, invitee ite, invitations its, users u, groups g where ai.invitation_id=its.invitation_id and ai.invitee_id=ite.invitee_id and u.user_id=ai.created_by and g.group_id= ai.group_id and u.email=:email")
	public List<InvitationDTO> getUserCreatedInvitations(@Param("email") String email);

	@Modifying
	@Query(value = "insert into all_invitations(invitation_id, invitee_id, status, status_msg, created_by, created_date, group_id) "
			+ "values (:invitationId, :inviteeId, :status, :statusMsg, :createdBy, :createdDate, :groupId)")
	public void saveAllInvitation(@Param("invitationId") Integer invitationId, @Param("inviteeId") Integer inviteeId,
			@Param("status") String status, @Param("statusMsg") String statusMsg, @Param("createdBy") Integer createdBy,
			@Param("createdDate") Date createdDate, @Param("groupId") Integer groupId);

	@Query(value = "select u.email as board_owner_email, ai.group_id as invited_group_id, g.group_name as invited_group_name from invitee i, all_invitations ai, groups g, users u where i.invitee_id = ai.invitee_id and g.group_id = ai.group_id and u.user_id = g.created_by and i.email = :emailId")
	public List<InvitationDetailsDTO> getAllInvitedBoardDetails(String emailId);
}
