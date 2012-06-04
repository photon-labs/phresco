/*
 * ###
 * Framework Web Archive
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
package com.photon.phresco.framework.actions;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkActions;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.LogErrorReport;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Model.Modules;
import com.phresco.pom.util.PomProcessor;

public class FrameworkBaseAction extends ActionSupport implements FrameworkConstants, FrameworkActions {
	private static final Logger S_LOGGER = Logger.getLogger(FrameworkBaseAction.class);
	private static Boolean debugEnabled = S_LOGGER.isDebugEnabled();

    private static final long serialVersionUID = 1L;
    private ProjectAdministrator projectAdministrator = null;
    private String path = null;
    private String copyToClipboard = null;
    
    public ProjectAdministrator getProjectAdministrator() throws PhrescoException {
        if(projectAdministrator == null) {
            projectAdministrator = PhrescoFrameworkFactory.getProjectAdministrator();
        }
        
        return projectAdministrator;
    }
    
    public HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    }
    
    public HttpSession getHttpSession() {
        return getHttpRequest().getSession();
    }
    
    public void openFolder() {
		if (debugEnabled) {
			S_LOGGER.debug("Entered FrameworkBaseAction.openFolder()");
		}
		try {
			if (Desktop.isDesktopSupported()) {
				File dir = new File(Utility.getProjectHome() + path);
				if(dir.exists()){
	    		Desktop.getDesktop().open(new File(Utility.getProjectHome() + path));
				}else{
					Desktop.getDesktop().open(new File(Utility.getProjectHome()));
				}
	    	}
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Unable to open the Path, " + FrameworkUtil.getStackTraceAsString(e));
				new LogErrorReport(e, "Open Folder");
			}
		}
	}
    
    public void copyPath() {
    	if (debugEnabled) {
    		S_LOGGER.debug("Entered FrameworkBaseAction.copyPath");
    	}
    	File dir = new File(Utility.getProjectHome() + path);
    	if(dir.exists()){
    		copyToClipboard = Utility.getProjectHome() + path;
    	}else{
    		copyToClipboard = Utility.getProjectHome();
    	}
    	copyToClipboard ();
    }
    
    public void copyToClipboard () {
    	S_LOGGER.debug("Entered FrameworkBaseAction.copyToClipboard");
    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	clipboard.setContents(new StringSelection(copyToClipboard), null);
    }
    
    protected List<String> getProjectModules(String projectCode) {
    	try {
            StringBuilder builder = getProjectHome(projectCode);
            builder.append(File.separatorChar);
            builder.append(POM_XML);
    		File pomPath = new File(builder.toString());
    		PomProcessor processor = new PomProcessor(pomPath);
    		Modules pomModule = processor.getPomModule();
    		if (pomModule != null) {
    			return pomModule.getModule();
    		}
    	} catch (JAXBException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (PhrescoPomException e) {
    		e.printStackTrace();
    	}
    	return null;
    }

	private StringBuilder getProjectHome(String projectCode) {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(projectCode);
		return builder;
	}
    
    protected List<String> getWarProjectModules(String projectCode) throws JAXBException, IOException, ArrayIndexOutOfBoundsException, PhrescoPomException {
    	List<String> projectModules = getProjectModules(projectCode);
    	List<String> warModules = new ArrayList<String>(projectModules.size());
    	for (String projectModule : projectModules) {
    		StringBuilder pathBuilder = getProjectHome(projectCode);
    		pathBuilder.append(File.separatorChar);
    		pathBuilder.append(projectModule);
    		pathBuilder.append(File.separatorChar);
    		pathBuilder.append(POM_XML);
    		PomProcessor processor = new PomProcessor(new File(pathBuilder.toString()));
    		String packaging = processor.getModel().getPackaging();
			if (StringUtils.isNotEmpty(packaging) && WAR.equalsIgnoreCase(packaging)) {
    			warModules.add(projectModule);
    		}
		}
    	return warModules;
    }

    public String getPath() {
    	return path;
    }

    public void setPath(String path) {
    	this.path = path;
    }

	public String getCopyToClipboard() {
		return copyToClipboard;
	}

	public void setCopyToClipboard(String copyToClipboard) {
		this.copyToClipboard = copyToClipboard;
	}

}
