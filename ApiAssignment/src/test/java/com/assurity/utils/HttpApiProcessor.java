package com.assurity.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.cucumber.java.Scenario;

/**
 * The `HttpApiProcessor` class handles HTTP requests and responses for API
 * testing. It utilizes HttpClient to perform GET requests, retrieve endpoint
 * details from the configuration, parse and pretty-print JSON responses, and
 * store the formatted JSON for further validations.
 */
@SuppressWarnings("deprecation")
public class HttpApiProcessor {

	// Path to the configuration file
	private static final String CONFIG_FILE = "/application-test.yml";
	private static String formattedJson;
	private static ClassicHttpResponse response;

	// Get endpoint details from the configuration file
	public static Map<String, Object> getEndpointDetails(String endpointCode) {
		Yaml yaml = new Yaml();
		InputStream inputStream = LogPrinter.class.getResourceAsStream(CONFIG_FILE);
		Map<String, Map<String, Object>> configMap = yaml.load(inputStream);
		return configMap.get(endpointCode);
	}

	// Construct the complete endpoint URL from endpoint details
	public static String constructEndpoint(Map<String, Object> endpointDetails) {
		String protocol = (String) endpointDetails.get("protocol");
		String host = (String) endpointDetails.get("host");
		String version = (String) endpointDetails.get("version");
		String resource = (String) endpointDetails.get("resource");
		String queryParameter = (String) endpointDetails.get("query-parameter");
		return protocol + "://" + host + "/" + version + resource + "?" + queryParameter;
	}

	// Parse the HTTP response and pretty-print the JSON
	public static void parseResponse(ClassicHttpResponse response, Scenario scenario) throws IOException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream inputStream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			String responseBody = sb.toString();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Object json = gson.fromJson(responseBody, Object.class);
			formattedJson = gson.toJson(json);

			reader.close();
			inputStream.close();

			// Print the formatted JSON to log and initiate validation log
			LogPrinter.printResponseBody(formattedJson);
			LogPrinter.initiateValidationLog();
		}
	}

	// Get the formatted JSON response
	public static String getJsonResponse() {
		return formattedJson;
	}

	// Perform an HTTP GET request and get the response
	public static void performGetRequest(String endpoint) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpUriRequestBase httpRequest = new HttpGet(endpoint);
		response = (ClassicHttpResponse) httpClient.execute(httpRequest);
	}

	// Get the HTTP response
	public static ClassicHttpResponse getResponse() {
		return response;
	}

}
