package com.photon.phresco.uiconstants;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


public class TestProperties {

	public static Properties properties = new Properties();
	private static TestProperties theInstance;
	private String COM_PHOTON_PHRESCO_CONFIG_PROPERTIES = "config.ini";

	public static TestProperties getInstance() throws IOException {
		if (null == theInstance)
			theInstance = new TestProperties();
		return theInstance;
	}

	void loadProperties(String paramString) throws IOException {		
		InputStream is = null;
		try {
			URL url = ClassLoader.getSystemResource(paramString);
			is = url.openStream();
			properties.load(url.openStream());
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		TestProperties instance = TestProperties.getInstance();
		instance.loadPhrescoConfigProperties();
		System.out.println(TestProperties.properties.getProperty("PHOTON_PHRESCO_URL"));
	}

	public void loadPhrescoConfigProperties() throws IOException {
		loadProperties(COM_PHOTON_PHRESCO_CONFIG_PROPERTIES);
	}

	public void loadAllProperties() throws IOException {
		loadProperties(COM_PHOTON_PHRESCO_CONFIG_PROPERTIES);

	}

	public String getValue(String paramString) {
		String str = properties.getProperty(paramString);
		if (str == null)
			throw new RuntimeException("No property found with key "
					+ paramString);
		return str;
	}

	public void setValue(String paramString1, String paramString2) {
		properties.setProperty(paramString1, paramString2);
	}
}