package com.mishipay.test.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;

import org.json.JSONObject;

public class Util {

	public static void clearFilewithoutDeleting(String filePath) {
		FileWriter file = null;
		PrintWriter print = null;
		try {
			file = new FileWriter(filePath, false);
			print = new PrintWriter(file, false);
			print.flush();
			print.close();
			file.close();
		} catch (IOException e) {
			Log.log("Error while clearing the file data : " + filePath);
			e.printStackTrace();
		}
	}

	public static String getRandomString() {
		int leftLimit = 97; // 'a'
		int rightLimit = 122; // 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String RandomString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return RandomString;
	}

	public static FileReader fileReader(String path) {
		FileReader reader = null;
		try {
			reader = new FileReader(path);
		} catch (FileNotFoundException e) {
			Log.log("Error while reading File !");
			e.printStackTrace();
		}
		return reader;
	}

	public static Properties propertyReader(String path) {
		FileReader file = fileReader(path);
		Properties prop = new Properties();
		try {
			prop.load(file);
		} catch (IOException e) {
			Log.log("Error while reading properties !");
			e.printStackTrace();
		}
		return prop;
	}

	public static String getPropertyFromPropertiesfile(String path, String key) {
		Properties prop = propertyReader(path);
		String value = prop.getProperty(key);
		return value;
	}

	public static String getBasketId(JSONObject json) {
		return json.getJSONObject("responseMessage").getJSONObject("data").getString("basket_id");
	}

}