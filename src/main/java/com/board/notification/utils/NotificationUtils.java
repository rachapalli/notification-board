package com.board.notification.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class NotificationUtils {

	public static String encodeString(String input) {
		return Base64.getEncoder().encodeToString(input.getBytes());
	}

	public static String decodeString(String input) {
		return new String(Base64.getDecoder().decode(input));
	}

	/**
	 * Method to get UK date and time
	 * 
	 * @return UK date
	 */
	public static Date getUKTime() {
		Date date = null;
		try {
			ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(NotificationConstants.ZONE_UK));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(NotificationConstants.DATE_FORMAT);
			DateFormat format = new SimpleDateFormat(NotificationConstants.DATE_FORMAT, Locale.ENGLISH);
			date = format.parse(zonedDateTime.format(formatter));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Method to validate email
	 * 
	 * @param email
	 * @return true if valid else false
	 */
	public static boolean isValidEmail(String email) {
		if (email == null || email.isEmpty()) {
			return false;
		}
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(NotificationConstants.EMAIL_PATTERN);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
	
	public static String getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) authentication.getPrincipal();
		return userDetail.getUsername();
	}
	
	/**
	 * function to generate a random string of length n
	 * 
	 * @param n
	 * @returns AlphaNumericString with length n
	 */
	public static String getAlphaNumericString(int n) {
		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (NotificationConstants.ALPHANUMERIC_STRING.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(NotificationConstants.ALPHANUMERIC_STRING.charAt(index));
		}

		return sb.toString();
	}
	
}
