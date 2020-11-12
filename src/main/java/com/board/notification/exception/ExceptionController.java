package com.board.notification.exception;

import java.sql.SQLException;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundHandler1(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(SecurityError.ALREADY_EXIST.getCode());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ErrorResponse> hibernateHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(SecurityError.OPERATION_FAILED.getCode());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<ErrorResponse> mesasgeException(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(SecurityError.INVALID_EMAIL_FORMAT.getCode());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(FileNotExistException.class)
	public ResponseEntity<ErrorResponse> fileNotFoundException(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(SecurityError.FILE_NOT_FOUND.getCode());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exception(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(SecurityError.INTERNAL_SERVER_ERROR.getCode());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
