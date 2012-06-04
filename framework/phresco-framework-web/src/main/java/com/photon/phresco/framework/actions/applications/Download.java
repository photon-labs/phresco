package com.photon.phresco.framework.actions.applications;

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.LogErrorReport;

public class Download extends FrameworkBaseAction {

    private static final long serialVersionUID = -4735573440570585624L;
    private static final Logger S_LOGGER = Logger.getLogger(Applications.class);
    private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
    
    public String list() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entering Method Download.list()");
    	}
    	
    	try {
			ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
			getHttpRequest().setAttribute(REQ_SERVER_DOWNLOAD_INFO, administrator.getServerDownloadInfo());
			getHttpRequest().setAttribute(REQ_DB_DOWNLOAD_INFO, administrator.getDbDownloadInfo());
			getHttpRequest().setAttribute(REQ_EDITOR_DOWNLOAD_INFO, administrator.getEditorDownloadInfo());
		} catch (PhrescoException e) {
			new LogErrorReport(e, "Listing downloads");
		}
    	
        return APP_DOWNLOAD;
    }
}
