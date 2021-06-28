package com.mishipay.test.helper;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mishipay.test.util.ConstantURIs;
import com.mishipay.test.util.Log;
import com.mishipay.test.util.Util;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBasketDetailsHelper {

	public static JSONObject getBasketDetails(String basketId, String storeId, String accessToken) {
		Map<String, String> headersMap = new HashMap<String, String>();
		Map<String, String> paramsMap = new HashMap<String, String>();
		String url = Util.getPropertyFromPropertiesfile(ConstantURIs.CONFIG_FILE_PATH, "baseUrl")
				+ ConstantURIs.GET_BASKET_DETAILS_URI;
		JSONObject json = new JSONObject();
		Response response = null;

		try {
			// creating query parameters map
			paramsMap.put("basket_id", basketId);
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
			Log.log("Basket details fetched successfully : " + responseMessage);
			Log.log("Time taken to fetch basket details : " + responseTime);
		}
		json.put("statusCode", statusCode);
		json.put("responseTime", responseTime);
		json.put("responseMessage", responseMessage);

		return json;
	}

}