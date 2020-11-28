package com.board.notification.model.dto;

import java.io.Serializable;

public class CommonResponse implements Serializable {

	private static final long serialVersionUID = -8722377099060483240L;
	private String message;
	private Object results;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResults() {
		return results;
	}

	public void setResults(Object results) {
		this.results = results;
	}

	public CommonResponse(String message, Object results) {
		super();
		this.message = message;
		this.results = results;
	}
	
	public CommonResponse(String message) {
		super();
		this.message = message;
	}

}
