package com.assurity.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.Scenario;

/**
 * The `LogPrinter` class provides utility methods to log API testing
 * information and validation results.
 */
public class LogPrinter {

	private static final Logger logger = LoggerFactory.getLogger(LogPrinter.class);
	private static final String divider = "------------------------------------------------------";
	private static final String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	private static final String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

	// Prints the formatted JSON response payload to the log.
	public static void printResponseBody(String formattedJson) throws IOException {
		logger.info("{}", divider);
		logger.info("Payload:");
		logger.info("{}", divider);
		if (formattedJson != null) {
			logger.info(formattedJson);
		}
	}

	// Logs the validation result for a specific field.
	public static void logValidationResult(boolean isMatch, String field, String expectedValue, String actualValue) {
		logger.info("{}", divider);
		logger.info("Field: {}", field);
		logger.info("Expected Value: {}", expectedValue);
		logger.info("Actual Value: {}", actualValue);
		String validationStatus = isMatch ? "PASSED" : "FAILED";
		logger.info("Status: {}", validationStatus);
		if (!isMatch)
			Assert.fail();
		AssertionHelper.asserter(isMatch);
	}

	// Initiates the log with scenario information.
	public static void initiateLog(Scenario scenario) {
		logger.info("{}", divider);
		logger.info("Date: {}", date);
		logger.info("Time: {}", time);
		logger.info("Scenario: {}", scenario.getName());
	}

	// Logs the overall status of the test execution (PASSED or FAILED).
	public static void endLog(boolean isFailed) {
		logger.info("{}", divider);
		String overallValidationStatus = isFailed ? "FAILED" : "PASSED";
		logger.info("Overall Status: {}", overallValidationStatus);
		logger.info("{}", divider);
	}

	// Initiates the validation log for storing validation results.
	public static void initiateValidationLog() {
		logger.info(divider);
		logger.info("Validation Results:");
	}
}
