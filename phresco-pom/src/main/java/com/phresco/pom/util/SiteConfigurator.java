/*
 * ###
 * Phresco Pom
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
package com.phresco.pom.util;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.phresco.pom.model.ReportPlugin;
import com.phresco.pom.site.SiteConstants;

/**
 * @author suresh_ma
 *
 */
public class SiteConfigurator {

	/**
	 * @param constants
	 */
	public void addReportPlugin(SiteConstants constants,File file) {
		try {
			PomProcessor processor = new PomProcessor(file);
			ReportPlugin reportPlugin = new ReportPlugin();
			reportPlugin.setGroupId(constants.getGroupId());
			reportPlugin.setArtifactId(constants.getArtifactId());
			reportPlugin.setVersion(constants.getVersion());
			processor.siteConfig(reportPlugin);
			processor.save();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param constants
	 */
	public void addReportPlugin(SiteConstants[] constants,File file){
		for (SiteConstants siteConstants : constants) {
			addReportPlugin(siteConstants,file);
		}
	}
	
	/**
	 * @param constants
	 */
	public void removeReportPlugin(SiteConstants[] constants,File file){
		
		try {
			PomProcessor processor = new PomProcessor(file);
			for (SiteConstants siteConstants : constants) {
				String groupId = siteConstants.getGroupId();
				String artifactId = siteConstants.getArtifactId();
				processor.removeSitePlugin(groupId,artifactId);
				processor.save();
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
