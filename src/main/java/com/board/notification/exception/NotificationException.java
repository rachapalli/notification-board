package com.board.notification.exception;

public class NotificationException extends RuntimeException {
	private static final long serialVersionUID = -4608854237495356L;

	public NotificationException() {
		super();
	}

	public NotificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotificationException(String message) {
		super(message);
	}

	public NotificationException(Throwable cause) {
		super(cause);
	}
}
