package com.assurity.testrunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * TestRunner class is the entry point for running Cucumber tests.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features", // Specifies the location of the feature files
    glue = { "com.assurity.steps", "com.assurity.hooks" }, // Specifies the package where the step definitions are located
    tags = "@Scenario1 or @Scenario2 or @Scenario3", // Runs scenarios with the specified tag(s)
    plugin = { "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" } // Configures the Cucumber plugin for generating Extent reports (ExtentCucumberAdapter)
)
public class TestRunner {
    // The TestRunner class is left empty as it is used for configuration purposes.
}
