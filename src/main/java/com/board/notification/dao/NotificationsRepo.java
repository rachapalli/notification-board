package com.board.notification.dao;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.GroupNotification;
import com.board.notification.model.Notifications;

public interface NotificationsRepo extends CrudRepository<Notifications, Long> {

	@Query(value = "select n.* from group_notifications gn , notifications n where gn.notification_id = n.notification_id and gn.group_id=:groupId")
	public List<Notifications> getGroupNotifications(@Param("groupId") Integer groupId);

	@Modifying
	@Query(value = "insert into group_notifications(group_id, notification_id, created_by, created_date, is_active) values (:groupId, :notificationId, :createdBy, now(), 1);")
	public void saveGroupNotification(@Param("groupId") Integer groupId,
			@Param("notificationId") Integer notificationId, @Param("createdBy") Integer createdBy);
	
	
	@Query(value = "select g.group_id, g.group_name, n.* from group_notifications gn, notifications n, groups g "
			+ "where gn.notification_id = n.notification_id and gn.group_id = g.group_id and gn.created_by=:userId")
	public List<GroupNotification> getGroupUserNotifications(@Param("userId") Integer userId);
}
