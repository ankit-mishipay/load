package com.mishipay.test.util;

public class ConstantURIs {

	// API Uri constants
	public static final String CLOSEST_STORE_URI = "/d/v2/stores/closest-stores/";
	public static final String SIGN_UP = "/d/customer/signup/";
	public static final String SCAN_ITEM_URI = "/item-management/v1/item-scan/";
	public static final String ADD_ITEM_TO_BASKET_URI = "/item-management/v1/add-item-to-basket/";
	public static final String GET_BASKET_DETAILS_URI = "/item-management/v1/get-basket-details/";
	public static final String CREATE_ORDER_URI = "/order-management/v1/create-order/";
	public static final String CREATE_PAYMENT_SESSION_URI = "/payment-management/v1/create-payment-session/";
	public static final String VERIFY_PAYMENT_SESSION_URI = "/payment-management/v1/verify-payment/";

	// File Path Constants
	public static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/config.properties";
	public static final String LOG_FILE_PATH = System.getProperty("user.dir") + "/log.txt";
}