package com.board.notification.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.Invitation;
import com.board.notification.model.Invitations;

public interface InvitationsRepo extends CrudRepository<Invitations, Integer> {
	@Query(value = "select ite.invitee_name, ite.email,its.subject, its.message, ai.status, ai.status_msg, ai.created_by, ai.created_date, u.user_name from all_invitations ai, invitee ite, invitations its, users u where ai.invitation_id=its.invitation_id and ai.invitee_id=ite.invitee_id and u.user_id=ai.created_by")
	public List<Invitation> getAllInvitation();

	@Modifying
	@Query(value = "insert into all_invitations(invitation_id, invitee_id, status, status_msg, created_by, created_date) "
			+ "values (:invitationId, :inviteeId, :status, :statusMsg, :createdBy, :createdDate)")
	public void saveAllInvitation(@Param("invitationId") Integer invitationId, @Param("inviteeId") Integer inviteeId,
			@Param("status") String status, @Param("statusMsg") String statusMsg, @Param("createdBy") Integer createdBy,
			@Param("createdDate") Date createdDate);
}
