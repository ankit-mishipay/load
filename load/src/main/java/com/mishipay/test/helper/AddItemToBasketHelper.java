package com.mishipay.test.helper;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mishipay.test.util.ConstantURIs;
import com.mishipay.test.util.Log;
import com.mishipay.test.util.Util;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AddItemToBasketHelper {

	public static JSONObject addItemToBasket(String storeId, String accessToken, String productIdentifier,
			int quantity) {
		Map<String, String> headersMap = new HashMap<String, String>();
		Map<String, String> paramsMap = new HashMap<String, String>();
		Map<String, String> formDataMap = new HashMap<String, String>();
		String url = Util.getPropertyFromPropertiesfile(ConstantURIs.CONFIG_FILE_PATH, "baseUrl")
				+ ConstantURIs.ADD_ITEM_TO_BASKET_URI;
		JSONObject json = new JSONObject();
		Response response = null;
		try {
			// updating the mandatory query parameters
			paramsMap.put("store_id", storeId);

			// updating mandatory headers
			headersMap.put("Authorization", "Token " + accessToken);
			headersMap.put("Bundleid", "app.mishipay.com");

			// updating the mandatory form parameters
			formDataMap.put("product_identifier", productIdentifier);
			formDataMap.put("item_quantity", String.valueOf(quantity));
			formDataMap.put("store_id", storeId);

			response = RestAssured.given().headers(headersMap).queryParams(paramsMap).formParams(formDataMap)
					.urlEncodingEnabled(true).when().post(url);
		} catch (Exception e) {
			e.printStackTrace();
			Log.log("Failed to add item to basket");
		}

		int statusCode = response.getStatusCode();
		long responseTime = response.getTime();
		String responseMessage = response.getBody().asString();
		if (statusCode == 200) {
			Log.log("Item added to basket successfully : " + responseMessage);
			Log.log("Time taken to add item to basket : " + responseTime);
		}
		json.put("statusCode", statusCode);
		json.put("responseTime", responseTime);
		json.put("responseMessage", responseMessage);

		return json;
	}
}