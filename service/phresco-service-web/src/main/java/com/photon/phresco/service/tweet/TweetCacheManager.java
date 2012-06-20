package com.photon.phresco.service.tweet;

import org.apache.log4j.Logger;

public class TweetCacheManager {

	private static final String key = "tweet";
	private static final String Default = "[{\"error\":\"Rate limit exceeded. Clients may not make more than 150 requests per hour.\",\"request\":\"\\/statuses\\/user_timeline\\/photoninfotech.json\"}]";
	private static final Logger S_LOGGER = Logger
			.getLogger(TweetCacheManager.class);
	private static TweetCacheManager tweetCacheManager;
	private EHCacheManager manager;

	private TweetCacheManager() {
		manager = new EHCacheManager();
		manager.addWidget(key, new Widget(Default));
		cacheTweetMessage();
	}

	public static TweetCacheManager getTweetCacheManager() {
		if (tweetCacheManager == null) {
			tweetCacheManager = new TweetCacheManager();
		}
		return tweetCacheManager;
	}

	// Put the tweet message into the cache.
	public void cacheTweetMessage() {

		String responseString = getTweetMessageFromService();
		if (responseString != null && !"".equals(responseString)) {
			// responseString = responseString.replace("([", "[");
			// responseString = responseString.replace("]);", "]");
			manager.addWidget(key, new Widget(responseString));

		}
	}

	// Get the tweet message from the cache.
	public String getTweetMessageFromCache() {
		String value = manager.getWidget(key).getName();
		return value;
	}

	// Get News Update from the twitter
	public String getTweetMessageFromService() {
		String responseString = "";
		try {
			com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client
					.create();
			com.sun.jersey.api.client.WebResource webResource = client
				.resource("http://twitter.com/statuses/user_timeline/photoninfotech.json");
/*			com.sun.jersey.api.client.WebResource webResource = client
					.resource("http://localhost:2468/phresco/results.json");
*/
			responseString = webResource.get(String.class);

		} catch (java.lang.Exception e) {
			S_LOGGER.error("Error : " + e.getMessage());
		}
		return responseString;
	}

}
