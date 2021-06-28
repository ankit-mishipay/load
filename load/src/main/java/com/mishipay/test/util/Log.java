package com.mishipay.test.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Log {

	public static void log(String message) {
		System.out.println(message);
		writeToFile(ConstantURIs.LOG_FILE_PATH, message);
	}

	public static void writeToFile(String filePath, String message) {
		try {
			message = message + "\n";
			Files.write(Paths.get(filePath), message.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
