package com.assurity.utils;

import org.junit.Assert;

/**
 * The `AssertionHelper` class contains a custom assertion method called
 * asserter, which is used to check if a given condition is true. If the
 * condition is false, an assertion error will be thrown.
 */
public class AssertionHelper {

	public static void asserter(boolean isMatch) {
		if (!isMatch) {
			Assert.fail("Assertion failed.");
		}
	}

}