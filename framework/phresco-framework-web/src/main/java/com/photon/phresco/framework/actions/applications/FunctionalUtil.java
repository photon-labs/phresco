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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.util.Utility;

public class FunctionalUtil {
	private static final Logger S_LOGGER = Logger.getLogger(FunctionalUtil.class);
	private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
    
    public static void adaptTestConfig(Project project, String envName, String browser) throws PhrescoException {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method DrupalFunctionalUtil.adaptTestConfig(Project project, SettingsInfo serverDetails, String browser)");
		}
    	if (debugEnabled) {
			S_LOGGER.debug("adaptTestConfig() ProjectInfo = "+project.getProjectInfo());
		}
        InputStream is = null;
        OutputStream os = null;
        StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        builder.append(project.getProjectInfo().getCode());
        builder.append(FrameworkUtil.getInstance().getFuncitonalAdaptDir(project.getProjectInfo().getTechnology().getId()));
     
        try {
		    File file = new File(builder.toString());
        	if(file.exists()){
               ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
               administrator.updateTestConfiguration(project, envName, browser, builder.toString());
			}
        } catch (Exception e) {
            throw new PhrescoException(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
