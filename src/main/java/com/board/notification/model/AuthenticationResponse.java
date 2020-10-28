package com.board.notification.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8722377099060483240L;
	private final String token;
	private String message;

	public AuthenticationResponse(String token, String message) {
		super();
		this.token = token;
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
