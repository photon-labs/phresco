package com.phresco.pom.site;

public class SiteConstants {
	
	private String displayName = "";
	
	private String artifactId ="";
	
	private String groupId = "";
	
	private String version = "";
	
	private SiteConstants(String displayName, String artifactId, String groupId,
			String version) {
		super();
		this.displayName = displayName;
		this.artifactId = artifactId;
		this.groupId = groupId;
		this.version = version;
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

	public static final SiteConstants SUREFIRE_REPORT = new SiteConstants(
			SiteMessages.getString("SiteConstants.surefireReport.display.name") ,
			SiteMessages.getString("SiteConstants.surefireReport.group.id"), 
			SiteMessages.getString("SiteConstants.surefireReport.artifact.id"), 
			SiteMessages.getString("SiteConstants.surefireReport.version")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$


	public static final SiteConstants JAVADOC = new SiteConstants(SiteMessages.getString("SiteConstants.javadoc.display.name"), 
			SiteMessages.getString("SiteConstants.javadoc.artifact.id"),
			SiteMessages.getString("SiteConstants.javadoc.group.id"),
			SiteMessages.getString("SiteConstants.javadoc.version"));	
	
	public static final SiteConstants JDEPEND = new SiteConstants(SiteMessages.getString("SiteConstants.jdepend.display.name"), 
			SiteMessages.getString("SiteConstants.jdepend.artifact.id"),
			SiteMessages.getString("SiteConstants.jdepend.group.id"),
			SiteMessages.getString("SiteConstants.jdepend.version"));	
	
	public static final SiteConstants PMD = new SiteConstants(SiteMessages.getString("SiteConstants.pmd.display.name"), 
			SiteMessages.getString("SiteConstants.pmd.artifact.id"),
			SiteMessages.getString("SiteConstants.pmd.group.id"),
			SiteMessages.getString("SiteConstants.pmd.version"));	
	
	public static final SiteConstants COBERTURA = new SiteConstants(SiteMessages.getString("SiteConstants.cobertura.display.name"), 
			SiteMessages.getString("SiteConstants.cobertura.artifact.id"),
			SiteMessages.getString("SiteConstants.cobertura.group.id"),
			SiteMessages.getString("SiteConstants.cobertura.version"));	
	
	public static final SiteConstants LINK_CHECK = new SiteConstants(SiteMessages.getString("SiteConstants.linkcheck.display.name"), 
			SiteMessages.getString("SiteConstants.linkcheck.artifact.id"),
			SiteMessages.getString("SiteConstants.linkcheck.group.id"),
			SiteMessages.getString("SiteConstants.linkcheck.version"));	
	
	public static final SiteConstants JXR = new SiteConstants(SiteMessages.getString("SiteConstants.jxr.display.name"), 
			SiteMessages.getString("SiteConstants.jxr.artifact.id"),
			SiteMessages.getString("SiteConstants.jxr.group.id"),
			SiteMessages.getString("SiteConstants.jxr.version"));	
}
