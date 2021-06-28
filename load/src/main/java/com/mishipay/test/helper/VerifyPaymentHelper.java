package com.mishipay.test.helper;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mishipay.test.util.ConstantURIs;
import com.mishipay.test.util.Log;
import com.mishipay.test.util.Util;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class VerifyPaymentHelper {

	public static JSONObject verifyPayment(String orderId, String storeId, String accessToken) {
		Map<String, String> headersMap = new HashMap<String, String>();
		Map<String, String> paramsMap = new HashMap<String, String>();
		Map<String, String> formDataMap = new HashMap<String, String>();
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
			formDataMap.put("stripe_payment_method_id", "pm_card_gb");
			formDataMap.put("return_url", "https://mishipay.com/payment");
			formDataMap.put("save_card_in_db", "false");
			formDataMap.put("channel", "IOS");
			formDataMap.put("payment_method", "credit_card");
			formDataMap.put("psp_type", "stripeconnect");
			String url = Util.getPropertyFromPropertiesfile(ConstantURIs.CONFIG_FILE_PATH, "baseUrl")
					+ ConstantURIs.VERIFY_PAYMENT_SESSION_URI;
			response = RestAssured.given().headers(headersMap).queryParams(paramsMap).formParams(formDataMap)
					.urlEncodingEnabled(true).when().post(url);
		} catch (Exception e) {
			Log.log("Failed to create payment session");
			e.printStackTrace();
		}

		String responseMessage = response.getBody().asString();
		int statusCode = response.statusCode();
		long responseTime = response.time();
		if (statusCode == 200) {
			Log.log("Payment session created successfully : " + responseMessage);
			Log.log("Time taken to create payment session : " + responseTime);
		}
		json.put("responseMessage", responseMessage);
		json.put("statusCode", statusCode);
		json.put("responseTime", responseTime);

		return json;
	}
}
