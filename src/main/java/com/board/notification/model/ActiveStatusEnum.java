package com.board.notification.model;

public enum ActiveStatusEnum {
	ACTIVE(1, true), INACTIVE(0, false);

	private Integer status;
	private boolean statusFlag;

	public Integer status() {
		return status;
	}

	public boolean statusFlag() {
		return statusFlag;
	}

	ActiveStatusEnum(Integer status, boolean statusFlag) {
		this.status = status;
		this.statusFlag = statusFlag;
	}
}
