package com.board.notification.model;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationType {
	FILE("FILE"), TEXT("TEXT");

	String notificationType;

	@Override
	public String toString() {
		return notificationType;
	}

	NotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	@JsonCreator
	public static NotificationType decode(final String code) {
		return Stream.of(NotificationType.values()).filter(targetEnum -> targetEnum.notificationType.equals(code))
				.findFirst().orElse(null);
	}

	@JsonValue
	public String getNotificationType() {
		return notificationType;
	}
}
