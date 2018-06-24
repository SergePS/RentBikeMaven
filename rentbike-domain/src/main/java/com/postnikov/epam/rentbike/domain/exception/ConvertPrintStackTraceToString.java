package com.postnikov.epam.rentbike.domain.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ConvertPrintStackTraceToString {

	public static String convert(Throwable e) {
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		return result.toString();

	}

}
