/*
 * ###
 * Service Web Archive
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.service.rest.api;

import java.util.Timer;
import java.util.TimerTask;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.service.api.TweetCacheManager;
import com.sun.jersey.api.json.JSONWithPadding;

@Path(ServerConstants.REST_API_TWEETS)
public class Tweets implements ServerConstants {

	private static boolean isServerStarted = false;

	@GET
	@Produces("application/javascript")
	public JSONWithPadding testJSONP(@QueryParam("callback") String callback) throws JSONException{
		if(!isServerStarted){
			startScheduler(60);
		}
		TweetCacheManager tcm = PhrescoServerFactory.getTweetCacheManager();
		String tweetMessage = tcm.getTweetMessageFromCache();
		JSONArray uriArray = new JSONArray(tweetMessage);

		return new JSONWithPadding(uriArray, callback);
	}
	
	private static void startScheduler(int seconds) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				TweetCacheManager tcm = PhrescoServerFactory.getTweetCacheManager();
				tcm.cacheTweetMessage();
			}
		}, 0, seconds * 1000);
		isServerStarted = true;
	}
}