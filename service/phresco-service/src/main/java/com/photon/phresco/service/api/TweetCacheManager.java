package com.photon.phresco.service.api;


public interface TweetCacheManager {
	
	/**
     * Get the twitter message and save it to the cache.
     * 
     */ 
	public void cacheTweetMessage(); 
	
	/**
     * Returns twitter message from the cache.
     * @return twitter message
     * 
     */
	public String getTweetMessageFromCache();

	
}
