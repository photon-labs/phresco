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
package com.photon.phresco.framework.actions.applications;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.util.Utility;

public class ImgStreaming extends FrameworkBaseAction implements FrameworkConstants {
	
	private static final Logger S_LOGGER = Logger.getLogger(ImgStreaming.class);
    private static Boolean debugEnabled  =S_LOGGER.isDebugEnabled();
    private String projectCode = null;
    // screen shot dispaly
    private byte[] imageInBytes = null; 
    private String contentType = null; 
    private String contentDisposition = null; 
    private int contentLength; 
    private int bufferSize; 
    
	public void getScreenshot() {
        if (debugEnabled) {
            S_LOGGER.debug("Entering Method Quality.screenshotImageDisplay()");
        }
    	try {
	    	String testCaseName = getHttpRequest().getParameter(REQ_TESTCASE_NAME);
	    	String screenshotImgSrc = getScreenshotPath(testCaseName);
			imageInBytes = imageStream(screenshotImgSrc);
	    	contentType = CONTENT_TYPE; 
	    	contentDisposition = CONTENT_DISPOSITION; 
	    	contentLength = imageInBytes.length; 
	    	bufferSize = 1024;
	    	
			HttpServletResponse response = ServletActionContext.getResponse();
	        response.setContentType(getContentType());
			response.setContentLength(getContentLength());
			response.getOutputStream().write(getImageInBytes());
			response.getOutputStream().flush();
	    	
    	} catch (Exception e) {
            if (debugEnabled) {
                S_LOGGER.error("Entered into catch block of Quality.screenshotImageDisplay()"+ e);
            }
		}
	}
	
    private byte[] imageStream(String imageFile) throws PhrescoException, MalformedURLException, IOException {
        if (debugEnabled) {
            S_LOGGER.debug("Entering Method Quality.imageStream()");
        }
        BufferedImage image = ImageIO.read(new File(imageFile));
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ImageIO.write(image, IMG_PNG_TYPE, b);
        byte[] jpgByteArray = b.toByteArray();
        return jpgByteArray;
    }
    
    private String getScreenshotPath(String testCaseName)  throws PhrescoException {
        if (debugEnabled) {
            S_LOGGER.debug("Entering Method Quality.getScreenShotPath()");
         }
        FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
        ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
        Project project = administrator.getProject(projectCode);
    	StringBuilder sbuilder = new StringBuilder();
    	sbuilder.append(Utility.getProjectHome());
    	sbuilder.append(project.getProjectInfo().getCode());
    	sbuilder.append(frameworkUtil.getFunctionalReportDir(project.getProjectInfo().getTechnology().getId()));
    	sbuilder.append(File.separator);
    	sbuilder.append(SCREENSHOT_DIR);
    	sbuilder.append(File.separator);
    	sbuilder.append(testCaseName);
    	sbuilder.append(DOT + IMG_PNG_TYPE);
    	return sbuilder.toString();
    }
    
	public byte[] getImageInBytes() {
		return imageInBytes;
	}

	public void setImageInBytes(byte[] imageInBytes) {
		this.imageInBytes = imageInBytes;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}


	public String getProjectCode() {
		return projectCode;
	}


	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}
