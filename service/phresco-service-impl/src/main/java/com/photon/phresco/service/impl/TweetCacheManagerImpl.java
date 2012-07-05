package com.photon.phresco.service.impl;

import org.apache.commons.lang.StringUtils;

import com.photon.phresco.service.api.TweetCacheManager;
import com.photon.phresco.service.model.ServerConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;

public class TweetCacheManagerImpl implements TweetCacheManager {

	private static final String key = "tweet";
	//private static final String Default = "[{\"error\":\"Rate limit exceeded. Clients may not make more than 150 requests per hour.\",\"request\":\"\\/statuses\\/user_timeline\\/photoninfotech.json\"}]";
	private static final String defaultTweetMsg = "[{}]";
	private static final Logger S_LOGGER = Logger.getLogger(TweetCacheManagerImpl.class);
	private EHCacheManager manager;
	private ServerConfiguration config = null;

	public TweetCacheManagerImpl(ServerConfiguration config) {
		this.config = config;
		manager = new EHCacheManager();
		manager.addWidget(key, new Widget(defaultTweetMsg));
		cacheTweetMessage();
	}

	// Put the tweet message into the cache.
	public void cacheTweetMessage() {
		String responseString = getTweetMessageFromService();
		if (StringUtils.isNotEmpty(responseString)&& !responseString.equalsIgnoreCase(defaultTweetMsg)) {
			manager.addWidget(key, new Widget(responseString));
		}
	}

	// Get the tweet message from the cache.
	public String getTweetMessageFromCache() {
		String value = manager.getWidget(key).getName();
		return value;
	}

	// Get News Update from the twitter
	private String getTweetMessageFromService() {
		String twitterMessage = defaultTweetMsg;
		try {
//			Client client = Client.create();
//			//TODO: Read the twitter service url from server.config
//			WebResource webResource = client.resource(config.getTwitterServiceURL());
//			twitterMessage = webResource.get(String.class);
		} catch (java.lang.Exception e) {
			S_LOGGER.error("Error : " + e.getMessage());
		}
		return twitterMessage;
	}

}
