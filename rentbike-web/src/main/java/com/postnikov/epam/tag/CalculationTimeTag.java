package com.postnikov.epam.tag;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.postnikov.epam.rentbike.domain.exception.ConvertPrintStackTraceToString;

public class CalculationTimeTag extends TagSupport {
	
	private static Logger logger = LogManager.getLogger(CalculationTimeTag.class);

	private static final long serialVersionUID = -5306548341656156312L;

	private final static String DATE_TIME_PATTERN_TO_VIEW = "dd.MM.yyyy HH:mm:ss";
	private final static DateTimeFormatter VIEW_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_TO_VIEW);

	private String startTime;
	private String finishTime;

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public int doStartTag() throws JspTagException {

		try {
			JspWriter out = pageContext.getOut();

			if (startTime != "" && finishTime != "") {
				LocalDateTime dateFrom = LocalDateTime.parse(startTime, VIEW_FORMATTER);
				LocalDateTime dateTo = LocalDateTime.parse(finishTime, VIEW_FORMATTER);

				long difference = ChronoUnit.MINUTES.between(dateFrom, dateTo);

				out.write(String.valueOf(difference));
			}

		} catch (IOException e) {
			logger.log(Level.ERROR, "Write tag error, " + ConvertPrintStackTraceToString.convert(e));
		}

		return SKIP_BODY;
	}

}
