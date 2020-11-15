package com.board.notification.exception;

import org.springframework.http.HttpStatus;

public enum SecurityError {
	NO_DATA(HttpStatus.OK, 404100, "No Data."),
	NO_SIGN_IN_DATA(HttpStatus.UNAUTHORIZED, 404101, "No token was provided"),
	WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, 404102, "Wrong login or password"),
	WRONG_LOGIN(HttpStatus.NOT_FOUND, 404103, "User with given login was not found"),
	USER_WAS_BLOCKED(HttpStatus.UNAUTHORIZED, 404105, "This user was blocked"),
	EMAIL_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, 404106, "Please verify your email"),
	PHONE_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, 404107, "Please verify your phone number"),
	AWAITING_APPROVAL(HttpStatus.UNAUTHORIZED, 404108, "Waiting for approval"),
	TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, 404110, "Token was not found"),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 404111, "Token has expired"),
	WRONG_EMAIL_LOGIN(HttpStatus.NOT_FOUND, 404113, "Please login using you Email/Phone Number"),
	FILE_NOT_FOUND(HttpStatus.NOT_FOUND, 4041114, "File not found."),

	LOGIN_SUCCESS(HttpStatus.OK, 200001, "User is successfully logged in."),
	INVALID_DATA(HttpStatus.BAD_REQUEST, 400203, "Invalid data was provided"),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, 400014,
			"Password should contains from 8 to 20 symbols with at least 1 capital letter and at least 1 numeric symbol."),
	INVALID_NEW_PASSWORD(HttpStatus.BAD_REQUEST, 400001, "New password is invalid"),
	INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, 400013, "Invalid email format"),

	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500100, "Please contact administrator . . ."),
	LOGOUT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500101, "Something went wrong in logging out..."),
	DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500102, "Some error in Database Side..."),
	OPERATION_FAILED(HttpStatus.BAD_REQUEST, 400203, "The current operation is failed."),
	OPERATION_SUCCESS(HttpStatus.OK, 400204, "The current operation is successfully completed."),
	ALREADY_EXIST(HttpStatus.CONFLICT, 409107, "Already Exist.");

	private HttpStatus status;

	private int code;

	private String description;

	SecurityError(HttpStatus status, int code, String description) {
		this.status = status;
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public HttpStatus getHttpStatus() {
		return status;
	}
}
