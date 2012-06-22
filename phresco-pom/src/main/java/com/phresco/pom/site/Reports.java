package com.phresco.pom.site;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class Reports implements Serializable{
	
	private String displayName = "";
	
	private String artifactId ="";
	
	private String groupId = "";
	
	private String version = "";
	
	private boolean enabled=false;
	

	public Reports(String displayName, String artifactId, String groupId,
			String version) {
		super();
		this.displayName = displayName;
		this.artifactId = artifactId;
		this.groupId = groupId;
		this.version = version;
	}
	

	/**
	 * @param reportCategories the reportCategories to set
	 */
	private void setReportCategories(List<ReportCategories> reportCategories) {
		this.reportCategories = reportCategories;
	}

	private List<ReportCategories> reportCategories;
	
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the reportCategories
	 */
	public List<ReportCategories> getReportCategories() {
		return reportCategories;
	}

	public Reports() {
		super();
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getVersion() {
		return version;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public static final Reports SUREFIRE_REPORT = new Reports(
			SiteMessages.getString("SiteConstants.surefireReport.display.name") ,
			SiteMessages.getString("SiteConstants.surefireReport.group.id"), 
			SiteMessages.getString("SiteConstants.surefireReport.artifact.id"), 
			SiteMessages.getString("SiteConstants.surefireReport.version")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$


	public static final Reports JAVADOC = new Reports(SiteMessages.getString("SiteConstants.javadoc.display.name"), 
			SiteMessages.getString("SiteConstants.javadoc.artifact.id"),
			SiteMessages.getString("SiteConstants.javadoc.group.id"),
			SiteMessages.getString("SiteConstants.javadoc.version"));	
	
	public static final Reports JDEPEND = new Reports(SiteMessages.getString("SiteConstants.jdepend.display.name"), 
			SiteMessages.getString("SiteConstants.jdepend.artifact.id"),
			SiteMessages.getString("SiteConstants.jdepend.group.id"),
			SiteMessages.getString("SiteConstants.jdepend.version"));	
	
	public static final Reports PMD = new Reports(SiteMessages.getString("SiteConstants.pmd.display.name"), 
			SiteMessages.getString("SiteConstants.pmd.artifact.id"),
			SiteMessages.getString("SiteConstants.pmd.group.id"),
			SiteMessages.getString("SiteConstants.pmd.version"));	
	
	public static final Reports COBERTURA = new Reports(SiteMessages.getString("SiteConstants.cobertura.display.name"), 
			SiteMessages.getString("SiteConstants.cobertura.artifact.id"),
			SiteMessages.getString("SiteConstants.cobertura.group.id"),
			SiteMessages.getString("SiteConstants.cobertura.version"));	
	
	public static final Reports LINK_CHECK = new Reports(SiteMessages.getString("SiteConstants.linkcheck.display.name"), 
			SiteMessages.getString("SiteConstants.linkcheck.artifact.id"),
			SiteMessages.getString("SiteConstants.linkcheck.group.id"),
			SiteMessages.getString("SiteConstants.linkcheck.version"));	
	
	public static final Reports JXR = new Reports(SiteMessages.getString("SiteConstants.jxr.display.name"), 
			SiteMessages.getString("SiteConstants.jxr.artifact.id"),
			SiteMessages.getString("SiteConstants.jxr.group.id"),
			SiteMessages.getString("SiteConstants.jxr.version"));
	
	public static final Reports PROJECT_INFO = new Reports(SiteMessages.getString("SiteConstants.project-info.display.name"), 
			SiteMessages.getString("SiteConstants.project-info.artifact.id"),
			SiteMessages.getString("SiteConstants.project-info.group.id"),
			SiteMessages.getString("SiteConstants.project-info.version"));
	static {
		List<ReportCategories> catagories = new ArrayList<ReportCategories>();
		catagories.add(ReportCategories.INFO_INDEX);
		catagories.add(ReportCategories.INFO_MODULE);
		catagories.add(ReportCategories.INFO_DEPENDENCIES);
		PROJECT_INFO.setReportCategories(catagories);
	}
}