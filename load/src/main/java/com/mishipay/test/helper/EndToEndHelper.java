package com.mishipay.test.helper;

import org.json.JSONObject;

import com.mishipay.test.util.ConstantURIs;
import com.mishipay.test.util.Log;
import com.mishipay.test.util.Util;

public class EndToEndHelper {

	String storeId = Util.getPropertyFromPropertiesfile(ConstantURIs.CONFIG_FILE_PATH, "storeId");
	String productIdentifier = Util.getPropertyFromPropertiesfile(ConstantURIs.CONFIG_FILE_PATH, "storeId");
	volatile int testCount = 0;

	public void endToEnd() {
		JSONObject addItemResponse = null, createOrderResponse = null, createPaymnentSessionResponse = null,
				verifyPaymentResponse = null;

		JSONObject closestStoreResponse = ClosestStoreHelper.getClosestStore(storeId);
		if (closestStoreResponse.getInt("statusCode") != 200)
			Log.log("Closest store API failed");

		String accessToken = CreateUserHelper.createUsers(storeId);

		ScanItemHelper.scanItem(productIdentifier, storeId, accessToken);

		addItemResponse = AddItemToBasketHelper.addItemToBasket(storeId, accessToken, productIdentifier, 2);

		createOrderResponse = CreateOrderHelper.createOrder(Util.getBasketId(addItemResponse), storeId, accessToken);

		createPaymnentSessionResponse = CreatePaymentSessionHelper.createPaymentSession(storeId, accessToken,
				createOrderResponse.getString("orderId"));

		if (createPaymnentSessionResponse.getInt("statusCode") == 200)
			verifyPaymentResponse = VerifyPaymentHelper.verifyPayment(createOrderResponse.getString("orderId"), storeId,
					accessToken);

		if (verifyPaymentResponse.getInt("statusCode") == 200)
			Log.log(testCount + " Test Completed");

	}
}