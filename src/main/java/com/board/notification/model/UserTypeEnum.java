package com.board.notification.model;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserTypeEnum {
	ADMIN("Admin"), BOARD_OWNER("Board Owner"), MEMBER("Member");

	private String userType;

	@Override
	public String toString() {
		return userType;
	}

	UserTypeEnum(String userType) {
		this.userType = userType;
	}

	@JsonCreator
	public static UserTypeEnum decode(final String code) {
		return Stream.of(UserTypeEnum.values()).filter(targetEnum -> targetEnum.userType.equals(code)).findFirst()
				.orElse(null);
	}

	@JsonValue
	public String getUserType() {
		return userType;
	}
}
