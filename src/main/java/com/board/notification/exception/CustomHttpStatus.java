
package com.board.notification.exception;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class CustomHttpStatus extends HttpStatusCodeException {

	/** long Short Description */
	private static final long serialVersionUID = 6173897063582151105L;

	/**
	 * @param statusCode
	 * @param statusText
	 * @param responseBody
	 * @param responseCharset
	 * @description
	 */
	public CustomHttpStatus(HttpStatus statusCode, String statusText, byte[] responseBody, Charset responseCharset) {
		super(statusCode, statusText, responseBody, responseCharset);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param statusCode
	 * @param statusText
	 * @param responseHeaders
	 * @param responseBody
	 * @param responseCharset
	 * @description
	 */
	public CustomHttpStatus(HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody,
			Charset responseCharset) {
		super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param statusCode
	 * @param statusText
	 * @description
	 */
	public CustomHttpStatus(HttpStatus statusCode, String statusText) {
		super(statusCode, statusText);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param statusCode
	 * @description
	 */
	public CustomHttpStatus(HttpStatus statusCode) {
		super(statusCode);
		// TODO Auto-generated constructor stub
	}
}
