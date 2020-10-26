package com.board.notification.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.model.AuthenticationRequest;
import com.board.notification.model.AuthenticationResponse;
import com.board.notification.service.NotificationService;
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

	@RequestMapping("/getNotifications")
	public void getNotifications() {
		logger.info("Testing Rest Service . . .");
		notificationService.getNotifications();
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

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

}
