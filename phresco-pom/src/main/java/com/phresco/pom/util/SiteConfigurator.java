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
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import com.phresco.pom.model.ReportPlugin;
import com.phresco.pom.model.ReportPlugin.ReportSets;
import com.phresco.pom.model.ReportSet;
import com.phresco.pom.site.ReportCategories;
import com.phresco.pom.site.Reports;

/**
 * @author suresh_ma
 *
 */
public class SiteConfigurator {


	/**
	 * 
	 */
	private ReportPlugin reportPlugin = null;
	/**
	 * @param reports
	 * @param file
	 * @return
	 */
	public ReportPlugin addReportPlugin(Reports reports,File file) {
		try {
			PomProcessor processor = new PomProcessor(file);
			reportPlugin = new ReportPlugin();
			reportPlugin.setGroupId(reports.getGroupId());
			reportPlugin.setArtifactId(reports.getArtifactId());
			reportPlugin.setVersion(reports.getVersion());
			processor.siteReportConfig(reportPlugin);
			processor.save();
			return reportPlugin;
		} catch (JAXBException e) {
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * @param reports
	 * @param report
	 * @param file
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void addInfoReportPlugin(List<Reports> reports,File file) throws JAXBException, IOException {

		PomProcessor processor = new PomProcessor(file);
		for (Reports iterateReport : reports) {
			reportPlugin = new ReportPlugin();
			reportPlugin.setGroupId(iterateReport.getGroupId());
			reportPlugin.setArtifactId(iterateReport.getArtifactId());
			reportPlugin.setVersion(iterateReport.getVersion());
			processor.siteReportConfig(reportPlugin);
			ReportSets reportSets = reportPlugin.getReportSets();
			ReportSet reportSet = new ReportSet();
			com.phresco.pom.model.ReportSet.Reports repo = new com.phresco.pom.model.ReportSet.Reports();
			if(reportSets == null){
				reportPlugin.setReportSets(new ReportSets());
				List<ReportCategories> reportCategories = iterateReport.getReportCategories();
				for (ReportCategories reportCategories2 : reportCategories) {
					reportSet.setReports(repo);		
					reportSet.getReports().getReport().add(reportCategories2.getName());
				}
			} reportPlugin.getReportSets().getReportSet().add(reportSet);
			processor.save();
		}
//		reportPlugin = new ReportPlugin();
//		reportPlugin.setGroupId(Reports.PROJECT_INFO.getGroupId());
//		reportPlugin.setArtifactId(Reports.PROJECT_INFO.getArtifactId());
//		reportPlugin.setVersion(Reports.PROJECT_INFO.getVersion());
//		processor.siteReportConfig(reportPlugin);
//		ReportSets reportSets = reportPlugin.getReportSets();
//		ReportSet reportSet = new ReportSet();
//		com.phresco.pom.model.ReportSet.Reports repo = new com.phresco.pom.model.ReportSet.Reports();
//		if(reportSets == null){
//			reportPlugin.setReportSets(new ReportSets());
//			List<ReportCategories> reportCategories = reports[0].getReportCategories();
//			for (ReportCategories reportCategories2 : reportCategories) {
//				reportSet.setReports(repo);		
//				reportSet.getReports().getReport().add(reportCategories2.getName());
//			}
//		} 
//		reportPlugin.getReportSets().getReportSet().add(reportSet);
//		processor.save();
	}

	/**
	 * @param reports
	 * @param file
	 * @return
	 */
	public ReportPlugin addReportPlugin(List<Reports> reports,File file){
		for (Reports iterateReport : reports) {
			return addReportPlugin(iterateReport,file);
		}
		return null;
	}
	
	/**
	 * @param reports
	 * @param file
	 */
	public void removeReportPlugin(List<Reports> reports,File file){

		try {
			PomProcessor processor = new PomProcessor(file);
			for (Reports iterateReports : reports) {
				String groupId = iterateReports.getGroupId();
				String artifactId = iterateReports.getArtifactId();
				processor.removeSitePlugin(groupId,artifactId);
				processor.save();
			}
		} catch (JAXBException e) {
		} catch (IOException e) {
		}
	}
	public static void main(String[] args) throws JAXBException, IOException {
		SiteConfigurator siteConfigurator = new SiteConfigurator();
		List<Reports> reports = new ArrayList<Reports>();
		reports.add(Reports.PROJECT_INFO);
		
		siteConfigurator.addInfoReportPlugin(reports, new File("d:\\pom\\pom.xml"));
	}
}
