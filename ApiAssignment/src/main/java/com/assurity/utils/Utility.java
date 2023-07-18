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

@SuppressWarnings("deprecation")
public class Utility {
	private static final String CONFIG_FILE = "application-test.yml";

	public static Map<String, Object> readEndpointConfig(String endpointCode) {
		Yaml yaml = new Yaml();
		InputStream inputStream = Utility.class.getResourceAsStream("/" + CONFIG_FILE);
		Map<String, Map<String, Object>> configMap = yaml.load(inputStream);
		return configMap.get(endpointCode);
	}

	public static void printResponseBody(ClassicHttpResponse response) {
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try (InputStream inputStream = entity.getContent()) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(inputStream, StandardCharsets.UTF_8));
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}

					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String responseBody = sb.toString();
					Object json = gson.fromJson(responseBody, Object.class);
					String formattedJson = gson.toJson(json);
					System.out.println(formattedJson);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ClassicHttpResponse performGetRequest(String endpoint) {
		HttpClient httpClient = HttpClients.createDefault();
		HttpUriRequestBase httpRequest = new HttpGet(endpoint);

		try {
			return (ClassicHttpResponse) httpClient.execute(httpRequest);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}