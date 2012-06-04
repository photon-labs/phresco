package com.photon.phresco.service.rest.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.photon.phresco.commons.model.Tweet;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.data.api.AdminManager;
import com.photon.phresco.service.model.ServerConstants;

@Path(ServerConstants.REST_API_TWEETS)
public class Tweets implements ServerConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Tweets.class);
//	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	
	private static AdminManager adminManager = PhrescoServerFactory.getAdminManager();
	private static List<Tweet> tweets = new ArrayList<Tweet>(10);
	
	static {
		try {
			createTweet();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tweet> list(@Context HttpServletRequest request, 
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit, 
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {
		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
        return tweets;
	}
	
	/**
	 * Deletes all the tweets in the db
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<Tweet> tweet) throws PhrescoException {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tweet> update(List<Tweet> tweet) throws PhrescoException {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}
	
	private static void createTweet() throws TwitterException {
		Twitter twitter = new TwitterFactory().getInstance();
		ResponseList<Status> userTimeline = twitter.getUserTimeline("photoninfotech");		
		for (Status status : userTimeline) {
			Tweet tweet = new Tweet();
			tweet.setId(String.valueOf(status.getId()));
			tweet.setMessage(status.getText());
			tweets.add(tweet);
	    }
   }
}