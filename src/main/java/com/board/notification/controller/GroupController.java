package com.board.notification.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.exception.InvalidRequestException;
import com.board.notification.model.dto.CommonResponse;
import com.board.notification.model.dto.GroupDTO;
import com.board.notification.model.dto.GroupUsersDTO;
import com.board.notification.service.GroupService;
import com.board.notification.utils.NotificationConstants;
import com.board.notification.utils.NotificationUtils;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@PostMapping("/create")
	public ResponseEntity<?> createGroup(@Valid @RequestBody GroupDTO group) {
		GroupDTO createdGroup = groupService.createGroup(group);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_CREATE_SUCCESS, createdGroup));
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateGroup(@Valid @RequestBody GroupDTO group) {
		GroupDTO updatedGroup = groupService.updateGroup(group);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_UPDATE_SUCCESS, updatedGroup));
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteGroup(@RequestBody GroupDTO group) {
		groupService.deleteGroup(group);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_DELETE_SUCCESS));
	}

	@GetMapping("/getGroups")
	public List<GroupDTO> getAllGroups() {
		return groupService.getAllGroups();
	}

	@PostMapping("/getOwnerGroups")
	public List<GroupDTO> getUserGroups(@RequestBody Map<String, String> userInput) {
		if (userInput == null || userInput.isEmpty() || userInput.get(NotificationConstants.KEY_EMAIL) == null
				|| userInput.get(NotificationConstants.KEY_EMAIL).isEmpty()) {
			throw new InvalidRequestException(
					NotificationConstants.KEY_EMAIL + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		return groupService.getOwnerGroups(userInput.get(NotificationConstants.KEY_EMAIL));
	}
	
	@PostMapping("/getOwnerGroupUsers")
	public List<GroupUsersDTO> getGroupUsers(@RequestBody Map<String, String> userInput) {
		if (userInput == null || userInput.isEmpty()) {
			throw new InvalidRequestException(
					NotificationConstants.KEY_EMAIL + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		
		if (!NotificationUtils.isValidEmail(userInput.get(NotificationConstants.KEY_EMAIL))) {
			throw new InvalidRequestException(NotificationConstants.MSG_INVALID_EMAIL);
		}
		return groupService.getGroupUsers(userInput.get(NotificationConstants.KEY_EMAIL));
	}
	
	@PostMapping("/approve")
	public ResponseEntity<CommonResponse> approveGroup(@RequestBody GroupDTO group) {
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_APPROVAL_STATUS, groupService.approveGroup(group)));
	}

}
