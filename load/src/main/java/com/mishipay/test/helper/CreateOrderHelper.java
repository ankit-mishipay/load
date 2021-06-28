package com.mishipay.test.helper;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mishipay.test.util.ConstantURIs;
import com.mishipay.test.util.Log;
import com.mishipay.test.util.Util;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreateOrderHelper {

	public static JSONObject createOrder(String basketId, String storeId, String accessToken) {
		Map<String, String> headersMap = new HashMap<String, String>();
		Map<String, String> paramsMap = new HashMap<String, String>();
		Map<String, String> formDataMap = new HashMap<String, String>();
		String url = Util.getPropertyFromPropertiesfile(ConstantURIs.CONFIG_FILE_PATH, "baseUrl")
				+ ConstantURIs.CREATE_ORDER_URI;
		JSONObject json = new JSONObject();
		Response response = null;
		try {
			// updating the mandatory query parameters
			paramsMap.put("store_id", storeId);

			// updating mandatory headers
			headersMap.put("Authorization", "Token " + accessToken);
			headersMap.put("Bundleid", "app.mishipay.com");

			// updating the mandatory form parameters
			formDataMap.put("store_id", storeId);
			formDataMap.put("basket_id", basketId);
			formDataMap.put("platform", "IOS");

			response = RestAssured.given().headers(headersMap).queryParams(paramsMap).formParams(formDataMap)
					.urlEncodingEnabled(true).when().post(url);
		} catch (Exception e) {
			Log.log("Failed to add item to basket");
			e.printStackTrace();
		}

		int statusCode = response.statusCode();
		if (statusCode == 200) {
			JSONObject responseMessage = new JSONObject(response.getBody().asString().toString());
			String orderId = responseMessage.getJSONObject("data").getString("order_id");
			Long oId = responseMessage.getJSONObject("data").getLong("o_id");
			Long responseTime = response.getTime();
			if (statusCode == 200) {
				Log.log("Order created successfully : " + responseMessage);
				Log.log("Time taken to create order : " + responseTime);
			}
			json.put("responseTime", responseTime);
			json.put("statusCode", statusCode);
			json.put("orderId", orderId);
			json.put("oId", oId);
		}
		return json;
	}
}