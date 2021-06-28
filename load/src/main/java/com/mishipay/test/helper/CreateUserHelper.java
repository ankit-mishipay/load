package com.mishipay.test.helper;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.mishipay.test.util.ConstantURIs;
import com.mishipay.test.util.Log;
import com.mishipay.test.util.Util;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreateUserHelper {

	public static String createUsers(String storeId) {
		Map<String, String> headersMap = new HashMap<String, String>();
		Map<String, String> formDataMap = new HashMap<String, String>();
		String url = Util.getPropertyFromPropertiesfile(ConstantURIs.CONFIG_FILE_PATH, "baseUrl")
				+ ConstantURIs.SIGN_UP;
		Response response = null;
		try {
			String randomName = Util.getRandomString();
			headersMap.put("Bundleid", "app.mishipay.com");
			formDataMap.put("first_name", randomName);
			formDataMap.put("last_name", Util.getRandomString());
			formDataMap.put("email", randomName + "@mishipay.com");
			formDataMap.put("password", "Welcome@123");
			formDataMap.put("store_id", storeId);
			response = RestAssured.given().headers(headersMap).formParams(formDataMap).urlEncodingEnabled(true).when()
					.post(url);
		} catch (Exception e) {
			Log.log("Failed to logIn");
			e.printStackTrace();
		}

		String responseString = response.getBody().asString().toString();
		int responseCode = response.getStatusCode();
		long reposnseTime = response.time();
		String accessToken = "";
		if (responseCode == 200) {
			JSONObject responseJson = new JSONObject(responseString);
			accessToken = responseJson.getJSONObject("result").getString("access_token");
			Log.log("Access Token generated : " + accessToken);
			Log.log("Time taken to register user : " + reposnseTime);
		} else
			Log.log("Failed to get access token");

		return accessToken;
	}
}