package com.postnikov.epam.rentbike.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParkingParameterValidator {

	private final static String ADDRESS_PATTERN = "[\\wа-яА-ЯёЁ\\s\\.,0-9]{1,50}";
	private final static String CAPACITY_PATTERN = "[1-9]{1}\\d{0,2}";

	public static boolean addressValidate(String address) {

		if (address.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(ADDRESS_PATTERN);
		Matcher m = p.matcher(address);
		return m.matches();
	}

	public static boolean capacityValidate(String capacity) {

		if (capacity.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(CAPACITY_PATTERN);
		Matcher m = p.matcher(capacity);
		return m.matches();
	}

}
