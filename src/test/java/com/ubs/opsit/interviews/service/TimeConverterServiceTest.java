package com.ubs.opsit.interviews.service;

import java.time.format.DateTimeParseException;

import org.junit.Test;

import com.ubs.opsit.interviews.TimeConverter;

public class TimeConverterServiceTest {

	private final TimeConverter converter = new TimeConverterService();

	@Test(expected = NullPointerException.class)
	public void testNullTime() {
		converter.convertTime(null);
	}

	@Test(expected = DateTimeParseException.class)
	public void testEmptyTime() {
		converter.convertTime("");
	}

	@Test(expected = DateTimeParseException.class)
	public void testIllegalFormatTime() {
		converter.convertTime("4 PM");
	}

}
