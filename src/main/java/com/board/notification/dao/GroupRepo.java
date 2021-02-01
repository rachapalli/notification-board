package com.board.notification.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.Groups;
import com.board.notification.model.dto.GroupDTO;
import com.board.notification.model.dto.GroupUsersDTO;

public interface GroupRepo extends CrudRepository<Groups, Integer> {
	public Groups findByGroupName(String groupName);

	@Modifying
	@Query(value = "IF NOT EXISTS (select is_active from user_groups where group_id=:groupId and user_id=:userId) INSERT INTO user_groups(user_id, group_id, created_by,created_date,is_active) "
			+ "values (:userId, :groupId, :createdBy, :createdDate, :isActive)")
	public void addGroupUser(@Param("userId") Integer userId, @Param("groupId") Integer groupId,
			@Param("createdBy") Integer createdBy, @Param("createdDate") Date createdDate, @Param("isActive") Integer isActive);

	@Query(value = "select g.* from user_groups ug, users u, groups g where ug.user_id=u.user_id and ug.group_id=g.group_id and g.is_active=1 and u.email= :emailId")
	public List<Groups> getAllUserGroupsByEmail(@Param("emailId") String emailId);

	@Query(value = "select g.* from user_groups ug, users u, groups g where ug.user_id=u.user_id and ug.group_id=g.group_id and u.email= :emailId")
	public List<Groups> getBoardOwnerGroups(@Param("emailId") String emailId);
	
	@Query(value = "select u.email as user_email, u.user_name, g.group_name, ug.is_active, g.is_public from user_groups ug, users u, groups g where u.user_id = ug.user_id and ug.group_id = g.group_id and ug.user_id != g.created_by and g.created_by=:userId")
	public List<GroupUsersDTO> getGroupUsers(Integer userId);
	
	@Query(value = "select count(*) from user_groups ug, users u, groups g where ug.group_id=g.group_id and ug.user_id = u.user_id and ug.is_active=1 and u.email = :userEmail and g.group_id=:groupId")
	public Integer checkUserGroupAccess(@Param("userEmail") String userEmail, @Param("groupId") Integer groupId);
	
	@Query(value = "select g.group_id, g.group_name, g.is_public, g.created_by, u.user_name as created_user_name, g.created_date, g.is_active, g.is_approved from groups g, users u where g.created_by = u.user_id order by g.created_date desc")
	public List<GroupDTO> getAllGroups();
}
