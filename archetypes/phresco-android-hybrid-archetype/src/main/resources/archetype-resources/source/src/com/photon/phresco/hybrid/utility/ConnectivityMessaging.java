package com.photon.phresco.hybrid.utility;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityMessaging {
	public static boolean checkURLStatus(final String url){
	boolean statusFlag = false;
	HttpClient httpclient = new DefaultHttpClient();
    // Prepare a request object
    HttpGet httpget = new HttpGet(url); 
    // Execute the request
    HttpResponse response;
        try {
			response = httpclient.execute(httpget);
		 int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 200){
				statusFlag = true;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return statusFlag;
	}

	public static boolean checkNetworkConnectivity(final Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else if (netInfo != null
				&& (netInfo.getState() == NetworkInfo.State.DISCONNECTED
						|| netInfo.getState() == NetworkInfo.State.DISCONNECTING
						|| netInfo.getState() == NetworkInfo.State.SUSPENDED || netInfo
						.getState() == NetworkInfo.State.UNKNOWN)) {
			return false;
		} else {
			return false;
		}
	}

	public static void showNetworkConectivityAlert(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setCancelable(true);
		builder.setTitle("Network not available");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				activity.finish();
			}
		});
		builder.show();
	}

	public static void showServiceAlert(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setCancelable(true);
		builder.setTitle("Service unavailable");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				activity.finish();
			}
		});
		builder.show();
	}


}
