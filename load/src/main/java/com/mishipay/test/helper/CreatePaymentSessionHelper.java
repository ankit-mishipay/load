package com.mishipay.test.helper;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mishipay.test.util.ConstantURIs;
import com.mishipay.test.util.Log;
import com.mishipay.test.util.Util;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreatePaymentSessionHelper {

	public static JSONObject createPaymentSession(String storeId, String accessToken, String orderId) {
		Map<String, String> headersMap = new HashMap<String, String>();
		Map<String, String> paramsMap = new HashMap<String, String>();
		Map<String, String> formDataMap = new HashMap<String, String>();
		String url = Util.getPropertyFromPropertiesfile(ConstantURIs.CONFIG_FILE_PATH, "baseUrl")
				+ ConstantURIs.CREATE_PAYMENT_SESSION_URI;
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
			formDataMap.put("order_id", orderId);
			formDataMap.put("channel", "IOS");
			formDataMap.put("payment_method", "credit_card");
			formDataMap.put("psp_type", "stripeconnect");
			response = RestAssured.given().headers(headersMap).queryParams(paramsMap).formParams(formDataMap)
					.urlEncodingEnabled(true).when().post(url);
		} catch (Exception e) {
			Log.log("Failed to create payment session");
			e.printStackTrace();
		}
		int statusCode = response.getStatusCode();
		long responseTime = response.getTime();
		String responseMessage = response.getBody().asString().toString();
		if (statusCode == 200) {
			Log.log("Payment session created successfully : " + responseMessage);
			Log.log("Time taken to create payment session : " + responseTime);
		}
		json.put("statusCode", statusCode);
		json.put("responseTime", responseTime);
		json.put("responseMessage", responseMessage);
		return json;
	}
}