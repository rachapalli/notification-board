package com.board.notification.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.model.AuthenticationResponse;
import com.board.notification.model.UserTypeEnum;
import com.board.notification.model.dto.AppUser;
import com.board.notification.model.dto.CommonResponse;
import com.board.notification.model.dto.DeleteGroupNotificationDTO;
import com.board.notification.model.dto.GroupDTO;
import com.board.notification.model.dto.GroupNotificationDTO;
import com.board.notification.model.dto.GroupNotificationSearchDTO;
import com.board.notification.model.dto.NotificationDTO;
import com.board.notification.service.GroupService;
import com.board.notification.service.NotificationService;
import com.board.notification.service.UserService;
import com.board.notification.utils.NotificationConstants;
import com.board.notification.utils.NotificationUtils;

@RestController
@RequestMapping("/notification")
public class NotificationControlller {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/getNotifications/{groupName}")
	public ResponseEntity<?> getNotifications(@PathVariable(name = "groupName") String groupName,
			@RequestHeader(name = "Authorization", required = false) String token) {
		GroupDTO groupDTO = groupService.getGroupByName(groupName);
		if (groupDTO == null) {
			return new ResponseEntity<>(new CommonResponse("Group " + groupName + NotificationConstants.MSG_NOT_FOUND), HttpStatus.NOT_FOUND);
		} 
		
		if (!groupDTO.getIsPublic()) {
			if (token == null || token.isEmpty()) {
				return new ResponseEntity<>(new CommonResponse(NotificationConstants.MSG_LOGIN_RQRD), HttpStatus.UNAUTHORIZED);
			}
		}
		
		UserTypeEnum userRole = null;
		String loginUser = null;
		if (token != null && !token.isEmpty()) {
			loginUser = NotificationUtils.getLoginUser();
			if (loginUser != null && !loginUser.isEmpty()) {
				userRole = userService.getUserRole(loginUser);
			}
		}
		
		boolean skipCheckGroupActiveStatus = (UserTypeEnum.BOARD_OWNER.equals(userRole) || UserTypeEnum.PRODUCT_OWNER.equals(userRole)
				|| UserTypeEnum.ADMIN.equals(userRole));
		
		boolean skipCheckGroupApprovalStatus = (UserTypeEnum.PRODUCT_OWNER.equals(userRole)	|| UserTypeEnum.ADMIN.equals(userRole));
		
		boolean checkUserGroupAccess = !groupDTO.getIsPublic() && (UserTypeEnum.MEMBER.equals(userRole) || UserTypeEnum.BOARD_OWNER.equals(userRole));
		
		if (!skipCheckGroupActiveStatus) {
			if (!groupDTO.getIsActive()) {
				return new ResponseEntity<>(new CommonResponse("Group " + groupName + NotificationConstants.MSG_INACTIVE), HttpStatus.FORBIDDEN);
			}
		}
		
		if (!skipCheckGroupApprovalStatus) {
			if (groupDTO.getIsApproved() == null || !groupDTO.getIsApproved()) {
				return new ResponseEntity<>(new CommonResponse(NotificationConstants.MSG_BOARD_APPROVE), HttpStatus.FORBIDDEN);
			}
		}
		
		if (checkUserGroupAccess) {
			AppUser boardOwner = userService.findUserById(groupDTO.getCreatedBy());
			if (boardOwner == null || !boardOwner.getIsApproved()) {
				return new ResponseEntity<>(new CommonResponse("Board Owner is decliened state"), HttpStatus.FORBIDDEN);
			}
			if (!groupService.checkUserGroupAccess(loginUser, groupDTO.getGroupId())) {
				return new ResponseEntity<>(new CommonResponse(NotificationConstants.MSG_GROUP_ACCESS), HttpStatus.UNAUTHORIZED);
			}
		}
		
		return ResponseEntity.ok(notificationService.getGroupNotification(groupDTO.getGroupId()));
	}

	@PostMapping("/create")
	public ResponseEntity<?> saveGroupNotification(@Valid @RequestBody GroupNotificationDTO groupNotification) {
		GroupNotificationDTO savedGroupNotification = notificationService.saveGroupNotification(groupNotification);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_CREATE_SUCCESS, savedGroupNotification));
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateNotification(@Valid @RequestBody NotificationDTO notification) {
		NotificationDTO updateNotification = notificationService.updateNotification(notification);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_UPDATE_SUCCESS, updateNotification));
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteNotification(@Valid @RequestBody DeleteGroupNotificationDTO deleteGroupNotification) {
		notificationService.deleteNotification(deleteGroupNotification);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_DELETE_SUCCESS));
	}

	@GetMapping("/test")
	public ResponseEntity<?> testService() {
		return ResponseEntity.ok(new AuthenticationResponse(null, "Notification server is up and running"));
	}

	@PostMapping("/getUserGroupNotifications")
	public List<GroupNotificationDTO> getUserGroupNotifications(
			@Valid @RequestBody GroupNotificationSearchDTO groupNotificationSearchDTO) {
		return notificationService.getAllUserGroupNotifications(groupNotificationSearchDTO);
	}
	
	
}
