package com.photon.phresco.framework.actions.applications;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.lowagie.text.pdf.PdfContentByte;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.actions.FrameworkBaseAction;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.commons.AllTestSuite;
import com.photon.phresco.framework.commons.AndroidPerfReport;
import com.photon.phresco.framework.commons.FrameworkUtil;
import com.photon.phresco.framework.commons.JmeterReport;
import com.photon.phresco.framework.commons.JmeterTypeReport;
import com.photon.phresco.framework.commons.LoadTestReport;
import com.photon.phresco.framework.commons.SonarReport;
import com.photon.phresco.framework.commons.SureFireReport;
import com.photon.phresco.framework.commons.XmlReport;
import com.photon.phresco.framework.model.PerformanceTestResult;
import com.photon.phresco.framework.model.TestCase;
import com.photon.phresco.framework.model.TestCaseError;
import com.photon.phresco.framework.model.TestCaseFailure;
import com.photon.phresco.framework.model.TestResult;
import com.photon.phresco.framework.model.TestSuite;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;
import com.phresco.pom.model.Profile;
import com.phresco.pom.util.PomProcessor;


public class PhrescoReportGeneration extends FrameworkBaseAction implements FrameworkConstants {

	private static final long serialVersionUID = 1L;
	private static final Logger S_LOGGER = Logger.getLogger(PhrescoReportGeneration.class);
    private static Boolean debugEnabled  =S_LOGGER.isDebugEnabled();
	private Project project = null;
	private String techId = null;
	private String techName = null;
	private String testType = null;
	private String projectCode = null;
	private String projectName = null;
    private FrameworkUtil reportPaths = null;
    private String reportDatasType = null;
	private String skipTest = null;
    private String codeTechnology = null;
    private String report = null;
    private String validateAgainst = null;
	private String target = null;

    //test suite details
	private float noOfTstSuiteTests = 0;
    private float noOfTstSuiteFailures = 0;
    private float noOfTstSuiteErrors = 0;
    
    private String fileName = null;
    public PhrescoReportGeneration() {
    	final Date today = Calendar.getInstance().getTime();
        final DateFormat yymmdd = new SimpleDateFormat("MMM dd yyyy HH.mm");
        this.fileName = yymmdd.format(today);
    }
    
	public void generatePdfReport(Project proj, String tstType, String reportDataType)  throws PhrescoException {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.generatePdfReport()");
		try {
			reportPaths = FrameworkUtil.getInstance();
			project = proj;
			testType = tstType;
			techId = project.getProjectInfo().getTechnology().getId();
			projectCode = project.getProjectInfo().getCode();
			projectName = project.getProjectInfo().getName();
			techName = project.getProjectInfo().getTechnology().getName();
			reportDatasType = reportDataType;
			// Report generation for unit and functional
			if (UNIT.equals(testType) || FUNCTIONAL.equals(testType)) {
				//crisp and detail view report generation
				SureFireReport sureFireReports = sureFireReports();
				generateUnitAndFunctionalReport(sureFireReports);
			// Report generation for performance
			} else if (PERFORMACE.equals(testType)) {
				if(TechnologyTypes.ANDROIDS.contains(techId)) {
					//android technology reports 
					List<AndroidPerfReport> jmeterTestResultsForAndroid = getJmeterTestResultsForAndroid();
					generateAndroidPerformanceReport(jmeterTestResultsForAndroid);
				} else {
					ArrayList<JmeterTypeReport> jmeterTestResults = getJmeterTestResults();
					// Performance test report generation
					generateJmeterPerformanceReport(jmeterTestResults);
				}
			}  else if (LOAD.equals(testType)) {
				List<LoadTestReport> loadTestResults = getLoadTestResults();
				// Load test report generation
				generateLoadTestReport(loadTestResults);
			}
		} catch (Exception e) {
			S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.generatePdfReport()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException(e);
		}
	}
	
	public void cumulativePdfReport(Project proj, String tstType, String reportDataType) throws PhrescoException {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.cumulativePdfReport()");
		try {
			reportPaths = FrameworkUtil.getInstance();
			project = proj;
			testType = tstType;
			techId = project.getProjectInfo().getTechnology().getId();
			techName = project.getProjectInfo().getTechnology().getName();
			projectCode = project.getProjectInfo().getCode();
			projectName = project.getProjectInfo().getName();
			reportDatasType = reportDataType;
			cumalitiveTestReport();
		} catch(Exception e) {
			S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.cumulativePdfReport()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException(e);
		}
	}
	
	//Consolidated report for all test
	public void cumalitiveTestReport() throws Exception {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.cumalitiveTestReport()");
		try {
			//unit and functional details
			testType = UNIT;
			SureFireReport unitTestSureFireReports = null;
			unitTestSureFireReports = sureFireReports();
			S_LOGGER.debug("unitTestSureFireReports" + unitTestSureFireReports);
			
			testType = FUNCTIONAL;
			SureFireReport functionalSureFireReports = null;
			functionalSureFireReports = sureFireReports();
			S_LOGGER.debug(FUNCTIONAL_SURE_FIRE_REPORTS + functionalSureFireReports);
			
			testType = "";
			//performance details
			List<AndroidPerfReport> jmeterTestResultsForAndroid = null;
			ArrayList<JmeterTypeReport> jmeterTestResults = null;
			if(TechnologyTypes.ANDROIDS.contains(techId)) {
				jmeterTestResultsForAndroid = getJmeterTestResultsForAndroid();
				S_LOGGER.debug(JMETER_TEST_RESULTS_FOR_ANDROID + jmeterTestResultsForAndroid);
			} else {
				jmeterTestResults = getJmeterTestResults();
				S_LOGGER.debug(JMETER_TEST_RESULTS + jmeterTestResultsForAndroid);
			}
			
			//load test details
			List<LoadTestReport> loadTestResults = getLoadTestResults();
			S_LOGGER.debug("loadTestResults" + loadTestResults);
			
			Map<String, Object> cumulativeReportparams = new HashMap<String,Object>();
			cumulativeReportparams.put(REQ_PROJECT_CODE, projectCode);
			cumulativeReportparams.put(REQ_PROJECT_NAME, projectName);
			cumulativeReportparams.put(REQ_TECH_NAME, techName);
			cumulativeReportparams.put(REQ_REPORTS_DATA_TYPE, reportDatasType);
			cumulativeReportparams.put(UNIT_TEST_REPORTS, Arrays.asList(unitTestSureFireReports));
			cumulativeReportparams.put(FUNCTIONAL_TEST_REPORTS, Arrays.asList(functionalSureFireReports));
			
			if(TechnologyTypes.ANDROIDS.contains(techId)) {
				cumulativeReportparams.put(PERFORMANCE_SPECIAL_HANDLE, true);
				cumulativeReportparams.put(PERFORMANCE_TEST_REPORTS,jmeterTestResultsForAndroid);
			} else {
				cumulativeReportparams.put(PERFORMANCE_SPECIAL_HANDLE, false);
				cumulativeReportparams.put(PERFORMANCE_TEST_REPORTS, jmeterTestResults);
			}
			cumulativeReportparams.put(LOAD_TEST_REPORTS, loadTestResults);
			
			//Sonar details
			List<SonarReport> sonarReports = null;
			sonarReports = new ArrayList<SonarReport>();
			
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        	builder.append(projectCode);
        	builder.append(File.separator);
        	builder.append(POM_XML);
        	
        	File pomPath = new File(builder.toString());
        	PomProcessor path = new PomProcessor(pomPath);
        	List<Profile> profiles = path.getModel().getProfiles().getProfile();
        	
        	List<String> sonarTechReports = new ArrayList<String>(4);
        	
        	for (Profile profile : profiles) {
	    		if (profile.getProperties() != null) {
	    			List<Element> any = profile.getProperties().getAny();
	    			int size = any.size();
	    			
	    			for (int i = 0; i < size; ++i) {
	    				boolean tagExist = 	any.get(i).getTagName().equals(SONAR_LANGUAGE);
	    				if (tagExist != false){
	    					sonarTechReports.add(profile.getId());
						}
	    			}
	    		}
	    	}
    		
    		if(CollectionUtils.isEmpty(sonarTechReports)) {
    			sonarTechReports.add(SONAR_SOURCE);
    		}
        	sonarTechReports.add(FUNCTIONAL);
        	
        	for (String sonarTechReport : sonarTechReports) {
				SonarReport srcSonarReport = generateSonarReport(sonarTechReport, techId);
				if(srcSonarReport != null) {
					sonarReports.add(srcSonarReport);
				}
			}
			
			cumulativeReportparams.put(FrameworkConstants.REQ_SONAR_REPORT, sonarReports);
			generateCumulativeTestReport(cumulativeReportparams);
			
		} catch (Exception e) {
			S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.cumalitiveTestReport()" + FrameworkUtil.getStackTraceAsString(e));
			S_LOGGER.error("Report generation errorr ");
		}
	}
	
	//cumulative test report generation
	public void generateCumulativeTestReport(Map<String, Object> cumulativeReportparams) throws PhrescoException {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.generateCumulativeTestReport()");
		InputStream reportStream = null;
		BufferedInputStream bufferedInputStream = null;
		String uuid = UUID.randomUUID().toString();
		String outFileNamePDF = "";
		String outFileNameIphoneSonarPDF = "";
		String outFileNameXHTML = "";
		try {
			if (TechnologyTypes.IPHONE_NATIVE.equals(techId) || TechnologyTypes.IPHONE_HYBRID.equals(techId)) {
				S_LOGGER.debug("generateCumulativeTestReport for iphone technology ");
				outFileNamePDF = Utility.getPhrescoTemp() + uuid + File.separator + projectCode + UNDERSCORE + reportDatasType + UNDERSCORE + fileName + DOT + PDF;
				outFileNameIphoneSonarPDF = Utility.getPhrescoTemp() + uuid + File.separator + projectCode + UNDERSCORE + fileName + DOT + PDF;
				outFileNameXHTML = Utility.getPhrescoTemp() + uuid + File.separator + projectCode + UNDERSCORE + "index" + DOT + "xhtml";
				new File(outFileNameIphoneSonarPDF).getParentFile().mkdirs();
				new File(outFileNameXHTML).getParentFile().mkdirs();
			} else {
				S_LOGGER.debug("generateCumulativeTestReport for except mobile technology " + techId);
				outFileNamePDF = Utility.getProjectHome() + File.separator + projectCode + File.separator + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES + File.separator + CUMULATIVE + File.separator + projectCode + UNDERSCORE + reportDatasType + UNDERSCORE + fileName + DOT + PDF;
			}
			
			S_LOGGER.debug("outFileNamePDF... " + outFileNamePDF);
			new File(outFileNamePDF).getParentFile().mkdirs();
			String jasperFile = "PhrescoCumulativeReport.jasper";
			reportStream = this.getClass().getClassLoader().getResourceAsStream(REPORTS_JASPER + jasperFile);
	        
			bufferedInputStream = new BufferedInputStream(reportStream);
			JREmptyDataSource  dataSource = new JREmptyDataSource();
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, cumulativeReportparams, dataSource);
			JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter(); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileNamePDF);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.exportReport();
			
			if (TechnologyTypes.IPHONE_NATIVE.equals(techId) || TechnologyTypes.IPHONE_HYBRID.equals(techId)) {
				S_LOGGER.debug("going to generate html and concatinate files  " + techId);
				iphoneSonarHtmlToPdf(outFileNameIphoneSonarPDF, outFileNameXHTML);
				String outFinalFileNamePDF = Utility.getProjectHome() + File.separator + projectCode + File.separator + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES + File.separator + CUMULATIVE + File.separator + projectCode + UNDERSCORE + reportDatasType + UNDERSCORE + fileName + DOT + PDF;
				new File(outFinalFileNamePDF).getParentFile().mkdirs();
				
	            List<String> pdfs = new ArrayList<String>();
	            pdfs.add(outFileNamePDF);
	            pdfs.add(outFileNameIphoneSonarPDF);
	            concatPDFs(pdfs, outFinalFileNamePDF, true);
			}
			try {
				S_LOGGER.debug("going to delete directory ");
				FileUtils.deleteDirectory(new File(Utility.getPhrescoTemp() + uuid));
			} catch (Exception e) {
				S_LOGGER.error("Delete directory failed");
			}
			S_LOGGER.debug("Cumulative Report generation completed" + outFileNamePDF);
		} catch(Exception e) {
			S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.generateCumulativeTestReport()" + FrameworkUtil.getStackTraceAsString(e));
			S_LOGGER.error("Report generation error ");
			throw new PhrescoException(e);
		} finally {
			if (reportStream != null) {
				try {
					reportStream.close();
				} catch (IOException e) {
					S_LOGGER.error("Report generation error ");
				}
			}
			if (bufferedInputStream != null) {
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					S_LOGGER.error("Report generation error ");
				}
			}
		}
		
	}
	
	//Merge the PDF's
	public static void concatPDFs(List<String> PDFFiles,  String outputPDFFile, boolean paginate) {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.concatPDFs()");
        OutputStream outputStream = null;
		com.lowagie.text.Document document = null;
        try {
    		List<InputStream> pdfs = new ArrayList<InputStream>();
    		for (String PDFFile : PDFFiles) {
    	        pdfs.add(new FileInputStream(new File(PDFFile)));
    		}
    		
    		outputStream = new FileOutputStream(outputPDFFile);
    		document = new com.lowagie.text.Document();
    		
            List<com.lowagie.text.pdf.PdfReader> readers = new ArrayList<com.lowagie.text.pdf.PdfReader>();
            int totalPages = 0;
            Iterator<InputStream> iteratorPDFs = pdfs.iterator();
 
            // Create Readers for the pdfs.
            while (iteratorPDFs.hasNext()) {
                InputStream pdf = iteratorPDFs.next();
                com.lowagie.text.pdf.PdfReader pdfReader = new com.lowagie.text.pdf.PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            // Create a writer for the outputstream
            com.lowagie.text.pdf.PdfWriter writer = com.lowagie.text.pdf.PdfWriter.getInstance(document, outputStream);
 
            document.open();
            com.lowagie.text.pdf.BaseFont bf = com.lowagie.text.pdf.BaseFont.createFont(com.lowagie.text.pdf.BaseFont.HELVETICA,
            		com.lowagie.text.pdf.BaseFont.CP1252, com.lowagie.text.pdf.BaseFont.NOT_EMBEDDED);
            com.lowagie.text.pdf.PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
            // data
 
            com.lowagie.text.pdf.PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<com.lowagie.text.pdf.PdfReader> iteratorPDFReader = readers.iterator();
 
            // Loop through the PDF files and add to the output.
            while (iteratorPDFReader.hasNext()) {
            	com.lowagie.text.pdf.PdfReader pdfReader = iteratorPDFReader.next();
 
                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader,  pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);
 
                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, ""
                                + currentPageNumber + " of " + totalPages, 520,
                                5, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (Exception e) {
        	S_LOGGER.error("Entering Method PhrescoReportGeneration.concatPDFs()" + FrameworkUtil.getStackTraceAsString(e));
        } finally {
            if (document.isOpen())
                document.close();
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ioe) {
            	S_LOGGER.error("Entering Method ioe PhrescoReportGeneration.concatPDFs()" + FrameworkUtil.getStackTraceAsString(ioe));
            }
        }
    }
	
	//Conversion of Html to Pdf
	public void iphoneSonarHtmlToPdf(String outFileNameIphoneSonarPDF, String outFileNameXHTML) throws PhrescoException {
		S_LOGGER.debug("Entering Method  PhrescoReportGeneration.iphoneSonarHtmlToPdf()");
		try {
	        CleanerProperties props = new CleanerProperties();
	        props.setTranslateSpecialEntities(true);
	        props.setTransResCharsToNCR(true);
	        props.setOmitComments(true);
	        File staticAnalysisReport = null;
	        
	    	StringBuilder codeValidatePath = new StringBuilder(Utility.getProjectHome());
	    	codeValidatePath.append(projectCode);
	    	codeValidatePath.append(File.separatorChar);
	    	codeValidatePath.append(DO_NOT_CHECKIN_DIR);
	    	codeValidatePath.append(File.separatorChar);
	    	codeValidatePath.append(STATIC_ANALYSIS_REPORT);
	    	codeValidatePath.append(File.separatorChar);
	    	codeValidatePath.append(INDEX_HTML);
	    	staticAnalysisReport = new File(codeValidatePath.toString());
	    	S_LOGGER.debug("staticAnalysisReport " + staticAnalysisReport);
	        if(!staticAnalysisReport.exists()) {
	        	System.out.println("Index file is not available!!!!!!!!!");
	        	throw new PhrescoException("static analysis report is not available!!!");
	        }
	        
	        //checking of starting and ending tags are in proper
	        TagNode tagNode = new HtmlCleaner(props).clean(staticAnalysisReport);
	        new PrettyXmlSerializer(props).writeToFile(tagNode, outFileNameXHTML, "utf-8");
	        
	        File xhtmlpath = new File(outFileNameXHTML);
	        File pdfPath = new File(outFileNameIphoneSonarPDF);
	        com.itextpdf.text.Document pdfDocument = null;
	        PdfWriter pdfWriter = null;
	        pdfDocument = new com.itextpdf.text.Document();
	        pdfWriter = PdfWriter.getInstance(pdfDocument, new FileOutputStream(pdfPath));
	        pdfDocument.open();
	        
	        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, (com.itextpdf.text.Document) pdfDocument,
	                new FileInputStream(xhtmlpath), null);
	        pdfDocument.close();
		} catch (Exception e) {
			S_LOGGER.error("Entering Method PhrescoReportGeneration.iphoneSonarHtmlToPdf()" + FrameworkUtil.getStackTraceAsString(e));
			throw new PhrescoException(e);
		}
	}
	
	 public SonarReport generateSonarReport(String report, String techId) throws PhrescoException {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.generateSonarReport()");
		SonarReport sonarReport = new SonarReport();
    	String technology = null;
		String serverUrl = "";
		try {
			FrameworkUtil frameworkUtil = FrameworkUtil.getInstance();
			serverUrl = frameworkUtil.getSonarURL();
        
			StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        	builder.append(projectCode);
        	
            if (StringUtils.isNotEmpty(report) && FUNCTIONALTEST.equals(report)) {
                frameworkUtil = FrameworkUtil.getInstance();
                builder.append(frameworkUtil.getFuncitonalTestDir(techId));
            }
            
            builder.append(File.separatorChar);
        	builder.append(POM_XML);
        	File pomPath = new File(builder.toString());
        	
        	PomProcessor processor = new PomProcessor(pomPath);
        	String groupId = processor.getModel().getGroupId();
        	String artifactId = processor.getModel().getArtifactId();

        	StringBuilder sbuild = new StringBuilder();
        	sbuild.append(groupId);
        	sbuild.append(COLON);
        	sbuild.append(artifactId);
        	
        	if (StringUtils.isNotEmpty(report) && !SOURCE_DIR.equals(report)) {
        		sbuild.append(COLON);
        		sbuild.append(report);
        	}
        	
        	String artifact = sbuild.toString();
			Sonar sonar = new Sonar(new HttpClient4Connector(new Host(serverUrl)));
			
			//metric key parameters for sonar 
			String metrickey[] = {"ncloc", "lines", "files", "comment_lines_density" , "comment_lines", "duplicated_lines_density", "duplicated_lines", 
					"duplicated_blocks", "duplicated_files", "function_complexity", "file_complexity", "violations_density", "blocker_violations", 
					"critical_violations", "major_violations", "minor_violations", "info_violations", "weighted_violations",
					"classes", "functions",
					"statements","packages", "accessors", "public_documented_api_density", "public_undocumented_api","package_tangle_index","package_cycles", "package_feedback_edges", "package_tangles", "lcom4", "rfc",
					"directories", "class_complexity", "comment_blank_lines", "coverage", "uncovered_lines"};
			
			Resource resrc = sonar.find(ResourceQuery.createForMetrics(artifact, metrickey));
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
					|| TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET.equals(techId) || TechnologyTypes.JAVA_WEBSERVICE.equals(techId) 
					|| TechnologyTypes.NODE_JS_WEBSERVICE.equals(techId)) && (JAVA.equals(report) || FUNCTIONAL.equals(report))) {
				
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
				
			} else if ((TechnologyTypes.SHAREPOINT.equals(techId)) && (SONAR_SOURCE.equals(report) || FUNCTIONAL.equals(report))) {
				
				sonarReport.setClasses(resrc.getMeasure(metrickey[18]).getFormattedValue());
				sonarReport.setFunctions(resrc.getMeasure(metrickey[19]).getFormattedValue());
				sonarReport.setStatements(resrc.getMeasure(metrickey[20]).getFormattedValue());
				sonarReport.setAccessors(resrc.getMeasure(metrickey[22]).getFormattedValue());
				sonarReport.setPublicDocumentedApiDensity((resrc.getMeasure(metrickey[23]).getFormattedValue()));
				sonarReport.setPublicUndocumentedApi(resrc.getMeasure(metrickey[24]).getFormattedValue());
				sonarReport.setCoverage(resrc.getMeasure(metrickey[34]).getFormattedValue());
				sonarReport.setUncoveredLines(resrc.getMeasure(metrickey[35]).getFormattedValue());
				sonarReport.setShowDivElement(REPORT_ELEMENT_SHAREPOINT_SRC_FUNC);
				
			} else if ((SONAR_SOURCE.equals(report) || FUNCTIONAL.equals(report)) && !TechnologyTypes.NODE_JS_WEBSERVICE.equals(techId)) {
				sonarReport.setClasses(resrc.getMeasure(metrickey[18]).getFormattedValue());
				sonarReport.setFunctions(resrc.getMeasure(metrickey[19]).getFormattedValue());
				sonarReport.setShowDivElement(REPORT_ELEMENT_SRC_FUNC);
			} else if (JS.equals(report) || WEB.equals(report)) {
				sonarReport.setDirectories(resrc.getMeasure(metrickey[31]).getFormattedValue());
				sonarReport.setShowDivElement(REPORT_ELEMENT_JS_WEB);
				
			} else if (TechnologyTypes.NODE_JS_WEBSERVICE.equals(techId) && (SONAR_SOURCE.equals(report))) {
				sonarReport.setCommentBlankLines(resrc.getMeasure(metrickey[33]).getFormattedValue());
				sonarReport.setShowDivElement(REPORT_ELEMENT_NODE_JS);
			}
			
			sonarReport.setReportType(report);
			sonarReport.setTechnology(technology);
			return sonarReport;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	// Unit and functional pdf report generation
	public void generateUnitAndFunctionalReport(SureFireReport sureFireReports)  throws PhrescoException {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.generateUnitAndFunctionalReport()");
		InputStream reportStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			String outFileNamePDF = Utility.getProjectHome() + projectCode + File.separator + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES + File.separator + testType + File.separator + testType  + UNDERSCORE + reportDatasType + UNDERSCORE + fileName + DOT + PDF;
			new File(outFileNamePDF).getParentFile().mkdirs();
			String containerJasperFile = "PhrescoSureFireReport.jasper";
			reportStream = this.getClass().getClassLoader().getResourceAsStream(REPORTS_JASPER + containerJasperFile);
			bufferedInputStream = new BufferedInputStream(reportStream);
			Map<String, Object> parameters = new HashMap<String,Object>();
			parameters.put(REQ_PROJECT_CODE, projectCode);
			parameters.put(REQ_PROJECT_NAME, projectName);
			parameters.put(REQ_TECH_NAME, techName);
			parameters.put(REQ_TEST_TYPE, testType);
			parameters.put(REQ_REPORTS_DATA_TYPE, reportDatasType);
			JRBeanArrayDataSource dataSource = new JRBeanArrayDataSource(new SureFireReport[]{sureFireReports});
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter(); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileNamePDF);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.exportReport();
			S_LOGGER.debug("Unit and functional Report generation completed" + outFileNamePDF);
		} catch(Exception e) {
			S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.generateUnitAndFunctionalReport()" + FrameworkUtil.getStackTraceAsString(e));
			S_LOGGER.error("Unit and functional  generation error");
			throw new PhrescoException(e);
		} finally {
			if (reportStream != null) {
				try {
					reportStream.close();
				} catch (IOException e) {
					S_LOGGER.error("Report generation errorr ");
				}
			}
			if (bufferedInputStream != null) {
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					S_LOGGER.error("Report generation errorr ");
				}
			}
		}
	}
	
	// performance test report
	public void generateJmeterPerformanceReport(ArrayList<JmeterTypeReport> jmeterTestResults)  throws PhrescoException {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.generateJmeterPerformanceReport()");
		try {
			ArrayList<JmeterTypeReport> jmeterTstResults = jmeterTestResults;
			String outFileNamePDF = Utility.getProjectHome() + projectCode + File.separator + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES + File.separator + testType + File.separator + testType  + UNDERSCORE + reportDatasType + UNDERSCORE + fileName + DOT + PDF;
			String jasperFile = "PhrescoPerfContain.jasper";
			Map<String, Object> parameters = new HashMap<String,Object>();
			parameters.put(REQ_PROJECT_CODE, projectCode);
			parameters.put(REQ_PROJECT_NAME, projectName);
			parameters.put(REQ_TECH_NAME, techName);
			parameters.put(REQ_TEST_TYPE, testType);
			parameters.put(REQ_REPORTS_DATA_TYPE, reportDatasType);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jmeterTstResults);
			reportGenerate(outFileNamePDF, jasperFile, parameters, dataSource);
		} catch (Exception e) {
			S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.generateJmeterPerformanceReport()" + FrameworkUtil.getStackTraceAsString(e));
			S_LOGGER.error("Performance  generation error ");
			throw new PhrescoException(e);
		}
	}
	
	// performance test report
	public void generateAndroidPerformanceReport(List<AndroidPerfReport> androidPerReports)  throws PhrescoException {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.generateAndroidPerformanceReport()");
		try {
			String outFileNamePDF = Utility.getProjectHome() + projectCode + File.separator + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES + File.separator + testType + File.separator + testType + UNDERSCORE + reportDatasType + UNDERSCORE + fileName + DOT + PDF;
			String jasperFile = "PhrescoAndroidPerfContain.jasper";
			Map<String, Object> parameters = new HashMap<String,Object>();
			parameters.put(REQ_PROJECT_CODE, projectCode);
			parameters.put(REQ_PROJECT_NAME, projectName);
			parameters.put(REQ_TECH_NAME, techName);
			parameters.put(REQ_TEST_TYPE, testType);
			parameters.put(REQ_REPORTS_DATA_TYPE, reportDatasType);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(androidPerReports);
			reportGenerate(outFileNamePDF, jasperFile, parameters, dataSource);
		} catch (Exception e) {
			S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.generateAndroidPerformanceReport()" + FrameworkUtil.getStackTraceAsString(e));
			S_LOGGER.error("Android Performance  generation error ");
			throw new PhrescoException(e);
		}
	}
	
	// load test report
	public void generateLoadTestReport(List<LoadTestReport> loadTestResults)  throws PhrescoException {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.generateLoadTestReport()");
		try {
			String outFileNamePDF = Utility.getProjectHome() + projectCode + File.separator + DO_NOT_CHECKIN_DIR + File.separator + ARCHIVES + File.separator + testType + File.separator + testType + UNDERSCORE + reportDatasType + UNDERSCORE + fileName + DOT + PDF;
			String jasperFile = "PhrescoLoadTestContain.jasper";
			Map<String, Object> parameters = new HashMap<String,Object>();
			parameters.put(REQ_PROJECT_CODE, projectCode);
			parameters.put(REQ_PROJECT_NAME, projectName);
			parameters.put(REQ_TECH_NAME, techName);
			parameters.put(REQ_TEST_TYPE, testType);
			parameters.put(REQ_REPORTS_DATA_TYPE, reportDatasType);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(loadTestResults);
			reportGenerate(outFileNamePDF, jasperFile, parameters, dataSource);
		} catch (Exception e) {
			S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.generateLoadTestReport()" + FrameworkUtil.getStackTraceAsString(e));
			S_LOGGER.error("Load report generation error");
			throw new PhrescoException(e);
		}
	}
	
	public void reportGenerate(String outFileNamePDF, String jasperFile, Map<String, Object> parameters, JRBeanCollectionDataSource dataSource) throws PhrescoException {
		S_LOGGER.debug("Entering Method PhrescoReportGeneration.reportGenerate()");
		InputStream reportStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			new File(outFileNamePDF).getParentFile().mkdirs();
			reportStream = this.getClass().getClassLoader().getResourceAsStream(REPORTS_JASPER + jasperFile);
			bufferedInputStream = new BufferedInputStream(reportStream);
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter(); 
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileNamePDF);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.exportReport(); 
		} catch (Exception e) {
			S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.reportGenerate()" + FrameworkUtil.getStackTraceAsString(e));
			S_LOGGER.error("Load report generation error");
			throw new PhrescoException(e);
		} finally {
			if (reportStream != null) {
				try {
					reportStream.close();
				} catch (IOException e) {
					S_LOGGER.error("Report generation errorr ");
				}
			}
			if (bufferedInputStream != null) {
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					S_LOGGER.error("Report generation errorr ");
				}
			}
		}
	}
	
	public List<LoadTestReport> getLoadTestResults()  throws Exception {
		List<LoadTestReport> loadTestReports = new ArrayList<LoadTestReport>();
		String reportFilePath = Utility.getProjectHome() + projectCode + reportPaths.getLoadReportDir(techId);
		List<String> testResultFiles = getTestResultFiles(reportFilePath);
		for (String resultFile : testResultFiles) {
			Document doc = getDocumentOfFile(reportFilePath, resultFile);
			List<TestResult> loadTestResults = getLoadTestResult(doc);
			for (TestResult testResult : loadTestResults) {
				S_LOGGER.debug("testResult name ======> " + testResult.getThreadName());
			}
			// Adding report data to bean object
			LoadTestReport loadTestReport = new LoadTestReport();
			loadTestReport.setFileName(resultFile);
			loadTestReport.setTestResults(loadTestResults);
			loadTestReports.add(loadTestReport);
		}
		return loadTestReports;
	}
	
	public ArrayList<JmeterTypeReport> getJmeterTestResults() throws Exception {
        List<String> testResultsTypes = new ArrayList<String>();
        testResultsTypes.add("server");
        testResultsTypes.add("database");
        testResultsTypes.add("webservices");
        
        // List of performance test types
        ArrayList<JmeterTypeReport> jmeterTypeReports = new ArrayList<JmeterTypeReport>();
        for(String perType: testResultsTypes) {
            StringBuilder sb = new StringBuilder();
            sb.append(Utility.getProjectHome());
            sb.append(project.getProjectInfo().getCode());
            String performanceReportDir = reportPaths.getPerformanceReportDir(project.getProjectInfo().getTechnology().getId());
            
            if (StringUtils.isNotEmpty(performanceReportDir) && StringUtils.isNotEmpty(perType)) {
                Pattern p = Pattern.compile("dir_type");
                Matcher matcher = p.matcher(performanceReportDir);
                performanceReportDir = matcher.replaceAll(perType);
                sb.append(performanceReportDir); 
            }
            List<String> testResultFiles = getTestResultFiles(sb.toString());
			String deviceId = null; // for android alone
			
			// List of performance test reports
			List<JmeterReport> jmeterReports = new ArrayList<JmeterReport>();
            for (String testResultFile : testResultFiles) {
            	Document document = getDocumentOfFile(sb.toString(), testResultFile);
            	JmeterReport jmeterReport = getPerformanceReport(document, testResultFile, techId, deviceId); // need to pass tech id and tag name
            	jmeterReports.add(jmeterReport);
			}
            // When data is not available dont show in i report
            if (!jmeterReports.isEmpty()) {
	            JmeterTypeReport jmeterTypeReport = new JmeterTypeReport();
	            jmeterTypeReport.setType(perType);
	            jmeterTypeReport.setFileReport(jmeterReports);
	            // adding final data to jmeter type reports
	            jmeterTypeReports.add(jmeterTypeReport);
            }
        }
        
        for (JmeterTypeReport jmeterTypeReport : jmeterTypeReports) {
        	String type = jmeterTypeReport.getType();
        	List<JmeterReport> fileReports = jmeterTypeReport.getFileReport();
		}
        return jmeterTypeReports;
	}
	
	public List<AndroidPerfReport> getJmeterTestResultsForAndroid() throws Exception {
        // List of performance test types
        StringBuilder sb = new StringBuilder();
        sb.append(Utility.getProjectHome());
        sb.append(project.getProjectInfo().getCode());
        String performanceReportDir = reportPaths.getPerformanceReportDir(project.getProjectInfo().getTechnology().getId());
        if (StringUtils.isNotEmpty(performanceReportDir)) {
            sb.append(performanceReportDir); 
        }
        List<String> testResultFiles = getTestResultFiles(sb.toString());
		
		// List of performance test reports
        List<AndroidPerfReport> androidPerfFilesWithDatas = new ArrayList<AndroidPerfReport>(); //kalees
        for (String testResultFile : testResultFiles) {
        	Document document = getDocumentOfFile(sb.toString(), testResultFile);
        	
        	Map<String, String> deviceNamesWithId = getDeviceNames(document);
            Set st = deviceNamesWithId.entrySet();
            Iterator it = st.iterator();
            List<JmeterReport> androidDeviceWithDatas = new ArrayList<JmeterReport>();
            while (it.hasNext()) {
                Map.Entry m = (Map.Entry) it.next();
                String androidDeviceId = (String) m.getKey();
                String androidDeviceName = (String) m.getValue();
            	JmeterReport jmeterReport = getPerformanceReport(document, testResultFile, techId, androidDeviceId); // need to pass tech id and tag name
            	jmeterReport.setFileName(androidDeviceName);
                androidDeviceWithDatas.add(jmeterReport);
            }
            AndroidPerfReport androidPerReport = new AndroidPerfReport();
            androidPerReport.setFileName(testResultFile);
            androidPerReport.setDeviceReport(androidDeviceWithDatas);
            androidPerfFilesWithDatas.add(androidPerReport);
		}
        
        for (AndroidPerfReport androidPerfFilesWithData : androidPerfFilesWithDatas) {
        	List<JmeterReport> deviceReports = androidPerfFilesWithData.getDeviceReport();
        	for (JmeterReport jmeterReport : deviceReports) {
        		S_LOGGER.debug("getTotalAvg ======> " + jmeterReport.getTotalAvg());
			}
		}
        return androidPerfFilesWithDatas;
	}
	
	// unit and functional test report
	public ArrayList<XmlReport> sureFireReport() throws Exception {
		ArrayList<XmlReport> xmlReports = new ArrayList<XmlReport>();
		String reportFilePath = "";
		if (UNIT.equals(testType)) {
			reportFilePath = Utility.getProjectHome() + projectCode + reportPaths.getUnitReportDir(techId);
		} else {
			reportFilePath = Utility.getProjectHome() + projectCode + reportPaths.getFunctionalReportDir(techId);
		}
		List<String> testResultFiles = getTestResultFiles(reportFilePath);
		for (String resultFile : testResultFiles) {
			XmlReport xmlReport = new XmlReport();
			xmlReport.setFileName(resultFile);
			ArrayList<TestSuite> testSuiteWithTestCase = new ArrayList<TestSuite>();
			Document doc = getDocumentOfFile(reportFilePath, resultFile);
			List<TestSuite> testSuites = getTestSuite(doc);
			for (TestSuite testSuite : testSuites) {	// test suite ll have graph details
				List<TestCase> testCases = getTestCases(doc, testSuite.getName());
				testSuite.setTestCases(testCases);
				testSuiteWithTestCase.add(testSuite);
			}
			xmlReport.setTestSuites(testSuiteWithTestCase);
			xmlReports.add(xmlReport);
		}
		return xmlReports;
	}
	
	// unit and functional test report
	public SureFireReport sureFireReports() throws Exception {
		SureFireReport sureFireReport = new SureFireReport();
		
		String reportFilePath = "";
		if (UNIT.equals(testType)) {
			reportFilePath = Utility.getProjectHome() + projectCode + reportPaths.getUnitReportDir(techId);
		} else {
			reportFilePath = Utility.getProjectHome() + projectCode + reportPaths.getFunctionalReportDir(techId);
		}
		S_LOGGER.debug("reportFilePath " + reportFilePath);
		List<String> testResultFiles = getTestResultFiles(reportFilePath);
		ArrayList<TestSuite> testSuiteWithTestCase  = null;
		ArrayList<AllTestSuite> allTestSuiteDetails = null;
		// detailed information object
		testSuiteWithTestCase = new ArrayList<TestSuite>();
		// crisp information of the test
		allTestSuiteDetails =  new ArrayList<AllTestSuite>();
		
		// Iterate over each file
		for (String resultFile : testResultFiles) {

			Document doc = getDocumentOfFile(reportFilePath, resultFile);
			List<TestSuite> testSuites = getTestSuite(doc);
			
			//crisp info
			float totalTestSuites = 0;
			float successTestSuites = 0;
			float failureTestSuites = 0;
			float errorTestSuites = 0;

			for (TestSuite testSuite : testSuites) {	// test suite ll have graph details
				List<TestCase> testCases = getTestCases(doc, testSuite.getName());
				
				float tests = 0;
		        float failures = 0;
		        float errors = 0;
	         	failures = getNoOfTstSuiteFailures();
	            errors = getNoOfTstSuiteErrors();
	            tests =  getNoOfTstSuiteTests();
	            float success = 0;
	            
	            if (failures != 0 && errors == 0) {
	                if (failures > tests) {
	                    success = failures - tests;
	                } else {
	                    success = tests - failures;
	                }
	            } else if (failures == 0 && errors != 0) {
	                if (errors > tests) {
	                    success = errors - tests;
	                } else {
	                    success = tests - errors;
	                }
	            } else if (failures != 0 && errors != 0) {
	                float failTotal = (failures + errors);
	                if (failTotal > tests) {
	                    success = failTotal - tests;
	                } else {
	                    success = tests - failTotal;
	                }
	            } else {
	            	success = tests;
	            }
	            
	            totalTestSuites = totalTestSuites + tests;
	            failureTestSuites = failureTestSuites + failures;
	            errorTestSuites = errorTestSuites + errors;
	            successTestSuites = successTestSuites + success;
	            String rstValues = tests + "," + success + "," + failures + "," + errors;
	            S_LOGGER.debug("rstValues ====> " + rstValues);
	            AllTestSuite allTestSuiteDetail = new AllTestSuite(testSuite.getName(), tests, success, failures, errors);
	            allTestSuiteDetails.add(allTestSuiteDetail);
	            
				testSuite.setTestCases(testCases);
				testSuiteWithTestCase.add(testSuite);
			}
		}
		// detailed info
		sureFireReport.setTestSuites(testSuiteWithTestCase);
//		printDetailedObj(testSuiteWithTestCase);
		//crisp info
		sureFireReport.setAllTestSuites(allTestSuiteDetails);
		return sureFireReport;
	}
	
	//detailed object info
	private void printDetailedObj(ArrayList<TestSuite> testSuiteWithTestCase) {
		S_LOGGER.debug("printing required values!!!!!");
		for (TestSuite testSuite : testSuiteWithTestCase) {
			S_LOGGER.debug("getName " + testSuite.getName() + " tests " + testSuite.getTests() + " Failure " + testSuite.getFailures() + " Error" + testSuite.getErrors() + " testcases size " + testSuite.getTestCases().size());
		}
	}
	
    private List<TestSuite> getTestSuite(Document doc) throws TransformerException, PhrescoException {
        try {
            String testSuitePath = null;
    		if (UNIT.equals(testType)) {
    			testSuitePath = reportPaths.getUnitTestSuitePath(techId);
    		} else {
    			testSuitePath = reportPaths.getFunctionalTestSuitePath(techId);
    		}
    		S_LOGGER.debug("testSuitePath " + testSuitePath);
            NodeList nodelist = org.apache.xpath.XPathAPI.selectNodeList(doc, XPATH_MULTIPLE_TESTSUITE);
            if (nodelist.getLength() == 0) {
                nodelist = org.apache.xpath.XPathAPI.selectNodeList(doc, testSuitePath);
            }

            List<TestSuite> testSuites = new ArrayList<TestSuite>(2);
            TestSuite testSuite = null;

            for (int i = 0; i < nodelist.getLength(); i++) {
                testSuite =  new TestSuite();
                Node node = nodelist.item(i);
                NamedNodeMap nameNodeMap = node.getAttributes();

                for (int k = 0; k < nameNodeMap.getLength(); k++){
                    Node attribute = nameNodeMap.item(k);
                    String attributeName = attribute.getNodeName();
                    String attributeValue = attribute.getNodeValue();

                    if (ATTR_ASSERTIONS.equals(attributeName)) {
                        testSuite.setAssertions(attributeValue);
                    } else if (ATTR_ERRORS.equals(attributeName)) {
                        testSuite.setErrors(Float.parseFloat(attributeValue));
                    } else if (ATTR_FAILURES.equals(attributeName)) {
                        testSuite.setFailures(Float.parseFloat(attributeValue));
                    } else if (ATTR_FILE.equals(attributeName)) {
                        testSuite.setFile(attributeValue);
                    } else if (ATTR_NAME.equals(attributeName)) {
                        testSuite.setName(attributeValue);
                    } else if (ATTR_TESTS.equals(attributeName)) {
                        testSuite.setTests(Float.parseFloat(attributeValue));
                    } else if (ATTR_TIME.equals(attributeName)) {
                        testSuite.setTime(attributeValue);
                    }
                }
                testSuites.add(testSuite);
            }
            return testSuites;
        } catch (Exception e) {
            throw new PhrescoException(e);
        }
    }

    private List<TestCase> getTestCases(Document doc, String testSuiteName) throws TransformerException, PhrescoException {
        try {
            String testCasePath = null;
            String testSuitePath = null;
    		if (UNIT.equals(testType)) {
                testSuitePath = reportPaths.getUnitTestSuitePath(techId);
    		} else {
                testSuitePath = reportPaths.getFunctionalTestSuitePath(techId);
    		}
    		testCasePath = reportPaths.getTestCasePath(techId);
    		S_LOGGER.debug("testSuitePath " + testSuitePath);
    		S_LOGGER.debug("testCasePath " + testCasePath);
            StringBuilder sb = new StringBuilder(); //testsuites/testsuite[@name='yyy']/testcase
            //sb.append(XPATH_SINGLE_TESTSUITE);
            sb.append(testSuitePath);
            sb.append(NAME_FILTER_PREFIX);
            sb.append(testSuiteName);
            sb.append(NAME_FILTER_SUFIX);
            sb.append(testCasePath);
            //sb.append(XPATH_TESTCASE);

            NodeList nodelist = org.apache.xpath.XPathAPI.selectNodeList(doc, sb.toString());
            
            // For tehnologies like php and drupal duoe to plugin change xml testcase path modified
            if (nodelist.getLength() == 0) {
                StringBuilder sbMulti = new StringBuilder();
                sbMulti.append(testSuitePath);
                sbMulti.append(NAME_FILTER_PREFIX);
                sbMulti.append(testSuiteName);
                sbMulti.append(NAME_FILTER_SUFIX);
                sbMulti.append(XPATH_TESTSUTE_TESTCASE);
                nodelist = org.apache.xpath.XPathAPI.selectNodeList(doc, sbMulti.toString());
            }
            
            // For technology sharepoint
            if (nodelist.getLength() == 0) {
                StringBuilder sbMulti = new StringBuilder(); //testsuites/testsuite[@name='yyy']/testcase
                sbMulti.append(XPATH_MULTIPLE_TESTSUITE);
                sbMulti.append(NAME_FILTER_PREFIX);
                sbMulti.append(testSuiteName);
                sbMulti.append(NAME_FILTER_SUFIX);
                //sbMulti.append(XPATH_TESTCASE);
                sbMulti.append(testCasePath);
                nodelist = org.apache.xpath.XPathAPI.selectNodeList(doc, sbMulti.toString());
            }

            List<TestCase> testCases = new ArrayList<TestCase>();

            int failureTestCases = 0;
            int errorTestCases = 0;

            for (int i = 0; i < nodelist.getLength(); i++) {
                Node node = nodelist.item(i);
                NodeList childNodes = node.getChildNodes();
                NamedNodeMap nameNodeMap = node.getAttributes();
                TestCase testCase = new TestCase();

                if (childNodes != null && childNodes.getLength() > 0) {

                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node childNode = childNodes.item(j);

                        if (ELEMENT_FAILURE.equals(childNode.getNodeName())) {
                        	failureTestCases++;
                            TestCaseFailure failure = getFailure(childNode);
                            if (failure != null) {
                                testCase.setTestCaseFailure(failure);
                            } 
                        }

                        if (ELEMENT_ERROR.equals(childNode.getNodeName())) {
                        	errorTestCases++;
                            TestCaseError error = getError(childNode);
                            if (error != null) {
                                testCase.setTestCaseError(error);
                            }
                        }
                    }
                }

                for (int k = 0; k < nameNodeMap.getLength(); k++){
                    Node attribute = nameNodeMap.item(k);
                    String attributeName = attribute.getNodeName();
                    String attributeValue = attribute.getNodeValue();
                    if (ATTR_NAME.equals(attributeName)) {
                        testCase.setName(attributeValue);
                    } else if (ATTR_CLASS.equals(attributeName) || ATTR_CLASSNAME.equals(attributeName)) {
                        testCase.setTestClass(attributeValue);
                    } else if (ATTR_FILE.equals(attributeName)) {
                        testCase.setFile(attributeValue);
                    } else if (ATTR_LINE.equals(attributeName)) {
                        testCase.setLine(Float.parseFloat(attributeValue));
                    } else if (ATTR_ASSERTIONS.equals(attributeName)) {
                        testCase.setAssertions(Float.parseFloat(attributeValue));
                    } else if (ATTR_TIME.equals(attributeName)) {
                        testCase.setTime(attributeValue);
                    } 
                }
                testCases.add(testCase);
            }
            
            setNoOfTstSuiteFailures(failureTestCases);
            setNoOfTstSuiteErrors(errorTestCases);
            setNoOfTstSuiteTests(nodelist.getLength());
            return testCases;
        } catch (Exception e) {
            throw new PhrescoException(e);
        }
    }
    
    private TestCaseFailure getFailure(Node failureNode) throws TransformerException {
        TestCaseFailure failure = new TestCaseFailure();
        try {
            failure.setDescription(failureNode.getTextContent());
            failure.setFailureType(REQ_TITLE_EXCEPTION);
            NamedNodeMap nameNodeMap = failureNode.getAttributes();

            if (nameNodeMap != null && nameNodeMap.getLength() > 0) {
                for (int k = 0; k < nameNodeMap.getLength(); k++){
                    Node attribute = nameNodeMap.item(k);
                    String attributeName = attribute.getNodeName();
                    String attributeValue = attribute.getNodeValue();

                    if (ATTR_TYPE.equals(attributeName)) {
                        failure.setFailureType(attributeValue);
                    }
                }
            }
        } catch (Exception e) {
        	S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.getFailure()" + FrameworkUtil.getStackTraceAsString(e));
        }
        return failure;
    }

    private TestCaseError getError(Node errorNode) throws TransformerException {
        TestCaseError tcError = new TestCaseError();
        try {
            tcError.setDescription(errorNode.getTextContent());
            tcError.setErrorType(REQ_TITLE_ERROR);
            NamedNodeMap nameNodeMap = errorNode.getAttributes();

            if (nameNodeMap != null && nameNodeMap.getLength() > 0) {
                for (int k = 0; k < nameNodeMap.getLength(); k++){
                    Node attribute = nameNodeMap.item(k);
                    String attributeName = attribute.getNodeName();
                    String attributeValue = attribute.getNodeValue();

                    if (ATTR_TYPE.equals(attributeName)) {
                        tcError.setErrorType(attributeValue);
                    }
                }
            }
        } catch (Exception e) {
        	S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.getError()" + FrameworkUtil.getStackTraceAsString(e));
        }
        return tcError;
    }
    
	public static JmeterReport getPerformanceReport(Document document, String fileName, String techId, String deviceId) throws Exception {  // deviceid is the tag name for android
		JmeterReport jmeterReport = new JmeterReport();
		List<PerformanceTestResult> performanceTestResultOfFile = new ArrayList<PerformanceTestResult>();
		String xpath = "/*/*";	// For other technologies
		String device = "*";
		if(StringUtils.isNotEmpty(deviceId)) {
			device = "deviceInfo[@id='" + deviceId + "']";
		}
		if(TechnologyTypes.ANDROIDS.contains(techId)) {
			xpath = "/*/" + device + "/*";
		}
		NodeList nodeList = org.apache.xpath.XPathAPI.selectNodeList(document, xpath);
		Map<String, PerformanceTestResult> results = new LinkedHashMap<String, PerformanceTestResult>(100);
		double maxTs = 0;
		double minTs = 0;
		int lastTime = 0;
		int noOfSamples = nodeList.getLength();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			NamedNodeMap nameNodeMap = node.getAttributes();
			Node timeAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_JM_TIME);
			int time = Integer.parseInt(timeAttr.getNodeValue());
			Node bytesAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_JM_BYTES);
			int bytes = Integer.parseInt(bytesAttr.getNodeValue());
			Node successFlagAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_JM_SUCCESS_FLAG);
			boolean success = Boolean.parseBoolean(successFlagAttr.getNodeValue()) ? true : false ;
			Node labelAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_JM_LABEL);
			String label = labelAttr.getNodeValue();
			Node timeStampAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_JM_TIMESTAMP);
			double timeStamp = Long.parseLong(timeStampAttr.getNodeValue());
			boolean firstEntry = false;

			PerformanceTestResult performanceTestResult = results.get(label);
			if (performanceTestResult == null) {
				performanceTestResult = new PerformanceTestResult();
				firstEntry = true;
			} else {
				firstEntry = false;
			}

			performanceTestResult.setLabel(label.trim());
			performanceTestResult.setNoOfSamples(performanceTestResult.getNoOfSamples() + 1);
			performanceTestResult.getTimes().add(time);
			performanceTestResult.setTotalTime(performanceTestResult.getTotalTime() + time);
			performanceTestResult.setTotalBytes(performanceTestResult.getTotalBytes() + bytes);

			if (time < performanceTestResult.getMin() || firstEntry) {
				performanceTestResult.setMin(time);
			}

			if (time > performanceTestResult.getMax()) {
				performanceTestResult.setMax(time);
			}

			// Error calculation
			if (!success) {
				performanceTestResult.setErr(performanceTestResult.getErr() + 1);
			}

			// Throughput calculation

			if (timeStamp >= performanceTestResult.getMaxTs()) {
				performanceTestResult.setMaxTs(timeStamp);
				performanceTestResult.setLastTime(time);
			}

			if(i == 0 || (performanceTestResult.getMaxTs() > maxTs)) {
				maxTs = performanceTestResult.getMaxTs();
				lastTime = performanceTestResult.getLastTime();
			}

			if (timeStamp < performanceTestResult.getMinTs() || firstEntry) {
				performanceTestResult.setMinTs(timeStamp);
			}

			if(i == 0 ) {
				minTs = performanceTestResult.getMinTs();
			} else if(performanceTestResult.getMinTs() < minTs) {
				minTs = performanceTestResult.getMinTs();
			}

			Double calThroughPut = new Double(performanceTestResult.getNoOfSamples());
			calThroughPut = calThroughPut / (performanceTestResult.getMaxTs() + performanceTestResult.getLastTime() -
					performanceTestResult.getMinTs());
			double throughPut = calThroughPut * 1000;

			performanceTestResult.setThroughtPut(throughPut);

			results.put(label, performanceTestResult);
		}
		
		// Total Throughput calculation
		double totalThroughput = (noOfSamples /((maxTs + lastTime) - minTs)) * 1000;
		double stdDev = setStdDevToResults(results);
		
		// Getting all performance result objects
		// calculating total values
		int totalValue = results.keySet().size();
		int NoOfSample = 0; 
		double avg = 0; 
  		int min = 0;
  		int max = 0;
  		double StdDev = 0;
  		int Err = 0;
  		double KbPerSec = 0;
  		double sumOfBytes = 0;
  		int i = 1;
  		
		for (String key : results.keySet()) {
			PerformanceTestResult performanceTestResult = results.get(key);
			performanceTestResultOfFile.add(performanceTestResult);
			
			// calculating min,max, avgbytes, kbsec
        	NoOfSample = NoOfSample + performanceTestResult.getNoOfSamples();
        	avg = avg + performanceTestResult.getAvg();
        	if (i == 1) {
        		min = performanceTestResult.getMin();
        		max = performanceTestResult.getMax();
        	}
        	if (i != 1 && performanceTestResult.getMin() < min) {
        		min = performanceTestResult.getMin();
        	}
        	if (i != 1 && performanceTestResult.getMax() > max) {
        		max = performanceTestResult.getMax();
        	}
        	StdDev = StdDev + performanceTestResult.getStdDev();
        	Err = Err + performanceTestResult.getErr();
        	sumOfBytes = sumOfBytes + performanceTestResult.getAvgBytes();
        	
        	i++;
		}
		// Calculation of avg bytes of a file
      	double avgBytes = sumOfBytes / totalValue;
      	KbPerSec = (avgBytes / 1024) * totalThroughput;
		
		// setting performance file name and list objects
		jmeterReport.setFileName(fileName);
		jmeterReport.setJmeterTestResult(performanceTestResultOfFile);
		jmeterReport.setTotalThroughput(FrameworkUtil.roundFloat(2,totalThroughput)+"");
		jmeterReport.setTotalStdDev(FrameworkUtil.roundFloat(2,stdDev)+"");
		jmeterReport.setTotalNoOfSample(NoOfSample+"");
		jmeterReport.setTotalAvg((avg/totalValue)+"");
		jmeterReport.setMin(min+"");
		jmeterReport.setMax(max+"");
		jmeterReport.setTotalErr((Err/totalValue)+"");
		jmeterReport.setTotalAvgBytes(avgBytes+"");
		jmeterReport.setTotalKbPerSec(FrameworkUtil.roundFloat(2,KbPerSec)+"");
		
		return jmeterReport;
	}

	private static double setStdDevToResults(Map<String, PerformanceTestResult> results) {
		Set<String> keySet = results.keySet();
		long xBar = 0;  		//XBar Calculation
		long sumOfTime = 0;
		int totalSamples = 0;
		double sumMean = 0;
		List<Integer> allTimes = new ArrayList<Integer>();
		for (String key : keySet) {
			PerformanceTestResult performanceTestResult = results.get(key);
			// calculation of average time
			double avg = performanceTestResult.getTotalTime() / performanceTestResult.getNoOfSamples();
			sumOfTime = sumOfTime + performanceTestResult.getTotalTime();
			totalSamples = totalSamples + performanceTestResult.getNoOfSamples();
			performanceTestResult.setAvg(avg);

			// calculation of average bytes
			double avgBytes = (double) performanceTestResult.getTotalBytes() / performanceTestResult.getNoOfSamples();
			performanceTestResult.setAvgBytes(Math.round(avgBytes));
			// KB/Sec calculation
			Double calKbPerSec = new Double(performanceTestResult.getThroughtPut());
			calKbPerSec = calKbPerSec * (((double) avgBytes) / 1024)   ;
			performanceTestResult.setKbPerSec(calKbPerSec);

			// Std.Dev calculation
			List<Integer> times = performanceTestResult.getTimes();
			allTimes.addAll(times);
			Double totalMean = new Double(0);
			for (Integer time : times) {
				totalMean += Math.pow(time - avg, 2);
			}
			performanceTestResult.setStdDev((float) Math.sqrt(totalMean / performanceTestResult.getNoOfSamples()));

			performanceTestResult.setErr((performanceTestResult.getErr() / performanceTestResult.getNoOfSamples()) * 100);
		}

		//Total Std.Dev calculation
		xBar = sumOfTime / totalSamples;
		for (Integer time : allTimes) {
			sumMean += Math.pow(time - xBar, 2);
		}
		double stdDev = Math.sqrt(sumMean / totalSamples);
		return stdDev;
	}
	
	
    private List<TestResult> getLoadTestResult(Document doc) throws TransformerException, PhrescoException, ParserConfigurationException, SAXException, IOException {
    	 List<TestResult> testResults = new ArrayList<TestResult>(2);
	     try {
	         NodeList nodeList = org.apache.xpath.XPathAPI.selectNodeList(doc, XPATH_TEST_RESULT);
	         TestResult testResult = null;
	         for (int i = 0; i < nodeList.getLength(); i++) {
	             testResult =  new TestResult();
	             Node node = nodeList.item(i);
	             //              NodeList childNodes = node.getChildNodes();
	             NamedNodeMap nameNodeMap = node.getAttributes();
	
	             for (int k = 0; k < nameNodeMap.getLength(); k++) {
	                 Node attribute = nameNodeMap.item(k);
	                 String attributeName = attribute.getNodeName();
	                 String attributeValue = attribute.getNodeValue();
	
	                 if (ATTR_JM_TIME.equals(attributeName)) {
	                     testResult.setTime(Integer.parseInt(attributeValue));
	                 } else if (ATTR_JM_LATENCY_TIME.equals(attributeName)) {
	                     testResult.setLatencyTime(Integer.parseInt(attributeValue));
	                 } else if (ATTR_JM_TIMESTAMP.equals(attributeName)) {
	                     Date date = new Date(Long.parseLong(attributeValue));
	                     DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
	                     String strDate = format.format(date);
	                     testResult.setTimeStamp(strDate);
	                 } else if (ATTR_JM_SUCCESS_FLAG.equals(attributeName)) {
	                     testResult.setSuccess(Boolean.parseBoolean(attributeValue));
	                 } else if (ATTR_JM_LABEL.equals(attributeName)) {
	                     testResult.setLabel(attributeValue);
	                 } else if (ATTR_JM_THREAD_NAME.equals(attributeName)) {
	                     testResult.setThreadName(attributeValue);
	                 }
	             }
	             testResults.add(testResult);
	         }
	     } catch (Exception e) {
	    	 S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.getLoadTestResult()" + FrameworkUtil.getStackTraceAsString(e));
	     }
	     return testResults;
    }
    private Document getDocumentOfFile(String path, String fileName) {
    	Document doc = null;
        InputStream fis = null;
        DocumentBuilder builder = null;
        try {
            fis = new FileInputStream(new File(path + "/" + fileName)); // here should be new File(path + "/" + selectedTestResultFileName);
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();
            doc = builder.parse(fis);
        } catch (Exception e) {
        	S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.getDocumentOfFile()" + FrameworkUtil.getStackTraceAsString(e));
		} finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                	S_LOGGER.error("Entering into catch block of PhrescoReportGeneration.getDocumentOfFile()" + FrameworkUtil.getStackTraceAsString(e));
                }
            }
        }
		return doc;
    }
    
   
    private List<String> getTestResultFiles(String path) {
        File testDir = new File(path);
        List<String> testResultFileNames = new ArrayList<String>();
        if(testDir.isDirectory()){
            FilenameFilter filter = new PhrescoFileFilter("", XML);
            File[] listFiles = testDir.listFiles(filter);
            for (File file : listFiles) {
                if (file.isFile()) {
                    testResultFileNames.add(file.getName());
                }
            }
        }
        return testResultFileNames;
    }

	public static Map<String, String> getDeviceNames(Document document)  throws Exception {
		NodeList nodeList = org.apache.xpath.XPathAPI.selectNodeList(document, "/*/*");
		Map<String, String> deviceList = new LinkedHashMap<String, String>(100);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			NamedNodeMap nameNodeMap = node.getAttributes();
			String deviceId = "";
			String deviceName = "";
			Node idAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_ID);
			deviceId = idAttr.getNodeValue();
			Node nameAttr = nameNodeMap.getNamedItem(FrameworkConstants.ATTR_NAME);
			deviceName = nameAttr.getNodeValue();
			deviceList.put(deviceId, deviceName);
		}
		return deviceList;
	}
	
	public float getNoOfTstSuiteTests() {
		return noOfTstSuiteTests;
	}

	public void setNoOfTstSuiteTests(float noOfTstSuiteTests) {
		this.noOfTstSuiteTests = noOfTstSuiteTests;
	}

	public float getNoOfTstSuiteFailures() {
		return noOfTstSuiteFailures;
	}

	public void setNoOfTstSuiteFailures(float noOfTstSuiteFailures) {
		this.noOfTstSuiteFailures = noOfTstSuiteFailures;
	}

	public float getNoOfTstSuiteErrors() {
		return noOfTstSuiteErrors;
	}

	public void setNoOfTstSuiteErrors(float noOfTstSuiteErrors) {
		this.noOfTstSuiteErrors = noOfTstSuiteErrors;
	}

	public String getTechName() {
		return techName;
	}

	public void setTechName(String techName) {
		this.techName = techName;
	}
    
	public String getProjectCode() {
	    return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
	    this.projectCode = projectCode;
	}
	
	public String getCodeTechnology() {
		return codeTechnology;
	}
	
	public void setCodeTechnology(String codeTechnology) {
		this.codeTechnology = codeTechnology;
	}
	
	public String getReport() {
		return report;
	}
	
	public void setReport(String report) {
		this.report = report;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getSkipTest() {
		return skipTest;
	}
	
	public void setSkipTest(String skipTest) {
		this.skipTest = skipTest;
	}
	
	public String getValidateAgainst() {
		return validateAgainst;
	}
	
	public void setValidateAgainst(String validateAgainst) {
		this.validateAgainst = validateAgainst;
	}
}

class PhrescoFileFilter implements FilenameFilter {
	private String name;
	private String extension;

	public PhrescoFileFilter(String name, String extension) {
		this.name = name;
		this.extension = extension;
	}

	public boolean accept(File directory, String filename) {
		boolean fileOK = true;

		if (name != null) {
			fileOK &= filename.startsWith(name);
		}

		if (extension != null) {
			fileOK &= filename.endsWith('.' + extension);
		}
		return fileOK;
	}
}

class FileExtensionFileFilter implements FilenameFilter {
    private String filter_;
    public FileExtensionFileFilter(String filter) {
        filter_ = filter;
    }

    public boolean accept(File dir, String name) {
        return name.endsWith(filter_);
    }
}