package com.board.notification.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.board.notification.model.FileGroupNotification;
import com.board.notification.model.GroupNotifications;
import com.board.notification.model.MessageGroupNotification;
import com.board.notification.model.Notifications;

public interface NotificationsRepo extends CrudRepository<Notifications, Integer> {

	@Query(value = "select n.* from group_notifications gn , notifications n where gn.notification_id = n.notification_id and gn.group_id=:groupId")
	public List<GroupNotifications> getGroupNotifications(@Param("groupId") Integer groupId);

	@Modifying
	@Query(value = "insert into group_notifications(group_id, notification_id, created_by, created_date, is_active) values (:groupId, :notificationId, :createdBy, :createdDate, 1)")
	public void saveGroupNotification(@Param("groupId") Integer groupId,
			@Param("notificationId") Integer notificationId, @Param("createdBy") Integer createdBy, @Param("createdDate") Date createdDate);
	
	@Query(value = "select ns.notification_id, ns.DESCRIPTION, ns.ntype, af.file_id, af.file_key, gn.group_id, gp.group_name, ns.created_by, ns.created_date, ns.updated_by, ns.updated_date, gn.is_active from notifications ns, all_files af, group_notifications gn, groups gp where ns.file_id = af.file_id and gn.notification_id=ns.notification_id and gp.group_id=gn.group_id and gn.group_id=:groupId")
	public List<FileGroupNotification> getFileGroupNotifications(@Param("groupId") Integer groupId);
	
	@Query(value = "select ns.notification_id, ns.DESCRIPTION, ns.ntype, nm.MESSAGE_ID, nm.MESSAGE, gn.group_id, gp.group_name, ns.created_by, ns.created_date, ns.updated_by, ns.updated_date, gn.is_active from notifications ns, NOTIFICATION_MESSAGE nm, group_notifications gn, groups gp where ns.MESSAGE_ID = nm.MESSAGE_ID and gn.notification_id=ns.notification_id and gp.group_id=gn.group_id and gn.group_id=:groupId")
	public List<MessageGroupNotification> getMessageGroupNotifications(@Param("groupId") Integer groupId);
	
	@Query(value = "select ns.notification_id, ns.DESCRIPTION, ns.ntype, af.file_id, af.file_key, gn.group_id, gp.group_name, ns.created_by, ns.created_date, ns.updated_by, ns.updated_date, gn.is_active from notifications ns, all_files af, group_notifications gn, groups gp where ns.file_id = af.file_id and gn.notification_id=ns.notification_id and gp.group_id=gn.group_id and ns.created_by=:userId")
	public List<FileGroupNotification> getUserCreatedFileGroupNotifications(@Param("userId") Integer userId);
	
	@Query(value = "select ns.notification_id, ns.DESCRIPTION, ns.ntype, af.file_id, af.file_key, gn.group_id, gp.group_name, ns.created_by, ns.created_date, ns.updated_by, ns.updated_date, gn.is_active "
			+ "from notifications ns, all_files af, group_notifications gn, groups gp "
			+ "where ns.file_id = af.file_id and gn.notification_id=ns.notification_id and gp.group_id=gn.group_id and ns.created_by=:userId and gp.group_name=:groupName")
	public List<FileGroupNotification> getUserCreatedFileGroupNotificationsByGroup(@Param("userId") Integer userId, @Param("groupName") String groupName);
	
	@Query(value = "select ns.notification_id, ns.DESCRIPTION, ns.ntype, nm.MESSAGE_ID, nm.MESSAGE, gn.group_id, gp.group_name, ns.created_by, ns.created_date, ns.updated_by, ns.updated_date, gn.is_active from notifications ns, NOTIFICATION_MESSAGE nm, group_notifications gn, groups gp where ns.MESSAGE_ID = nm.MESSAGE_ID and gn.notification_id=ns.notification_id and gp.group_id=gn.group_id and ns.created_by=:userId")
	public List<MessageGroupNotification> getUserCreatedMessageGroupNotifications(@Param("userId") Integer userId);
	
	@Query(value = "select ns.notification_id, ns.DESCRIPTION, ns.ntype, nm.MESSAGE_ID, nm.MESSAGE, gn.group_id, gp.group_name, ns.created_by, ns.created_date, ns.updated_by, ns.updated_date, gn.is_active "
			+ "from notifications ns, NOTIFICATION_MESSAGE nm, group_notifications gn, groups gp "
			+ "where ns.MESSAGE_ID = nm.MESSAGE_ID and gn.notification_id=ns.notification_id and gp.group_id=gn.group_id and ns.created_by=:userId and gp.group_name=:groupName")
	public List<MessageGroupNotification> getUserCreatedMessageGroupNotificationsByGroup(@Param("userId") Integer userId, @Param("groupName") String groupName);
	
	@Query(value = "select ns.notification_id, ns.DESCRIPTION, ns.ntype, af.file_id, af.file_key, gn.group_id, g.group_name, ns.created_by, ns.created_date, ns.updated_by, ns.updated_date, gn.is_active from notifications ns, all_files af, group_notifications gn,user_groups ug, groups g where "
			+ "ns.file_id = af.file_id and gn.notification_id=ns.notification_id and g.group_id=gn.group_id and ug.group_id=g.group_id and ug.is_active=1 and g.is_active=1 and ug.user_id=:userId")
	public List<FileGroupNotification> getUserFileGroupNotifications(@Param("userId") Integer userId);
	
	@Query(value = "select ns.notification_id, ns.DESCRIPTION, ns.ntype, af.file_id, af.file_key, gn.group_id, g.group_name, ns.created_by, ns.created_date, ns.updated_by, ns.updated_date, gn.is_active "
			+ "from notifications ns, all_files af, group_notifications gn,user_groups ug, groups g where "
			+ "ns.file_id = af.file_id and gn.notification_id=ns.notification_id and g.group_id=gn.group_id and ug.group_id=g.group_id and ug.is_active=1 and g.is_active=1 and ug.user_id=:userId and g.group_name=:groupName")
	public List<FileGroupNotification> getUserFileGroupNotificationsByGroup(@Param("userId") Integer userId, @Param("groupName") String groupName);
	
	@Query(value = "select ns.notification_id,ns.DESCRIPTION,ns.ntype, nm.MESSAGE_ID, nm.MESSAGE, gn.group_id, g.group_name,ns.created_by, ns.created_date, ns.updated_by, ns.updated_date, gn.is_active from user_groups ug, groups g, notifications ns, NOTIFICATION_MESSAGE nm, group_notifications gn  where "
			+ "ns.MESSAGE_ID = nm.MESSAGE_ID and gn.notification_id=ns.notification_id and g.group_id=gn.group_id and ug.group_id=g.group_id and ug.is_active=1 and g.is_active=1 and ug.user_id=:userId")
	public List<MessageGroupNotification> getUserMessageGroupNotifications(@Param("userId") Integer userId);
	
	@Query(value = "select ns.notification_id,ns.DESCRIPTION,ns.ntype, nm.MESSAGE_ID, nm.MESSAGE, gn.group_id, g.group_name,ns.created_by, ns.created_date, ns.updated_by, ns.updated_date, gn.is_active "
			+ "from user_groups ug, groups g, notifications ns, NOTIFICATION_MESSAGE nm, group_notifications gn  where "
			+ "ns.MESSAGE_ID = nm.MESSAGE_ID and gn.notification_id=ns.notification_id and g.group_id=gn.group_id and ug.group_id=g.group_id and ug.is_active=1 and g.is_active=1 and ug.user_id=:userId and g.group_name=:groupName")
	public List<MessageGroupNotification> getUserMessageGroupNotificationsByGroup(@Param("userId") Integer userId, @Param("groupName") String groupName);
	
	@Modifying
	@Query(value = "update group_notifications SET is_active=0 where group_id=:groupId and notification_id= :notificationId")
	public Integer deleteGroupNotification(@Param("groupId") Integer groupId, @Param("notificationId") Integer notificationId);
	
	@Modifying
	@Query(value = "update group_notifications SET is_active=1 where notification_id= :notificationId")
	public Integer enableGroupNotification(@Param("notificationId") Integer notificationId);
	
}
