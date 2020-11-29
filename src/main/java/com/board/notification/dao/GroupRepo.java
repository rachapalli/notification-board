package com.board.notification.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.Groups;

public interface GroupRepo extends CrudRepository<Groups, Integer> {
	public Groups findByGroupName(String groupName);

	@Modifying
	@Query(value = "INSERT INTO user_groups(user_id, group_id, created_by,created_date,is_active) "
			+ "values (:userId, :groupId, :createdBy, :createdDate, :isActive)")
	public void addGroupUser(@Param("userId") Integer userId, @Param("groupId") Integer groupId,
			@Param("createdBy") Integer createdBy, @Param("createdDate") Date createdDate, @Param("isActive") Integer isActive);

	@Query(value = "select g.* from user_groups ug, users u, groups g where ug.user_id=u.user_id and ug.group_id=g.group_id and u.email= :emailId")
	public List<Groups> getAllUserGroupsByEmail(@Param("emailId") String emailId);

	@Query(value = "select g.* from user_groups ug, users u, groups g where ug.user_id=u.user_id and ug.group_id=g.group_id and u.email= :emailId")
	public List<Groups> getBoardOwnerGroups(@Param("emailId") String emailId);
}
