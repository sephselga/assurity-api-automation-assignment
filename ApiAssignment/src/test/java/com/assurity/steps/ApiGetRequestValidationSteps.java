package com.assurity.steps;

import java.util.Map;

import org.apache.hc.core5.http.ClassicHttpResponse;

import com.assurity.utils.Utility;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.ParseException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ApiGetRequestValidationSteps {

	private String endpoint;
	private ClassicHttpResponse classicResponse;

	@Given("the API endpoint with code: {string} is retrieved from the application configuration")
	public void getEndpoint(String endpointCode) {
		Map<String, Object> endpointConfig = Utility.readEndpointConfig("endpoint-tmsandbox");
		String protocol = (String) endpointConfig.get("protocol");
		String host = (String) endpointConfig.get("host");
		String version = (String) endpointConfig.get("version");
		String resource = (String) endpointConfig.get("resource");
		String queryParameter = (String) endpointConfig.get("query-parameter");

		endpoint = protocol + "://" + host + "/" + version + resource + "?" + queryParameter;
	}

	@When("an HTTP GET request is sent to the API endpoint")
	public void sendGetRequestToApi() throws ParseException {
		classicResponse = Utility.performGetRequest(endpoint);
	}
	
	@Then("the response should be received and parsed")
	public void retrieveAndParseResponse() {
		Utility.printResponseBody(classicResponse);
	}

}