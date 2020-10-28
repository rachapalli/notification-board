package com.board.notification.dao;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.Invitation;
import com.board.notification.model.Invitations;

public interface InvitationsRepo extends CrudRepository<Invitations, Integer> {
	@Query(value = "select ite.invitee_name, ite.email,ite.contact_number,its.message, ai.status, its.created_by, its.created_date from all_invitations ai, invitee ite, invitations its "
			+ "where ai.invitation_id=its.invitation_id and ai.invitee_id=ite.invitee_id")
	public List<Invitation> getAllInvitation();

	@Modifying
	@Query(value = "insert into all_invitations (invitation_id, invitee_id, status, created_by, created_date) "
			+ "values (:invitationId, :inviteeId, :status, :createdBy, now())")
	public void saveAllInvitattion(@Param("invitationId") Integer invitationId, @Param("inviteeId") Integer inviteeId,
			@Param("status") String status, @Param("createdBy") Integer createdBy);
}
