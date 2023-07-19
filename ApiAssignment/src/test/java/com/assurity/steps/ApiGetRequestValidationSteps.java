package com.assurity.steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.ClassicHttpResponse;

import com.assurity.utils.LogPrinter;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.ParseException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ApiGetRequestValidationSteps {

	private String endpoint;
	private ClassicHttpResponse response;

	@Given("the API endpoint with code: {string} is retrieved from the application configuration")
	public void getEndpoint(String endpointCode) {
		Map<String, Object> endpointDetails = LogPrinter.getEndpointDetails(endpointCode);
		endpoint = LogPrinter.constructEndpoint(endpointDetails);
	}

	@When("an HTTP GET request is sent to the API endpoint")
	public void sendGetRequestToApi() throws ParseException, IOException {
		LogPrinter.performGetRequest(endpoint);
	}

	@Then("the response should be received and parsed")
	public void retrieveAndParseResponse() throws IOException {
		response = LogPrinter.getResponse();
		String jsonResponse = LogPrinter.parseResponse(response);
		LogPrinter.printResponseBody(jsonResponse);
	}

	@And("the value of the field: {string} in the response should be {string}")
	public void validate(String field, String expectedValue) {
		String actualValue = LogPrinter.getActualValue(LogPrinter.getJsonResponse(), field);
		boolean isMatch = LogPrinter.isMatch(expectedValue, actualValue);
		LogPrinter.logValidationResult(isMatch, field, expectedValue, actualValue);
	}

	@And("the value of the field: {string} in the response should be {string} on object: {string} having {string} as {string}")
	public void validate(String field, String expectedValue, String object, String propertyHelper,
			String propertyHelperValue) {
		String actualValue = LogPrinter.getActualValue(LogPrinter.getJsonResponse(), field, object, propertyHelper,
				propertyHelperValue);
		boolean isMatch = LogPrinter.isMatch(expectedValue, actualValue);
		LogPrinter.logValidationResult(isMatch, field, expectedValue, actualValue);
	}

	@And("the following fields are validated in the response:")
	public void validate(DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
		int numColumns = data.get(0).size();

		if (numColumns == 2) {
			for (Map<String, String> row : data) {
				String property = row.get("property");
				String expectedValue = row.get("expected_value");

				String actualValue = LogPrinter.getActualValue(LogPrinter.getJsonResponse(), property);
				boolean isMatch = LogPrinter.isMatch(expectedValue, actualValue);
				LogPrinter.logValidationResult(isMatch, property, expectedValue, actualValue);
			}
		}

		if (numColumns == 5) {
			for (Map<String, String> row : data) {
				String property1 = row.get("property");
				String expectedValue1 = row.get("expected_value");
				String object = row.get("object");
				String propertyHelper = row.get("property_helper");
				String propertyHelperValue = row.get("property_helper_value");
				
				if (object == null) {
					String actualValue = LogPrinter.getActualValue(LogPrinter.getJsonResponse(), property1);
					boolean isMatch = LogPrinter.isMatch(expectedValue1, actualValue);
					LogPrinter.logValidationResult(isMatch, property1, expectedValue1, actualValue);
				} else {
					String actualValue = LogPrinter.getActualValue(LogPrinter.getJsonResponse(), property1, object,
							propertyHelper, propertyHelperValue);
					boolean isMatch = LogPrinter.isMatch(expectedValue1, actualValue);
					LogPrinter.logValidationResult(isMatch, property1, expectedValue1, actualValue);
				}
			}
		}
	}

}