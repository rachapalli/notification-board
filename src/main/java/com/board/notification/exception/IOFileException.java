
package com.board.notification.exception;

public class IOFileException extends Exception {
	/** long Short Description */
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	/**
	 * @return errorMessage of String Type
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param String type set into errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public IOFileException() {
		super();
	}

	public IOFileException(String errorMessage) {
		super("errorMessage");
		this.errorMessage = errorMessage;
	}
}
