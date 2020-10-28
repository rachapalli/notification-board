package com.board.notification.model;

public enum StatusEnum {
	SUCCESS("SUCCESS"), FAIL("FAIL");

	private String status;

	StatusEnum(String status) {
		this.status = status;
	}

	public String status() {
		return status;
	}
}
