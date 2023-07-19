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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@SuppressWarnings("deprecation")
public class LogPrinter {
	private static final String CONFIG_FILE = "application-test.yml";
	private static String formattedJson;
	private static ClassicHttpResponse response;
	
	private static final Logger logger = LoggerFactory.getLogger(LogPrinter.class);

	public static Map<String, Object> getEndpointDetails(String endpointCode) {
		Yaml yaml = new Yaml();
		InputStream inputStream = LogPrinter.class.getResourceAsStream("/" + CONFIG_FILE);
		Map<String, Map<String, Object>> configMap = yaml.load(inputStream);
		return configMap.get(endpointCode);
	}
	
	public static String constructEndpoint(Map<String, Object> endpointDetails) {
		String protocol = (String) endpointDetails.get("protocol");
		String host = (String) endpointDetails.get("host");
		String version = (String) endpointDetails.get("version");
		String resource = (String) endpointDetails.get("resource");
		String queryParameter = (String) endpointDetails.get("query-parameter");
		return protocol + "://" + host + "/" + version + resource + "?" + queryParameter;
	}
	
    public static String parseResponse(ClassicHttpResponse response) throws IOException {
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

            return formattedJson;
        }

        return null;
    }

	public static void printResponseBody(String formattedJson) throws IOException {
        if (formattedJson != null) {
            logger.info(formattedJson);
        }
	}
	
	public static String getJsonResponse() {
		return formattedJson;
	}

	public static void performGetRequest(String endpoint) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpUriRequestBase httpRequest = new HttpGet(endpoint);
		response = (ClassicHttpResponse) httpClient.execute(httpRequest);
	}
	
	public static ClassicHttpResponse getResponse()  {
		return response;
	}

	public static String getActualValue(String json, String fieldName) {
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		JsonElement fieldElement = jsonObject.get(fieldName);
		String actualValue = fieldElement.getAsString();
		return actualValue;
	}
	
	public static boolean isMatch(String expectedValue, String actualValue) {
		return expectedValue.equals(actualValue);
	}

    public static String getActualValue(String json, String childField, String arrayField, String helperChildField, String helperChildValue) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        for (JsonElement element : jsonObject.getAsJsonArray(arrayField)) {
            JsonObject object = element.getAsJsonObject();
            JsonElement helperChildElement = object.get(helperChildField);
            if (helperChildElement != null && helperChildElement.getAsString().equals(helperChildValue)) {
                JsonElement childElement = object.get(childField);
                if (childElement != null) {
                    String actualValue = childElement.getAsString();
                    return actualValue;
                }
            }
        }
        return null;
    }

	public static void logValidationResult(boolean isMatch, String field, String expectedValue, String actualValue) {
			logger.info("FIELD NAME: {}", field);
			logger.info("EXPECTED VALUE: {}", expectedValue);
			logger.info("ACTUAL VALUE: {}", actualValue);
		    String validationStatus = isMatch ? "PASSED" : "FAILED";
		    logger.info("VALIDATION STATUS: {}", validationStatus);
	}
}