package com.board.notification.exception;

public class DataAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataAccessException(final String message) {
		super(message);
	}

	public DataAccessException() {
		super();
	}

	public DataAccessException(final String message, final Exception exception) {
		super(message, exception);
	}
}
