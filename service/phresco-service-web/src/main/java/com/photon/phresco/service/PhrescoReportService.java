/*
 * ###
 * Service Web Archive
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
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
package com.photon.phresco.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.sonatype.aether.resolution.VersionRangeResolutionException;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.util.TechnologyTypes;
import com.phresco.pom.site.Reports;

@Path("/reports")
public class PhrescoReportService implements ServerConstants {
	private static  Map<String, List<Reports>> siteReport = new HashMap<String,List<Reports>>();
	static {
		initializeReportMap();
	}
	
	@GET
	@Path("{techId}")
	@Produces({ MediaType.APPLICATION_JSON})
	public List<Reports> getReportJSON(@PathParam("techId") String techId) throws VersionRangeResolutionException, PhrescoException, JSONException {
		List<Reports> list = siteReport.get(techId);
		return list;
	}
	
	public static void initializeReportMap() {
		List<Reports> allReports = new ArrayList<Reports>();
		allReports.add(Reports.PROJECT_INFO);
		allReports.add(Reports.JAVADOC);
		allReports.add(Reports.COBERTURA);
		allReports.add(Reports.JDEPEND);
		allReports.add(Reports.JXR);
		allReports.add(Reports.PMD);
		allReports.add(Reports.SUREFIRE_REPORT);
		
		siteReport.put(TechnologyTypes.JAVA, allReports);
		siteReport.put(TechnologyTypes.JAVA_STANDALONE, allReports);
		siteReport.put(TechnologyTypes.JAVA_WEBSERVICE, allReports);
		siteReport.put(TechnologyTypes.ANDROID_HYBRID, allReports);
		siteReport.put(TechnologyTypes.ANDROID_NATIVE, allReports);
		siteReport.put(TechnologyTypes.HTML5,allReports);
		siteReport.put(TechnologyTypes.HTML5_MOBILE_WIDGET,allReports);
		siteReport.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET,allReports);
		siteReport.put(TechnologyTypes.HTML5_WIDGET,allReports);
				
		List<Reports> infoandSurefireReports = new ArrayList<Reports>();
		infoandSurefireReports.add(Reports.PROJECT_INFO);
		infoandSurefireReports.add(Reports.SUREFIRE_REPORT);
		siteReport.put(TechnologyTypes.PHP, infoandSurefireReports);
		siteReport.put(TechnologyTypes.PHP_DRUPAL7, infoandSurefireReports);
		siteReport.put(TechnologyTypes.PHP_DRUPAL6, infoandSurefireReports);
		siteReport.put(TechnologyTypes.NODE_JS_WEBSERVICE, infoandSurefireReports);
		siteReport.put(TechnologyTypes.SHAREPOINT, infoandSurefireReports);
		siteReport.put(TechnologyTypes.WORDPRESS, infoandSurefireReports);
		
		
		List<Reports> infoReports = new ArrayList<Reports>();
		infoReports.add(Reports.PROJECT_INFO);
		siteReport.put(TechnologyTypes.IPHONE_NATIVE, infoReports);
		siteReport.put(TechnologyTypes.IPHONE_HYBRID, infoReports);
	}
}
