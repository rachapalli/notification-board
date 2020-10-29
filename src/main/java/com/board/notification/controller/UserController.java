package com.board.notification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.model.AppUser;
import com.board.notification.model.Users;
import com.board.notification.service.UserService;
import com.board.notification.utils.NotificationConstants;

@RestController
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public AppUser createAppUser(@RequestBody AppUser appUser) {
		return userService.createOrUpdateUser(appUser);
	}

	@GetMapping("/{email}")
	public Users getUserByEmail(@PathVariable(name = "email") String email) {
		return userService.getUserByEmail(email);
	}

	@GetMapping("/")
	public List<AppUser> getAllAppUsers() {
		return userService.getAllAppUsers();
	}

	@GetMapping("/activate")
	public String activateUser(@RequestParam(name = "key") String key) {
		boolean isUserActiavted = userService.activateUser(key);
		return isUserActiavted ? NotificationConstants.USER_ACTIVATED_SUCEES
				: NotificationConstants.USER_ACTIVATED_FAIL;
	}

}
