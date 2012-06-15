package com.photon.phresco.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.JsonSerializer;
import com.photon.phresco.service.tweet.TweetCacheManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import com.sun.jersey.api.json.JSONWithPadding;

/**
 * Phresco Service Class hosted at the URI path "/news"
 */

@Path("/news")
public class TweeterService {

	private static boolean isServerStarted = false;

	public TweeterService() {
	}

	public static void startScheduler(int seconds) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				TweetCacheManager tcm = TweetCacheManager
						.getTweetCacheManager();
				tcm.cacheTweetMessage();
				System.out.println("Caching ...");
			}
		}, 0, seconds * 1000);
		isServerStarted = true;
	}

	@GET
	@Path("/jsonp")
	@Produces("application/javascript")
	public JSONWithPadding testJSONP(@QueryParam("callback") String callback) throws JSONException{
		System.out.println("Inside text news");
		if(!isServerStarted){
			startScheduler(60);
			System.out.println("Caching scheduler started");
		}
		TweetCacheManager tcm = TweetCacheManager.getTweetCacheManager();
		String tweetMessage = tcm.getTweetMessageFromCache();
		JSONArray uriArray = new JSONArray(tweetMessage);

		return new JSONWithPadding(uriArray, callback);
	} 
}