package com.board.notification.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.exception.InvalidRequestException;
import com.board.notification.model.AuthenticationRequest;
import com.board.notification.model.AuthenticationResponse;
import com.board.notification.model.GroupNotification;
import com.board.notification.model.Notifications;
import com.board.notification.model.Users;
import com.board.notification.service.NotificationService;
import com.board.notification.service.UserService;
import com.board.notification.service.impl.NotificationUserDetailsService;
import com.board.notification.utils.TokenUtils;

@RestController
public class NotificationControlller {
	private static final Logger logger = LogManager.getLogger(NotificationControlller.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtils jwtTokenUtil;

	@Autowired
	private NotificationUserDetailsService userDetailsService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserService userService;

	@GetMapping("/getNotifications/{groupName}")
	public List<Notifications> getNotifications(@PathVariable(name = "groupName") String groupName) {
		return notificationService.getGroupNotification(groupName);
	}

	@PostMapping("/saveGroupNotification")
	public GroupNotification saveGroupNotification(@RequestBody GroupNotification groupNotification) {
		return notificationService.saveTextGroupNotification(groupNotification);
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt, "Login success"));
	}

	@PostMapping("/getUserGroupNotifications")
	public List<GroupNotification> getUserGroupNotifications(@RequestBody Map<String, String> input) {
		if (input != null && !input.isEmpty() && input.get("email") != null) {
			Users user = userService.getUserByEmail(input.get("email"));
			return notificationService.getUserGroupNotifications(user.getUserId());
		} else {
			throw new InvalidRequestException("Email is mandatory");
		}
	}
}
