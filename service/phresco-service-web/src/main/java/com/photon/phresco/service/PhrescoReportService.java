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
		List<Reports> javaReports = new ArrayList<Reports>();
		javaReports.add(Reports.JAVADOC);
		javaReports.add(Reports.COBERTURA);
		javaReports.add(Reports.JDEPEND);
		javaReports.add(Reports.JXR);
		javaReports.add(Reports.PMD);
		javaReports.add(Reports.SUREFIRE_REPORT);
		siteReport.put(TechnologyTypes.PHP, javaReports);
		
		List<Reports> phpReports = new ArrayList<Reports>();
		phpReports.add(Reports.JDEPEND);
		phpReports.add(Reports.JXR);
		siteReport.put(TechnologyTypes.PHP, phpReports);
		
		siteReport.put(TechnologyTypes.PHP_DRUPAL6, javaReports);
		siteReport.put(TechnologyTypes.PHP_DRUPAL7, javaReports);
		siteReport.put(TechnologyTypes.JAVA, javaReports);
		siteReport.put(TechnologyTypes.JAVA_STANDALONE, javaReports);
		siteReport.put(TechnologyTypes.NODE_JS_WEBSERVICE, javaReports);
		siteReport.put(TechnologyTypes.SHAREPOINT, javaReports);
		siteReport.put(TechnologyTypes.HTML5, javaReports);
		siteReport.put(TechnologyTypes.HTML5_MOBILE_WIDGET, javaReports);
		siteReport.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, javaReports);
		siteReport.put(TechnologyTypes.HTML5_WIDGET, javaReports);
		siteReport.put(TechnologyTypes.WORDPRESS, javaReports);
		siteReport.put(TechnologyTypes.IPHONE_HYBRID, javaReports);
		siteReport.put(TechnologyTypes.IPHONE_NATIVE, javaReports);
		siteReport.put(TechnologyTypes.ANDROID_HYBRID, javaReports);
		siteReport.put(TechnologyTypes.ANDROID_NATIVE, javaReports);
	}
}
