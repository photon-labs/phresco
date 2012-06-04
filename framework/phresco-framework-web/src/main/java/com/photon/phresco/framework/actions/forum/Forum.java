package com.photon.phresco.framework.actions.forum;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.util.Credentials;

public class Forum extends FrameworkBaseAction {
	private static final long serialVersionUID = -4282767788002019870L; 
	
	private static final Logger S_LOGGER 	= Logger.getLogger(Forum.class);
	private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
	
	public String forum() {
		if (S_LOGGER.isDebugEnabled())S_LOGGER.debug("entered forumIndex()");
		
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method Forum.forum()");
		}
		
		try {
			
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			String serviceUrl= administrator.getJforumPath();
			
			UserInfo sessionUserInfo = (UserInfo)getHttpSession().getAttribute(REQ_USER_INFO);
			Credentials credentials = sessionUserInfo.getCredentials();
			
			String username = credentials.getUsername();
			byte[] usernameEncode = Base64.encodeBase64(username.getBytes());
			String encodedUsername = new String(usernameEncode);
			
			String password = credentials.getPassword();
			
			getHttpRequest().setAttribute(REQ_USER_NAME, encodedUsername);
			getHttpRequest().setAttribute(REQ_PASSWORD, password);
			
			URL sonarURL = new URL(serviceUrl);
			HttpURLConnection connection = (HttpURLConnection) sonarURL.openConnection();
			int responseCode = connection.getResponseCode();
			if(responseCode != 200) {
			    getHttpRequest().setAttribute(REQ_ERROR, "Help is not available");
			    return HELP;
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append(serviceUrl);
			sb.append(JFORUM_PARAMETER_URL);
			sb.append(JFORUM_USERNAME);
			sb.append(encodedUsername);
			sb.append(JFORUM_PASSWORD);
			sb.append(password);
			
			getHttpRequest().setAttribute(REQ_JFORUM_URL, sb.toString());
			
		} catch (Exception e) {
        	if (debugEnabled) {
        		S_LOGGER.error("Entered into catch block of Forum.forum()" + FrameworkUtil.getStackTraceAsString(e));
    		}
		} 
		return HELP;
	}
}
