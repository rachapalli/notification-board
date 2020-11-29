package com.board.notification.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.board.notification.exception.InvalidRequestException;
import com.board.notification.model.AuthenticationRequest;
import com.board.notification.model.AuthenticationResponse;
import com.board.notification.model.dto.AppUser;
import com.board.notification.model.dto.CommonResponse;
import com.board.notification.service.UserService;
import com.board.notification.service.impl.NotificationUserDetailsService;
import com.board.notification.utils.NotificationConstants;
import com.board.notification.utils.TokenUtils;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtils jwtTokenUtil;

	@Autowired
	private NotificationUserDetailsService userDetailsService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CommonResponse> createUser(@Valid @RequestBody AppUser appUser) {
		AppUser createdAppUser = userService.createUser(appUser);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_CREATE_SUCCESS, createdAppUser));
	}

	@PostMapping("/update")
	public ResponseEntity<CommonResponse> updateUser(@Valid @RequestBody AppUser appUser) {
		AppUser updatedUser = userService.updateUser(appUser);
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_UPDATE_SUCCESS, updatedUser));
	}

	@PostMapping("/delete")
	public ResponseEntity<CommonResponse> createAppUser(@RequestBody AppUser appUser) {
		if (appUser == null || appUser.getEmail() == null || appUser.getEmail().isEmpty()) {
			throw new InvalidRequestException("email " + NotificationConstants.MSG_NOT_NULL_EMPTY);
		}
		return ResponseEntity.ok(new CommonResponse(NotificationConstants.MSG_DELETE_SUCCESS,
				userService.deleteUser(appUser.getEmail())));
	}

	@GetMapping("/{email}")
	public AppUser getUserByEmail(@PathVariable(name = "email") String email) {
		return userService.getUserByEmail(email);
	}

	@PostMapping("/findUser")
	public AppUser getUserByEmail(Map<String, String> requestMap) {
		if (requestMap == null || requestMap.isEmpty() || requestMap.get(NotificationConstants.KEY_EMAIL) == null) {
			throw new InvalidRequestException(NotificationConstants.REQUIRED_MSG + NotificationConstants.KEY_EMAIL);
		}
		return userService.getUserByEmail(requestMap.get(NotificationConstants.KEY_EMAIL));
	}

	@GetMapping("/getUsers")
	public List<AppUser> getAllAppUsers() {
		return userService.getAllAppUsers();
	}

	@GetMapping("/activate")
	public String activateUser(@RequestParam(name = "key") String key) {
		boolean isUserActiavted = userService.activateUser(key);
		return isUserActiavted ? NotificationConstants.USER_ACTIVATED_SUCEES
				: NotificationConstants.USER_ACTIVATED_FAIL;
	}

	@GetMapping("/userTypes")
	public List<String> getAllActiveUserTypes() {
		return userService.getAllActiveUserTypes();
	}

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt, "Login success", userDetails));
	}

}
