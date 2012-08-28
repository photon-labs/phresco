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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;

public class PhrescoReportGenerationTest implements FrameworkConstants  {
	
	private ProjectAdministrator administrator = null;
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
		Project project = administrator.getProject("PHR_VSR_Mobile_Services");
		System.out.println("project ===> " + project.getProjectInfo().getTechnology().getName());
		compileReports();
		reportGen.cumulativePdfReport(project, "", "detail");
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
		File pdfFileDir = new File("/Users/kaleeswaran/work/cairo/framework/phresco-framework-web/src/main/resources/reports/template");
		File[] listFiles = pdfFileDir.listFiles(new FileExtensionFileFilter(".jrxml"));
		for (File jrxmlFile : listFiles) {
//			System.out.println("jrxmlFile ===> " + jrxmlFile.getName());
			System.out.println("jrxmlFile ===> " + jrxmlFile.getAbsolutePath());
			JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFile.getAbsolutePath());
			String desinationPath = "/Users/kaleeswaran/work/cairo/framework/phresco-framework-web/src/main/resources/reports/jasper/" + FilenameUtils.removeExtension(jrxmlFile.getName()) + ".jasper";
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
    
}


