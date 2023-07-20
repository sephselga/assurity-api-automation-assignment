package com.assurity.hooks;

import com.assurity.utils.LogPrinter;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Hook class contains Cucumber hooks for executing actions before and after
 * scenarios.
 */
public class Hook {

	// Cucumber Before hook to initiate log for the scenario.
	@Before
	public void before(Scenario scenario) {
		LogPrinter.initiateLog(scenario);
	}

	// Cucumber After hook to end the log and print the overall status of the
	// scenario.
	@After
	public void after(Scenario scenario) {
		LogPrinter.endLog(scenario.isFailed());
	}

}