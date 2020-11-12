
package com.board.notification.exception;

public class FileNotExistException extends Exception {
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

	public FileNotExistException() {
		super();
	}

	public FileNotExistException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
}
