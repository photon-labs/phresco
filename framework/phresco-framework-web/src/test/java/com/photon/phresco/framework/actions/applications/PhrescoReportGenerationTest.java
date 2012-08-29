package com.photon.phresco.framework.actions.applications;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.Mode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.SonarReport;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;
import com.phresco.pom.model.Model;
import com.phresco.pom.model.Model.Profiles;
import com.phresco.pom.model.Profile;
import com.phresco.pom.util.PomProcessor;

public class PhrescoReportGenerationTest extends FrameworkBaseAction implements FrameworkConstants  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProjectAdministrator administrator = null;
	private String projectCode = null;
	private String report = null;
	private static String FUNCTIONALTEST = "functional";
	private PhrescoReportGeneration reportGen = new PhrescoReportGeneration();
	
	@Before
	public void setUp() throws Exception {
		System.out.println("before !!!!!!!!!");
		administrator = PhrescoFrameworkFactory.getProjectAdministrator();
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Tear down!!!!!");
	    if (administrator != null) {
	        administrator = null;
	    }
	}
	
//	@Test
	public void readResources() throws IOException {
		InputStream inStream = null;
		OutputStream ps = null;
		try {
//			String resourceName = "framework.config";
			String resourceName = "reports/jasper/report.jasper";
			inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
			ps = System.out;
			byte[] data = new byte[1024];
			while (inStream.read(data) != -1) {
				ps.write(data);
			}
		
		} finally {
			if (inStream != null) {
				inStream.close();
			}

			if (ps != null) {
				ps.close();
			}
		}
	}
	
	//@Test
	public void testPhrescoReportGeneration()  throws Exception  {
		Project project = administrator.getProject("PHR_phpnone");
		System.out.println("project ===> " + project.getProjectInfo().getTechnology().getName());
//		reportGen.compileReports();
//		reportGen.cumulativePdfReport(project, "", "detail");
		
	}
	
	//@Test
	public void sampleReportGenration() throws Exception {
		InputStream is = null;
		try {
			
			System.out.println("Pdf report generation started!!!!!");
			String outFileNamePDF = "/Users/kaleeswaran/Tried/JasperReportExample/samplePdf.pdf";
//			
//			JasperDesign jasperDesign = JRXmlLoader.load("/Users/kaleeswaran/Tried/JasperReportExample/report.jrxml");
//			String desinationPath = "/Users/kaleeswaran/Tried/JasperReportExample/report.jasper";
			
//			JasperCompileManager.compileReportToFile(jasperDesign, desinationPath);

//			String outFileNamePDF = "/Users/kaleeswaran/Tried/JasperReportExample/mavenCompiled/samplePdf.pdf";
//			FileInputStream fis = new FileInputStream("/Users/kaleeswaran/Tried/JasperReportExample/mavenCompiled/report.jasper");						
			
			boolean readFromClasspath = false;
			is = readReportFile(readFromClasspath);
//			BufferedInputStream bufferedInputStream = new BufferedInputStream(is);
			
			Map<String, Object> parameters = new HashMap<String,Object>();
			
			List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();
			for (int i = 0; i < 10; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Name", "Kalees");
				map.put("EmpId", "E3084");
				maps.add(map);
			}
			JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);
//			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
//
//			String resourceName = "reports/jasper/report.jasper";
//			URL resource = this.getClass().getClassLoader().getResource(resourceName);
			
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);

			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter(); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,outFileNamePDF);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.exportReport(); 
			System.out.println("Completed!!!!!!!!!");
			
		} catch (Exception e) {
			System.out.println("Error in report!!!!");
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public void compileReports() throws Exception {
		//jrxml path!!!!!
		File pdfFileDir = new File("/Users/kaleeswaran/work/boston-new/framework/phresco-framework-web/src/main/resources/reports/template");
		File[] listFiles = pdfFileDir.listFiles(new FileExtensionFileFilter(".jrxml"));
		for (File jrxmlFile : listFiles) {
//			System.out.println("jrxmlFile ===> " + jrxmlFile.getName());
			System.out.println("jrxmlFile ===> " + jrxmlFile.getAbsolutePath());
			JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFile.getAbsolutePath());
			String desinationPath = "/Users/kaleeswaran/work/boston-new/framework/phresco-framework-web/src/main/resources/reports/jasper/" + FilenameUtils.removeExtension(jrxmlFile.getName()) + ".jasper";
			System.out.println("desinationPath ====> " + desinationPath);
			JasperCompileManager.compileReportToFile(jasperDesign, desinationPath);
		}
		
	}
	
	private InputStream readReportFile(boolean readFromClasspath) throws IOException {
		if (readFromClasspath) {
			return readFromClasspath();
		}
		
//		FileInputStream fis = new FileInputStream("/Users/kaleeswaran/work/boston-new/framework/phresco-framework-web/src/main/resources/reports/jasper/report.jasper");
//		FileInputStream fis = new FileInputStream("/Users/kaleeswaran/work/boston-new/framework/phresco-framework-web/target/classes/reports/jasper/report.jasper");
		FileInputStream fis = new FileInputStream("/Users/kaleeswaran/softwares/apache-tomcat-7.0.25/webapps/phresco/WEB-INF/classes/reports/jasper/report.jasper");
//		FileInputStream fis = new FileInputStream("/Users/kaleeswaran/Tried/JasperReportExample/report.jasper");
		return fis;
	}

	private InputStream readFromClasspath() throws IOException {
		String resourceName = "reports/jasper/report.jasper";
		return this.getClass().getClassLoader().getResourceAsStream(resourceName);
	}

    
    public void reportGenerationTests(Project project, String testType) throws Exception { 
    	InputStream reportStream = null;
    	try {
    		System.out.println("Pdf report generation started!!!!!");
    		String outFileNamePDF = "/Users/kaleeswaran/Tried/JasperReportExample/samplePdfPhresco.pdf";
//    		JasperDesign jasperDesign = JRXmlLoader.load("/reports/template/report.jrxml");
//    		String desinationPath = "/reports/jasper/report.jasper";
//    		JasperCompileManager.compileReportToFile(jasperDesign, desinationPath);
//    		FileInputStream fis = new FileInputStream("/reports/jasper/report.jasper");
    		
    		boolean readFromClasspath = true;
    		reportStream = readReportFile(readFromClasspath);
    		
    		Map<String, Object> parameters = new HashMap<String,Object>();
    		
    		List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();
    		for (int i = 0; i < 10; i++) {
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("Name", "Kalees");
    			map.put("EmpId", "E3084");
    			maps.add(map);
    		}
    		JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);
    		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
    		
    		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    		JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter(); 
    		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,outFileNamePDF);
    		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		exporter.exportReport(); 
    		System.out.println("Pdf generation completed!!!!!!!!!!");
		} catch (Exception e) {
			System.out.println("Error pdf!!!!!!");
			e.printStackTrace();
		} finally {
			if(reportStream != null) {
				reportStream.close();
			}
		}
    }
    
   
    public void getSonarReports() {
    	try {
        	List<SonarReport> sonarReports = null;
    		sonarReports = new ArrayList<SonarReport>();
    		
    		List<String> sonarTechReports = new ArrayList<String>(4);
    		
          ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
        	Project project = administrator.getProject("PHR_SonarAllReport");
        	projectCode = project.getProjectInfo().getCode();
        	System.out.println(" projectcode ===========>" + projectCode);
        	String techId = project.getProjectInfo().getTechnology().getId();
        	System.out.println(" technology ===========>" + techId);
        	
    		if (TechnologyTypes.HTML5_WIDGET.equals(techId) || TechnologyTypes.HTML5_MOBILE_WIDGET.equals(techId) 
    				|| TechnologyTypes.HTML5.equals(techId) || TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET.equals(techId) 
    				|| TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.equals(techId) || TechnologyTypes.JAVA_WEBSERVICE.equals(techId)) {
    			System.out.println("java. js , web");
    			//sonarTechReports.add("java");
    			//sonarTechReports.add("js");
    			sonarTechReports.add("web");
    		} else {
    		//	System.out.println("src");
    			//sonarTechReports.add("source");
    		}
    		//System.out.println("Functional");
    		//sonarTechReports.add("functional");
    		for (String sonarTechReport : sonarTechReports) {
    			SonarReport srcSonarReport = generateSonarReport(sonarTechReport, techId);
    			if(srcSonarReport != null) {
    				sonarReports.add(srcSonarReport);
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public SonarReport generateSonarReport(String report, String techId) throws Exception {
		SonarReport sonarReport = new SonarReport();
		StringBuilder sb = new StringBuilder();
    	String technology = null;
		try {
			FrameworkConfiguration frameworkConfig = PhrescoFrameworkFactory.getFrameworkConfig();
        	String serverUrl = "";
    	    if (StringUtils.isNotEmpty(frameworkConfig.getSonarUrl())) {
    	    	serverUrl = frameworkConfig.getSonarUrl();
    	    } else {
    	    	serverUrl = getHttpRequest().getRequestURL().toString();
    	    	StringBuilder tobeRemoved = new StringBuilder();
    	    	tobeRemoved.append(getHttpRequest().getContextPath());
    	    	tobeRemoved.append(getHttpRequest().getServletPath());

    	    	Pattern pattern = Pattern.compile(tobeRemoved.toString());
    	    	Matcher matcher = pattern.matcher(serverUrl);
    	    	serverUrl = matcher.replaceAll("");
    	    }
        	StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        	builder.append(projectCode);
        	
            if (StringUtils.isNotEmpty(report) && FUNCTIONALTEST.equals(report)) {
                FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
                builder.append(frameworkUtil.getFuncitonalTestDir(techId));
            }
            
            builder.append(File.separatorChar);
        	builder.append(POM_XML);
        	File pomPath = new File(builder.toString());
        	System.out.println("pomPath path =========" + pomPath);
        	PomProcessor processor = new PomProcessor(pomPath);
        	String groupId = processor.getModel().getGroupId();
        	String artifactId = processor.getModel().getArtifactId();
        	
        	Profile profiles = processor.getProfile("web");
        	System.out.println(" web -----------" + profiles.getId());
        	
        	StringBuilder sbuild = new StringBuilder();
        	sbuild.append(groupId);
        	sbuild.append(COLON);
        	sbuild.append(artifactId);
        	
        	if (StringUtils.isNotEmpty(report) && !SOURCE_DIR.equals(report)) {
        		sbuild.append(COLON);
        		sbuild.append(report);
        	}
        	
        	String artifact = sbuild.toString();
        	System.out.println("report  == " + report);
        	System.out.println("artifact == " + artifact);
			Sonar sonar = new Sonar(new HttpClient4Connector(new Host(serverUrl)));
			
			String metrickey[] = {"ncloc", "lines", "files", "comment_lines_density" , "comment_lines", "duplicated_lines_density", "duplicated_lines", 
					"duplicated_blocks", "duplicated_files", "function_complexity", "file_complexity", "violations_density", "blocker_violations", 
					"critical_violations", "major_violations", "minor_violations", "info_violations", "weighted_violations",
					"classes", "functions",
					"statements","packages", "accessors", "public_documented_api_density", "public_undocumented_api","package_tangle_index","package_cycles", "package_feedback_edges", "package_tangles", "lcom4", "rfc",
					"directories", "class_complexity"};
			
			Resource resrc = sonar.find(ResourceQuery.createForMetrics(artifact, metrickey));
			
			System.out.println(" resrc value is "+ resrc);
			System.out.println(" Metric key is "  + metrickey[1]+ "  "+resrc.getMeasure(metrickey[1]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[0] " + metrickey[0] + " " + resrc.getMeasure(metrickey[0]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[1] " + metrickey[1] + " " + resrc.getMeasure(metrickey[1]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[2] " + metrickey[2] + " " + resrc.getMeasure(metrickey[2]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[3] " + metrickey[3] + " " + resrc.getMeasure(metrickey[3]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[4] " + metrickey[4] + " " + resrc.getMeasure(metrickey[4]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[5] " + metrickey[5] + " " + resrc.getMeasure(metrickey[5]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[6] " + metrickey[6] + " " + resrc.getMeasure(metrickey[6]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[7] " + metrickey[7] + " " + resrc.getMeasure(metrickey[7]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[8] " + metrickey[8] + " " + resrc.getMeasure(metrickey[8]).getFormattedValue());
			if (!WEB.equals(report)) { 
				System.out.println("resrc.getMeasure(metrickey[9] " + metrickey[9] + " " + resrc.getMeasure(metrickey[9]).getFormattedValue());
			}
				System.out.println("resrc.getMeasure(metrickey[10] " + metrickey[10] + " " + resrc.getMeasure(metrickey[10]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[11] " + metrickey[11] + " " + resrc.getMeasure(metrickey[11]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[12] " + metrickey[12] + " " + resrc.getMeasure(metrickey[12]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[13] " + metrickey[13] + " " + resrc.getMeasure(metrickey[13]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[14] " + metrickey[14] + " " + resrc.getMeasure(metrickey[14]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[15] " + metrickey[15] + " " + resrc.getMeasure(metrickey[15]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[16] " + metrickey[16] + " " + resrc.getMeasure(metrickey[16]).getFormattedValue());
			System.out.println("resrc.getMeasure(metrickey[17] " + metrickey[17] + " " + resrc.getMeasure(metrickey[17]).getFormattedValue());
			
			sonarReport.setNonCommentLinesOfCode(resrc.getMeasure(metrickey[0]).getFormattedValue());
			sonarReport.setLines(resrc.getMeasure(metrickey[1]).getFormattedValue());
			sonarReport.setFiles(resrc.getMeasure(metrickey[2]).getFormattedValue());
			sonarReport.setCommentLinesDensity(resrc.getMeasure(metrickey[3]).getFormattedValue());
			sonarReport.setCommentLines(resrc.getMeasure(metrickey[4]).getFormattedValue());
			sonarReport.setDuplicatedLinesDensity(resrc.getMeasure(metrickey[5]).getFormattedValue());
			sonarReport.setDuplicatedLines(resrc.getMeasure(metrickey[6]).getFormattedValue());
			sonarReport.setDuplicatedBlocks(resrc.getMeasure(metrickey[7]).getFormattedValue());
			sonarReport.setDuplicatedFiles(resrc.getMeasure(metrickey[8]).getFormattedValue());
			if (!WEB.equals(report)) { 
				sonarReport.setFunctionComplexity(resrc.getMeasure(metrickey[9]).getFormattedValue());
			}
			sonarReport.setFileComplexity(resrc.getMeasure(metrickey[10]).getFormattedValue());
			sonarReport.setViolationsDensity(resrc.getMeasure(metrickey[11]).getFormattedValue());
			sonarReport.setBlockerViolations(resrc.getMeasure(metrickey[12]).getFormattedValue());
			sonarReport.setCriticalViolations(resrc.getMeasure(metrickey[13]).getFormattedValue());
			sonarReport.setMajorViolations(resrc.getMeasure(metrickey[14]).getFormattedValue());
			sonarReport.setMinorViolations(resrc.getMeasure(metrickey[15]).getFormattedValue());
			sonarReport.setInfoViolations(resrc.getMeasure(metrickey[16]).getFormattedValue());
			sonarReport.setWeightedViolations(resrc.getMeasure(metrickey[17]).getFormattedValue());
			
			if ((TechnologyTypes.HTML5_WIDGET.equals(techId) || TechnologyTypes.HTML5_MOBILE_WIDGET.equals(techId) 
					|| TechnologyTypes.HTML5.equals(techId) || TechnologyTypes.HTML5_JQUERY_MOBILE_WIDGET.equals(techId) 
					|| TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.equals(techId) || TechnologyTypes.JAVA_WEBSERVICE.equals(techId)) && (JAVA.equals(report) || FUNCTIONAL.equals(report))){
				
				System.out.println(" java ---------- " + report);
				System.out.println("resrc.getMeasure(metrickey[18] " + metrickey[18] +" " + resrc.getMeasure(metrickey[18]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[19] " + metrickey[19] +" " +resrc.getMeasure(metrickey[19]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[20] " + metrickey[20] +" " +resrc.getMeasure(metrickey[20]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[21] " + metrickey[21] +" " +resrc.getMeasure(metrickey[21]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[22] " + metrickey[22] +" " +resrc.getMeasure(metrickey[22]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[23] " + metrickey[23] +" " +resrc.getMeasure(metrickey[23]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[24] " + metrickey[24] +" " +resrc.getMeasure(metrickey[24]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[25] " + metrickey[25] +" "+resrc.getMeasure(metrickey[25]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[26] " + metrickey[26] +" " +resrc.getMeasure(metrickey[26]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[27] " + metrickey[27] +" " +resrc.getMeasure(metrickey[27]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[28] " + metrickey[28] +" " +resrc.getMeasure(metrickey[28]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[29] " + metrickey[29] +" " +resrc.getMeasure(metrickey[29]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[30] " + metrickey[30] +" " +resrc.getMeasure(metrickey[30]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[32] " +  metrickey[32] +" " +resrc.getMeasure(metrickey[32]).getFormattedValue());
				
				sonarReport.setClasses(resrc.getMeasure(metrickey[18]).getFormattedValue());
				sonarReport.setFunctions(resrc.getMeasure(metrickey[19]).getFormattedValue());
				sonarReport.setStatements(resrc.getMeasure(metrickey[20]).getFormattedValue());
				sonarReport.setPackages(resrc.getMeasure(metrickey[21]).getFormattedValue());
				sonarReport.setAccessors(resrc.getMeasure(metrickey[22]).getFormattedValue());
				sonarReport.setPublicDocumentedApiDensity((resrc.getMeasure(metrickey[23]).getFormattedValue()));
				sonarReport.setPublicUndocumentedApi(resrc.getMeasure(metrickey[24]).getFormattedValue());
				sonarReport.setPackageTangleIndex(resrc.getMeasure(metrickey[25]).getFormattedValue());
				sonarReport.setPackageCycles(resrc.getMeasure(metrickey[26]).getFormattedValue());
				sonarReport.setPackageFeedbackEdges(resrc.getMeasure(metrickey[27]).getFormattedValue());
				sonarReport.setPackageTangles(resrc.getMeasure(metrickey[28]).getFormattedValue());
				sonarReport.setLackOfCohesionMethods(resrc.getMeasure(metrickey[29]).getFormattedValue());
				sonarReport.setResponseForCode(resrc.getMeasure(metrickey[30]).getFormattedValue());
				sonarReport.setClassComplexity(resrc.getMeasure(metrickey[32]).getFormattedValue());
				sonarReport.setShowDivElement(REPORT_ELEMENT_JAVA_FUNC);
				
			} else if (SONAR_SOURCE.equals(report) || FUNCTIONAL.equals(report)) {
				System.out.println("resrc.getMeasure(metrickey[18] " + metrickey[18]+ " " + resrc.getMeasure(metrickey[18]).getFormattedValue());
				System.out.println("resrc.getMeasure(metrickey[19] " + metrickey[19]+ " " + resrc.getMeasure(metrickey[19]).getFormattedValue());
				sonarReport.setClasses(resrc.getMeasure(metrickey[18]).getFormattedValue());
				sonarReport.setFunctions(resrc.getMeasure(metrickey[19]).getFormattedValue());
				sonarReport.setShowDivElement(REPORT_ELEMENT_SRC_FUNC);
				
			} else if (JS.equals(report) || WEB.equals(report)) {
				System.out.println(" js ---------- " + report);
				System.out.println("metrickey[31] " + metrickey[31] +" ");
				sonarReport.setDirectories(resrc.getMeasure(metrickey[31]).getFormattedValue());
				sonarReport.setShowDivElement(REPORT_ELEMENT_JS_WEB);
			} 
			
			sonarReport.setReportType(report);
			sonarReport.setTechnology(technology);
			return sonarReport;
			
		} catch (Exception e) {
			e.printStackTrace();
//			throw new PhrescoException(e);
			return null;
		}
    }
    
    //@Test
    public void getProfile() throws Exception {
    	StringBuilder builder = new StringBuilder(Utility.getProjectHome());
    	String techid = "web";
    	System.out.println(" builder ====> " + builder);
    	builder.append("PHR_SonarAllReport");
    	builder.append(File.separator);
    	builder.append(POM_XML);
    	File pomPath = new File(builder.toString());
    	System.out.println("pomPath ============" + pomPath);
    	PomProcessor path = new PomProcessor(pomPath);
    	Profile profiles = path.getProfile(techid);
    	
    	System.out.println("path.getProfiles  ---------->>>>>>>>> " + profiles.getId());
    	
    	if (profiles.equals(profiles.getId())) {
    		System.out.println(" present " );
    	}
    }
    
    public String getReport() {
		return report;
	}
	
	public void setReport(String report) {
		this.report = report;
	}
	
	public String getProjectCode() {
	    return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
	    this.projectCode = projectCode;
	}
}


