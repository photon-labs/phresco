package com.photon.phresco.hybrid.config;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.AssetManager;
import android.content.res.Resources;

import com.photon.phresco.hybrid.logger.PhrescoLogger;
import com.photon.phresco.hybrid.utility.Constants;

public class EnvConstuctor {

	private static final String TAG = "EnvConstuctor ******* ";
	private static final String ENV_PROD = "Production";
	private static final String SERVER = "Server";
	private static final String WEB_SERVICE = "WebService";
	private ConfigReader confReaderObj;

	public EnvConstuctor(Resources resources) {
		try {
			AssetManager assetManager = resources.getAssets();
			InputStream inputStream = assetManager.open(Constants.PHRESCO_ENV_CONFIG);
			confReaderObj = new ConfigReader(inputStream);
			PhrescoLogger.info(TAG + " EnvConstuctor -  Exception ");
		} catch (IOException e) {
			PhrescoLogger.info(TAG + " EnvConstuctor -  Exception " + e.toString());
			PhrescoLogger.warning(e);
		} catch (Exception e) {
			PhrescoLogger.info(TAG + " EnvConstuctor -  Exception " + e.toString());
			PhrescoLogger.warning(e);
		}
	}

	public String getWebServiceURL(String configName) {
		String configJsonString = confReaderObj.getConfigAsJSON(ENV_PROD, WEB_SERVICE, configName);
		StringBuilder stringBuilder = new StringBuilder();
		try {
			JSONObject jsonObject = new JSONObject(configJsonString);
			stringBuilder.append(jsonObject.getString("protocol"));
			stringBuilder.append("://");
			stringBuilder.append(jsonObject.getString("host"));
			stringBuilder.append(":");
			stringBuilder.append(jsonObject.getString("port"));
			stringBuilder.append("/");
			stringBuilder.append(jsonObject.getString("context"));
			stringBuilder.append("/");
		} catch (JSONException e) {
			PhrescoLogger.info(TAG + " EnvConstuctor -  Exception " + e.toString());
			PhrescoLogger.warning(e);
		}
		return stringBuilder.toString();
	}

	public String getServerURL(String configName) {
		StringBuilder stringBuilder = new StringBuilder();
		String configJsonString = confReaderObj.getConfigAsJSON(ENV_PROD, SERVER, configName);
		try {
			JSONObject jsonObject = new JSONObject(configJsonString);
			stringBuilder.append(jsonObject.getString("protocol"));
			stringBuilder.append("://");
			stringBuilder.append(jsonObject.getString("host"));
			stringBuilder.append(":");
			stringBuilder.append(jsonObject.getString("port"));
			stringBuilder.append("/");
			stringBuilder.append(jsonObject.getString("context"));
			stringBuilder.append("/");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			PhrescoLogger.info(TAG + " EnvConstuctor -  Exception " + e.toString());
			PhrescoLogger.warning(e);
		}
		return stringBuilder.toString();
	}
}
