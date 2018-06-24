package com.postnikov.epam.rentbike.validator;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BikeParameterValidator {

	private final static String COUNT_PATTERN = "[1-9]";
	private final static String AMOUNT_PATTERN = "(\\d{1}|[1-9]{1}\\d{1,5})((\\.|,){1}[\\d]{1,2})?";
	private final static String MODEL_PATTERN = "[\\w\\-\\s\\.\\dа-яА-ЯёЁ]{1,50}";
	private final static String WHEEL_SIZE_PATTERN = "[1-9]{1}\\d{0,1}";
	private final static String SPEED_COUNT_PATTERN = "[1-9]{1}\\d{0,1}";
	private final static String BIKE_TYPE_PATTERN = "[a-zA-Zа-яА-ЯёЁ\\s]{1,30}";
	private final static String BRAND_PATTERN = "[\\w\\-\\sа-яА-ЯёЁ]{1,30}";
	

	public static boolean countValidate(String count) {
		if (count.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(COUNT_PATTERN);
		Matcher m = p.matcher(count);
		return m.matches();
	}

	public static boolean amountValidate(String amount) {
		if (amount.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(AMOUNT_PATTERN);
		Matcher m = p.matcher(amount);
		if (!m.matches()) {
			return false;
		}

		BigDecimal amountNumber = new BigDecimal(amount);

		if (amountNumber.compareTo(BigDecimal.ZERO) == 0) {
			return false;
		}
		return true;
	}
	
	public static boolean modelValidate(String model) {
		if (model.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(MODEL_PATTERN);
		Matcher m = p.matcher(model);
		return m.matches();
	}
	
	public static boolean wheelSizeValidate(String wheelSize) {
		if (wheelSize.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(WHEEL_SIZE_PATTERN);
		Matcher m = p.matcher(wheelSize);
		return m.matches();
	}
	
	public static boolean speedCountValidate(String speedCount) {
		if (speedCount.isEmpty()) {
			return false;
		}

		Pattern p = Pattern.compile(SPEED_COUNT_PATTERN);
		Matcher m = p.matcher(speedCount);
		return m.matches();
	}
	
	public static boolean bikeTypeValidate(String bikeType) {
		if(bikeType.isEmpty()) {
			return false;
		}
		
		Pattern p = Pattern.compile(BIKE_TYPE_PATTERN);
		Matcher m = p.matcher(bikeType);
		return m.matches();
	}
	
	public static boolean brandValidate(String brand) {
		if(brand.isEmpty()) {
			return false;
		}
		
		Pattern p = Pattern.compile(BRAND_PATTERN);
		Matcher m = p.matcher(brand);
		return m.matches();
	}
	
}
