package com.photon.phresco.selenium.util;

public class Constants {

	/**
	 * Execute tests on internet explorer
	 */
	public static final String BROWSER_IE = "iexplore";

	/**
	 * Execute tests on firefox browser
	 */
	public static final String BROWSER_FIREFOX = "firefox";

	/**
	 * Execute tests on googlechrome browser
	 */
	public static final String BROWSER_CHROME = "googlechrome";

	/**
	 * Execute tests on safari browser
	 */
	public static final String BROWSER_SAFARI = "safari";

	/**
	 * Execute tests on opera browser
	 */
	public static final String BROWSER_OPERA = "opera";

	public static String DEFAULT_TIMEOUT = "180000";

	public static String ONE_MINUTE_TIMEOUT = "60000";

	public static String THIRTY_SECOND_TIMEOUT = "30000";

	public static String WINDOWS_OS = "Windows";

	public static String LINUX_OS = "Linux";

	public static String MAC_OS = "Mac";

	public static String DIRECTORY = "/chromedriver";

	public static String WINDOWS_DIRECTORY = DIRECTORY + "/windows";

	public static String LINUX_DIRECTORY_32 = DIRECTORY + "/linux-32";

	public static String MAC_DIRECTORY = DIRECTORY + "/mac";

	public static String LINUX_DIRECTORY_64 = DIRECTORY + "/linux-64";

	public static void setDefaultTimeout(String timeout) {
		int parseInt = Integer.parseInt(timeout);
		if (parseInt > 0) {
			DEFAULT_TIMEOUT = timeout;
		}
	}
}
