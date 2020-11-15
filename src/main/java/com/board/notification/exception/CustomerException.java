
package com.board.notification.exception;

public class CustomerException extends Exception {
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

	public CustomerException() {
		super();
	}

	/**
	 * @param errorMessage
	 * @description
	 */
	public CustomerException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

}
