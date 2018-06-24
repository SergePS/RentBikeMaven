package com.postnikov.epam.rentbike.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateFormatter {

	private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.S";
	private final static DateTimeFormatter FORMATTER_DATA_TIME = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
	
	private final static String DATE_TIME_PATTERN_TO_VIEW = "dd.MM.yyyy HH:mm:ss";
	private final static DateTimeFormatter FORMATTER_DATA_TIME_TO_VIEW = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_TO_VIEW);
	
	private final static String DATE_PATTERN_TO_DB = "yyyy-MM-dd";
	private final static DateTimeFormatter FORMATTER_DB = DateTimeFormatter.ofPattern(DATE_PATTERN_TO_DB);
	
	private final static String DATE_PATTERN_TO_VIEW = "dd.MM.yyyy";
	private final static DateTimeFormatter FORMATTER_VIEW = DateTimeFormatter.ofPattern(DATE_PATTERN_TO_VIEW);
	
	public static String takeCurrentDateToDB() {
		LocalDate date = LocalDate.now();
		return date.format(FORMATTER_DB);
	}

	public static String takeCurrentDateTimeToDB() {
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.format(FORMATTER_DATA_TIME);
	}

	public static String modifyDateToDB(String dateView) {
		LocalDate date = LocalDate.parse(dateView, FORMATTER_VIEW);
		return date.format(FORMATTER_DB);
	}

	public static String modifyDateToView(String dateDB) {
		LocalDate date = LocalDate.parse(dateDB, FORMATTER_DB);
		return date.format(FORMATTER_VIEW);
	}
	
	public static String modifyDateTimeToView(String dateDB) {
		LocalDateTime date = LocalDateTime.parse(dateDB, FORMATTER_DATA_TIME);
		return date.format(FORMATTER_DATA_TIME_TO_VIEW);
	}

	public static long takeMinutesBetweenDates(String dateFromString, String dateToString) {
		LocalDateTime dateFrom = dateFormatFromStringToDate(dateFromString);
		LocalDateTime dateTo = dateFormatFromStringToDate(dateToString);

		return ChronoUnit.MINUTES.between(dateFrom, dateTo);
	}

	private static LocalDateTime dateFormatFromStringToDate(String dateString) {
		return LocalDateTime.parse(dateString, FORMATTER_DATA_TIME);
	}

}
