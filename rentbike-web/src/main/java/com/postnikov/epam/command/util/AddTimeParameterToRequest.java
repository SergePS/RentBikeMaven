package com.postnikov.epam.command.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpServletRequest;

import com.postnikov.epam.rentbike.domain.RequestParameter;

public class AddTimeParameterToRequest {

	private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.S";
	private final static DateTimeFormatter FORMATTER_DATA_TIME = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

	public static void addParam(HttpServletRequest request, String dateTime) {

		LocalDateTime dateFrom = dateFormatFromStringToDate(dateTime);
		LocalDateTime dateTo = LocalDateTime.now();

		long difference = ChronoUnit.SECONDS.between(dateFrom, dateTo);

		long diffSeconds = difference % 60;
		long diffMinutes = difference / 60 % 60;
		long diffHours = difference / (60 * 60) % 24;
		long diffDays = difference / (24 * 60 * 60);

		request.setAttribute(RequestParameter.SECONDS.parameter(), diffSeconds);
		request.setAttribute(RequestParameter.MINUTES.parameter(), diffMinutes);
		request.setAttribute(RequestParameter.HOURS.parameter(), diffHours);
		request.setAttribute(RequestParameter.DAYS.parameter(), diffDays);

	}

	private static LocalDateTime dateFormatFromStringToDate(String dateString) {
		return LocalDateTime.parse(dateString, FORMATTER_DATA_TIME);
	}

}
