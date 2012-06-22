package com.phresco.pom.site;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SiteMessages {
	
	private static final String BUNDLE_NAME = "com.phresco.pom.site.siteMessages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private SiteMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}