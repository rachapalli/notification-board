package com.board.notification.dao;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.Groups;

public interface GroupRepo extends CrudRepository<Groups, Integer> {
	public Groups findByGroupName(String groupName);

	@Modifying
	@Query(value = "INSERT INTO user_groups(user_id, group_id, created_by,created_date,is_active) "
			+ "values (:userId, :groupId, :createdBy, now(), Integer isActive)")
	public void addGroupUser(@Param("userId") Integer userId, @Param("groupId") Integer groupId,
			@Param("createdBy") Integer createdBy, @Param("isActive") Integer isActive);

}
