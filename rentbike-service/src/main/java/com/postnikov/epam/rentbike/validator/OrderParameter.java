package com.postnikov.epam.rentbike.validator;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderParameter {
	
	private final static String AMOUNT_PATTERN = "(\\d{1}|[1-9]{1}\\d{1,5})((\\.|,){1}[\\d]{1,2})?";
	
	public boolean amountValidate(String amount) {

		if (amount.isEmpty()) {
			return false;
		}
		
		Pattern p = Pattern.compile(AMOUNT_PATTERN);
		Matcher m = p.matcher(amount);
		if(! m.matches()) {
			return false;
		}
		
		BigDecimal amountNumger = new BigDecimal(amount);
		if(amountNumger.compareTo(BigDecimal.ZERO)==0) {
			return false;
		}

		return true;

	}
}
