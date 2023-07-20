package com.assurity.utils;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.cucumber.datatable.DataTable;

/**
 * The ResponseValidator class provides methods to validate fields in an API
 * response and perform data validation based on the provided DataTable. The
 * class contains the following methods:
 */
public class ResponseValidator {

	// Validates a single field in the API response against an expected value.
	public static void validateField(String responseJson, String field, String expectedValue) {
		String actualValue = getActualValue(HttpApiProcessor.getJsonResponse(), field);
		boolean isMatch = isMatch(expectedValue, actualValue);
		LogPrinter.logValidationResult(isMatch, field, expectedValue, actualValue);
	}

	// Validates a field within a complex object in the API response against an
	// expected value.
	public static void validateField(String responseJson, String field, String expectedValue, String object,
			String propertyHelper, String propertyHelperValue) {
		String actualValue = getActualValue(HttpApiProcessor.getJsonResponse(), field, object, propertyHelper,
				propertyHelperValue);
		boolean isMatch = isMatch(expectedValue, actualValue);
		LogPrinter.logValidationResult(isMatch, field, expectedValue, actualValue);
	}

	// Validates multiple fields in the API response based on the DataTable input.
	public static void validateField(String responseJson, DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> row : data) {
			String field = row.get("property");
			String expectedValue = row.get("expected_value");
			String object = row.get("object");
			String propertyHelper = row.get("property_helper");
			String propertyHelperValue = row.get("property_helper_value");

			int dataColumnCount = getDataColumnCount(dataTable);
			if (dataColumnCount == 2 || areNull(object, propertyHelper, propertyHelperValue)) {
				validateField(responseJson, field, expectedValue);
			} else if (dataColumnCount == 5 && areNotNull(object, propertyHelper, propertyHelperValue)) {
				validateField(responseJson, field, expectedValue, object, propertyHelper, propertyHelperValue);
			} else {
				throw new IllegalArgumentException("Invalid number of columns in DataTable. Expected 2 or 5 columns.");
			}
		}
	}

	// Retrieves the actual value of a field from the JSON response.
	public static String getActualValue(String json, String fieldName) {
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		JsonElement fieldElement = jsonObject.get(fieldName);
		String actualValue = fieldElement.getAsString();
		return actualValue;
	}

	// Retrieves the actual value of a child field within a complex object from the
	// JSON response.
	public static String getActualValue(String json, String childField, String arrayField, String helperChildField,
			String helperChildValue) {
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

	// Checks if the expected value matches the actual value.
	public static boolean isMatch(String expectedValue, String actualValue) {
		return expectedValue.equals(actualValue);
	}

	// Gets the number of columns in the DataTable.
	public static int getDataColumnCount(DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
		int numColumns = data.get(0).size();
		return numColumns;
	}

	// Checks if all the provided fields are null.
	public static boolean areNull(String object, String propertyHelper, String propertyHelperValue) {
		return object == null && propertyHelper == null && propertyHelperValue == null;
	}

	// Checks if all the provided fields are not null.
	public static boolean areNotNull(String object, String propertyHelper, String propertyHelperValue) {
		return object != null && propertyHelper != null && propertyHelperValue != null;
	}

}