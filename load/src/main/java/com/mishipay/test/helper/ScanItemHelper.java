package com.mishipay.test.helper;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mishipay.test.util.ConstantURIs;
import com.mishipay.test.util.Log;
import com.mishipay.test.util.Util;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ScanItemHelper {

	public static JSONObject scanItem(String identifier, String storeId, String accessToken) {
		Map<String, String> headersMap = new HashMap<String, String>();
		Map<String, String> paramsMap = new HashMap<String, String>();
		String url = Util.getPropertyFromPropertiesfile(ConstantURIs.CONFIG_FILE_PATH, "baseUrl")
				+ ConstantURIs.SCAN_ITEM_URI;
		JSONObject json = new JSONObject();
		Response response = null;

		try {
			// creating query parameters map
			paramsMap.put("product_identifier", identifier);
			paramsMap.put("store_id", storeId);

			// creating headers map
			headersMap.put("Bundleid", "com.mishipay.mainapp");
			headersMap.put("Authorization", "Token " + accessToken);

			response = RestAssured.given().headers(headersMap).params(paramsMap).get(url);
		} catch (Exception e) {
			Log.log("Failed to scan items.");
			e.printStackTrace();
		}

		int statusCode = response.getStatusCode();
		Long responseTime = response.getTime();
		String responseMessage = response.getBody().asString();
		if (statusCode == 200) {
			Log.log("Item scanned successfully : " + responseMessage);
			Log.log("Time taken to scan item : " + responseTime);
		} else
			Log.log("Not able to scan Item, got " + statusCode + " status code.");
		json.put("statusCode", statusCode);
		json.put("responseTime", responseTime);
		json.put("responseMessage", responseMessage);

		return json;
	}
}