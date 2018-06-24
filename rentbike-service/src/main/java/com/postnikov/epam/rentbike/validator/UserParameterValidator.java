package com.postnikov.epam.rentbike.validator;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.rentbike.domain.ApplicationProperty;

public class UserParameterValidator {
	private final static Logger logger = LogManager.getLogger(UserParameterValidator.class);

	private final static String LOGIN_PATTERN = "[a-zA-Z]{1}[a-zA-Z0-9]{2,20}";
	private final static String NAME_PATTERN = "[a-zA-Zа-яА-ЯЁё]{3,15}";
	private final static String EMAIL_PATTERN = "[a-zA-Z]{1}\\w{1,15}@[a-zA-Z]{1,10}\\.[a-z]{2,3}";
	private final static String BIRTHDAY_PATTERN = "(0{1}[1-9]|[1-2]{1}[0-9]{1}|3{1}[0-1]{1})\\.(0{1}[1-9]|1{1}[0-2]{1})\\.(1{1}9{1}[0-9]{2}|2{1}0{1}[0-9]{2})";
	private final static String CRADIT_CARD_PATTERN = "[1-9]{1}\\d{15}";
	private final static String PASSWORD_LETTER_PATTERN = "[\\wа-яА-ЯёЁ]";
	private final static String ID_PATTERN = "[1-9]{1}\\d{0,18}";

	private final static String MIN_REGISTER_AGE_PROP_KEY = "min_age";
	private final static int MIN_AGE = Integer.parseInt(ApplicationProperty.takeProperty().getProperty(MIN_REGISTER_AGE_PROP_KEY));

	private final static String DOT_SEPARATOR = "\\.";

	private final static int YEAR_ARREY_ADDRESS = 2;
	private final static int MONTH_ARREY_ADDRESS = 1;
	private final static int DAY_ARREY_ADDRESS = 0;
	
	
	public static boolean idValidate(String id) {
		
		if (id == null || id.isEmpty()) {
			return false;
		}
		
		Pattern p = Pattern.compile(ID_PATTERN);
		Matcher m = p.matcher(id);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	public static boolean loginValidate(String login) {

		if (login == null || login.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(LOGIN_PATTERN);
		Matcher m = p.matcher(login);
		if (!m.matches()) {
			return false;
		}

		return true;
	}

	public static boolean passwordValidate(char[] password) {

		if (password.length < 3 || password.length > 15) {
			return false;
		}
		
		Pattern p = Pattern.compile(PASSWORD_LETTER_PATTERN);
		Matcher m;
		for(int i = 0; i<password.length; i++) {
			m = p.matcher(String.valueOf(password[i]));
			if(!m.matches()) {
				return false;
			}
		}
		return true;
	}

	public static boolean nameValidate(String name) {

		if (name == null || name.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(NAME_PATTERN);
		Matcher m = p.matcher(name);
		if (!m.matches()) {
			return false;
		}

		return true;
	}

	public static boolean emailValidate(String email) {

		if (email == null || email.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(EMAIL_PATTERN);
		Matcher m = p.matcher(email);
		if (!m.matches()) {
			return false;
		}

		return true;
	}

	public static boolean birthdayValidate(String birthday) {

		if (birthday == null || birthday.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(BIRTHDAY_PATTERN);
		Matcher m = p.matcher(birthday);
		if (!m.matches()) {
			return false;
		}

		String[] DateArrey = birthday.split(DOT_SEPARATOR);
		int year = Integer.parseInt(DateArrey[YEAR_ARREY_ADDRESS]);
		int month = Integer.parseInt(DateArrey[MONTH_ARREY_ADDRESS]);
		int day = Integer.parseInt(DateArrey[DAY_ARREY_ADDRESS]);

		try {
			LocalDate dateCheck = LocalDate.of(year, month, day);

			if ((LocalDate.now().getYear() - dateCheck.getYear()) < MIN_AGE) {
				return false;
			}

		} catch (DateTimeException e) {
			logger.log(Level.ERROR, "Date " + birthday + "is not valid, " + e);
			return false;
		}

		return true;
	}
	
	public static boolean dateValidate(String date) {

		if (date == null || date.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(BIRTHDAY_PATTERN);
		Matcher m = p.matcher(date);
		if (!m.matches()) {
			return false;
		}

		String[] DateArrey = date.split(DOT_SEPARATOR);
		int year = Integer.parseInt(DateArrey[YEAR_ARREY_ADDRESS]);
		int month = Integer.parseInt(DateArrey[MONTH_ARREY_ADDRESS]);
		int day = Integer.parseInt(DateArrey[DAY_ARREY_ADDRESS]);

		try {
			LocalDate.of(year, month, day);
		} catch (DateTimeException e) {
			logger.log(Level.ERROR, "Date " + date + "is not valid, " + e);
			return false;
		}

		return true;
	}

	public static boolean craditcardValidate(String creditCard) {

		if (creditCard == null || creditCard.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(CRADIT_CARD_PATTERN);
		Matcher m = p.matcher(creditCard);
		if (!m.matches()) {
			return false;
		}

		return true;
	}

}
