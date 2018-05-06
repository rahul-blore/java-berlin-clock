package com.ubs.opsit.interviews.service;

import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.ubs.opsit.interviews.TimeConverter;

public class TimeConverterService implements TimeConverter {

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	private static final String SECOND_BLINK = "Y";
	private static final String HOUR_BLINK = "R";	
	private static final String OFF = "O";


	@Override
	public String convertTime(String aTime) {

		StringBuilder output = new StringBuilder();
		TemporalAccessor raw = dateTimeFormatter.parse(aTime);

		int hour = raw.query(LocalTime::from).getHour();
		int minutes = raw.query(LocalTime::from).getMinute();
		int seconds = raw.query(LocalTime::from).getSecond();

		Period period = raw.query(DateTimeFormatter.parsedExcessDays());

		// to accept date 24:00:00
		if (period.equals(Period.ofDays(1))) {
			hour = 24; minutes = 0; seconds = 0;
		}

		if (checkEvenInput(seconds)) {
			output.append(SECOND_BLINK);
		} else {
			output.append(OFF);
		}

		output.append(System.lineSeparator());
		appendHourRows(output, hour);
		appendMinuteRows(output, minutes);

		return output.toString();
	}

	/**
	 * @param input
	 * @return boolean value of check
	 */
	private boolean checkEvenInput(int input) {
		if ((input % 2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This will append the hour rows in StringBuilder objects
	 * 
	 * @param sbf
	 */
	private void appendHourRows(StringBuilder sbf, int hour) {
		int quot = hour / 5;
		int remainder = hour % 5;

		loopAndAppend(sbf, quot, HOUR_BLINK, false, 0);
		loopAndAppend(sbf, 4 - quot, OFF, false, 0);
		sbf.append(System.lineSeparator());

		loopAndAppend(sbf, remainder, HOUR_BLINK, false, 0);
		loopAndAppend(sbf, 4 - remainder, OFF, false, 0);
		sbf.append(System.lineSeparator());

	}

	/**
	 * Append the minute rows in the StringBuilder objects
	 * 
	 * @param minute
	 * 
	 * @param sbf
	 */
	private void appendMinuteRows(StringBuilder stringBuilder, int minute) {

		int quot = minute / 5;
		int remainder = minute % 5;

		loopAndAppend(stringBuilder, quot, SECOND_BLINK, true, 3);
		loopAndAppend(stringBuilder, 11 - quot, OFF, false, 0);
		stringBuilder.append(System.lineSeparator());

		loopAndAppend(stringBuilder, remainder, SECOND_BLINK, false, 0);
		loopAndAppend(stringBuilder, 4 - remainder, OFF, false, 0);
	}

	/**
	 * Loop the appender based on the threshold
	 * 
	 * @param stringBuilder
	 * @param threshold
	 * @param appender
	 * @param factor3Switch
	 */
	private void loopAndAppend(StringBuilder stringBuilder, int threshold, String appender, boolean factor3Switch,
			int factorValue) {
		for (int i = 1; i <= threshold; i++) {
			if (factor3Switch) {
				if (i > 0 && (i % factorValue) == 0) {
					stringBuilder.append(HOUR_BLINK);
				} else {
					stringBuilder.append(appender);
				}
			} else {
				stringBuilder.append(appender);
			}
		}
	}
}