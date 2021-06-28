package com.mishipay.runner;

import com.mishipay.test.helper.EndToEndHelper;
import com.mishipay.test.util.ConstantURIs;
import com.mishipay.test.util.Util;

public class Runner extends Thread {

	EndToEndHelper end = null;
	static int usersPerSecond = Integer
			.parseInt(Util.getPropertyFromPropertiesfile(ConstantURIs.CONFIG_FILE_PATH, "usersPerSecond"));

	public static void main(String[] args) {
		Util.clearFilewithoutDeleting(ConstantURIs.LOG_FILE_PATH);

		Runner thread = null;
		for (int i = 0; i < usersPerSecond; i++) {
			thread = new Runner();
			thread.start();
		}
	}

	public void run() {
		end = new EndToEndHelper();
		end.endToEnd();
	}

}