package com.board.notification.utils;

import java.util.Base64;

public class NotificationUtils {

	public static String encodeString(String input) {
		return Base64.getEncoder().encodeToString(input.getBytes());
	}

	public static String decodeString(String input) {
		return new String(Base64.getDecoder().decode(input));
	}
}
