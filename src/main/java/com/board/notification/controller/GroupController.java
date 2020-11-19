package com.board.notification.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.exception.AlreadyExistsException;
import com.board.notification.model.Groups;
import com.board.notification.service.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Groups createGroup(@Valid @RequestBody Groups groups) throws AlreadyExistsException {
		return groupService.createOrUpdateGroup(groups);
	}

	@GetMapping("/getGroups")
	public Iterable<Groups> getAllGroups() {
		return groupService.getAllGroups();
	}

	@PostMapping("/getOwnerGroups")
	public List<Groups> getUserGroups(@RequestBody Map<String, String> userInput) {
		if (userInput != null && !userInput.isEmpty()) {
			String email = userInput.get("email");
			if (email != null && !email.isEmpty()) {
				return groupService.getUserGroups(email);
			}
		}
		return Collections.emptyList();
	}

}
