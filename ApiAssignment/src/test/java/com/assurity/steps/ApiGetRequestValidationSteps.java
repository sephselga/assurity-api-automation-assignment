package com.assurity.steps;

import java.io.IOException;
import java.util.Map;

import org.apache.hc.core5.http.ClassicHttpResponse;

import com.assurity.utils.HttpApiProcessor;
import com.assurity.utils.ResponseValidator;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.ParseException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * ApiGetRequestValidationSteps class contains step definitions for the API
 * validation scenarios.
 */
public class ApiGetRequestValidationSteps {

	private String endpoint;
	private ClassicHttpResponse response;
	private String responseJson;
	private Scenario scenario;

	// Step definition to retrieve the API endpoint details based on the provided
	// endpoint code from the application configuration.
	@Given("the API endpoint with code: {string} is retrieved from the application configuration")
	public void getEndpoint(String endpointCode) {
		Map<String, Object> endpointDetails = HttpApiProcessor.getEndpointDetails(endpointCode);
		endpoint = HttpApiProcessor.constructEndpoint(endpointDetails);
	}

	// Step definition to send an HTTP GET request to the API endpoint.
	@When("an HTTP GET request is sent to the API endpoint")
	public void sendGetRequestToApi() throws ParseException, IOException {
		HttpApiProcessor.performGetRequest(endpoint);
	}

	// Step definition to receive and parse the response from the API.
	@Then("the response should be received and parsed")
	public void retrieveAndParseResponse() throws IOException {
		response = HttpApiProcessor.getResponse();
		HttpApiProcessor.parseResponse(response, scenario);
		responseJson = HttpApiProcessor.getJsonResponse();
	}

	// Step definition to validate a specific field in the API response.
	@And("the value of the field: {string} in the response should be {string}")
	public void validate(String field, String expectedValue) {
		ResponseValidator.validateField(responseJson, field, expectedValue);
	}

	// Step definition to validate a specific field in the API response, within a
	// complex object.
	@And("the value of the field: {string} in the response should be {string} on object: {string} having {string} as {string}")
	public void validate(String field, String expectedValue, String object, String propertyHelper,
			String propertyHelperValue) {
		ResponseValidator.validateField(responseJson, field, expectedValue, object, propertyHelper,
				propertyHelperValue);
	}

	// Step definition to validate multiple fields in the API response based on the
	// provided DataTable.
	@And("the following fields are validated in the response:")
	public void validate(DataTable dataTable) {
		ResponseValidator.validateField(responseJson, dataTable);
	}

}
