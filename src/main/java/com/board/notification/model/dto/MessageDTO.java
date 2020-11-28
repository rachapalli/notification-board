package com.board.notification.model.dto;

import javax.validation.constraints.NotBlank;

public class MessageDTO {

	private Integer messageId;
	@NotBlank(message = "Description can not be Empty/null")
	private String message;

	public MessageDTO(Integer messageId, String message) {
		super();
		this.messageId = messageId;
		this.message = message;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MessageDTO [messageId=" + messageId + ", message=" + message + "]";
	}

}
