/*
 * ###
 * Phresco Service Tools
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
package com.photon.phresco.service.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.codehaus.plexus.util.StringUtils;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Documentation;
import com.photon.phresco.model.Documentation.DocumentationType;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.service.model.ArtifactInfo;
import com.photon.phresco.service.model.DocumentType;
import com.photon.phresco.util.TechnologyTypes;
import com.sun.jersey.api.client.ClientResponse;

public class JsLibraryGenerator {

	private static final String id1 = "jslib_";
	private static final int noOfRowsToSkip = 1;
	private static final String JSLIBRARY = "JSLibraries";
	private static final String DELIMITER = ",";
	static final Map<String, String> INPUT_EXCEL_MAP = new HashMap<String, String>(16);
	private static final String JS_LIBRARY_EXCEL_FILE = "PHTN_PHRESCO_JS-Libraries.xls";
	private static final String OUTPUT_XML_FILE = "jslibrary.xml";
	private RepositoryManager manager;
	private HSSFWorkbook workBook;
	private File outFile;
	private File inputDir;
	private File binariesDir;
	private ServiceManager serviceManager = null;
	private ServiceContext context = null;

	static final Map<String, File> contentsHomeMap = new HashMap<String, File>(
			16);


	public JsLibraryGenerator(File inputFileDir, File outDir, File binariesDir2)
			throws PhrescoException {
		this.outFile = outDir;
		this.inputDir = inputFileDir;
		this.binariesDir = binariesDir2;
		PhrescoServerFactory.initialize();
		this.manager = PhrescoServerFactory.getRepositoryManager();
		PhrescoServerFactory.initialize();
		initContentsHomeMap();
		initInputExcelMap();
		context = new ServiceContext();
        context.put(ServiceClientConstant.SERVICE_URL, "http://localhost:3030/service/rest");
        context.put(ServiceClientConstant.SERVICE_USERNAME, "demouser");
        context.put(ServiceClientConstant.SERVICE_PASSWORD, "phresco");
	}
	
	private static void initInputExcelMap() {
//	    INPUT_EXCEL_MAP.put(TechnologyTypes.PHP,"PHTN_PHRESCO_JS-Libraries.xls");
	    INPUT_EXCEL_MAP.put(TechnologyTypes.ANDROID_HYBRID,"PHTN_PHRESCO_JS-Libraries.xls");
	    INPUT_EXCEL_MAP.put(TechnologyTypes.IPHONE_HYBRID,"PHTN_PHRESCO_JS-Libraries.xls");
	    INPUT_EXCEL_MAP.put(TechnologyTypes.IPHONE_NATIVE,"PHTN_PHRESCO_JS-Libraries.xls");
        INPUT_EXCEL_MAP.put(TechnologyTypes.HTML5_MOBILE_WIDGET,"PHTN_PHRESCO_Html5_Mobile_Widget_JS-Libraries.xls");
        INPUT_EXCEL_MAP.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET,"PHTN_PHRESCO_Html5_Mobile_Widget_JS-Libraries.xls");
        INPUT_EXCEL_MAP.put(TechnologyTypes.HTML5_WIDGET,"PHTN_PHRESCO_Html5_Mobile_Widget_JS-Libraries.xls");
       // INPUT_EXCEL_MAP.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, "PHTN_PHRESCO_Html5_Jquery_Widget_JS-Libraries.xls");
       // INPUT_EXCEL_MAP.put(TechnologyTypes.HTML5_WIDGET, "PHTN_PHRESCO_Html5_Yui_Widget_JS-Libraries.xls");
    }
	
	private  void initContentsHomeMap() {
		File projectRootDir = new File(binariesDir, "/technologies/");
		contentsHomeMap.put("js-libraries", new File(projectRootDir, "js-libraries"));
	}

	public void publish() throws PhrescoException {
	    for (String tech : INPUT_EXCEL_MAP.keySet()) {
            generate(tech);
        }
	}


	private void generate(String tech) throws PhrescoException {
		ModuleGroup moduleGroup = new ModuleGroup();
		List<ModuleGroup> jsLibs = new ArrayList<ModuleGroup>();
        workBook = getWorkBook(tech);
        HSSFSheet sheet = workBook.getSheet(JSLIBRARY);
        Iterator<Row> rowIterator = sheet.rowIterator();
        for (int i = 0; i < noOfRowsToSkip; i++) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            moduleGroup = createJSLibrary(row, tech);
            jsLibs.add(moduleGroup);
        }
        serviceManager = ServiceClientFactory.getServiceManager(context);
		RestClient<ModuleGroup> techClient = serviceManager.getRestClient("component/modules");
		ClientResponse response = techClient.create(jsLibs);
		System.out.println(tech + "Uploaded Successfully................");
		System.out.println(response.getStatus());
//        writeTo(libraries, tech);
//        uploadToRepository(libraries, tech);
        
    }

    private HSSFWorkbook getWorkBook(String tech) throws PhrescoException  {
        FileInputStream fs = null;
        try {
           fs = new FileInputStream(new File(inputDir, INPUT_EXCEL_MAP.get(tech)));
           workBook = new HSSFWorkbook(fs);
        } catch (Exception e) {
            throw new PhrescoException();
        }
        return workBook;
    }

//    private void uploadToRepository(Libraries libraries, String tech)
//			throws PhrescoException {
//
//		ArtifactInfo info = new ArtifactInfo("jslibraries", tech, "",
//				"xml", "0.1");
//		manager.addArtifact(info, new File(outFile, tech + ".xml"));
//
//	}

//	private void writeTo(Libraries libraries, String tech) throws PhrescoException {
//		try {
//			JAXBContext jaxbContext = JAXBContext
//					.newInstance("com.photon.phresco.service.jaxb");
//			Marshaller marshaller = jaxbContext.createMarshaller();
//			JAXBElement<Libraries> jaxbApptype = new ObjectFactory()
//					.createLibraries(libraries);
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
//					Boolean.TRUE);
//			marshaller.marshal(jaxbApptype, new File(outFile, tech + ".xml"));
//		} catch (PropertyException e) {
//			throw new PhrescoException(e);
//		} catch (JAXBException e) {
//			throw new PhrescoException(e);
//		}
//	}

	private String getValue(Cell cell) {
		if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
			return cell.getStringCellValue();
		}

		if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			return String.valueOf(cell.getNumericCellValue());
		}

		return null;
	}

	private ModuleGroup createJSLibrary(Row row, String techId) throws PhrescoException {
		ModuleGroup moduleGroup = new ModuleGroup();
		Cell serialNo = row.getCell(0);
		if (serialNo == null || Cell.CELL_TYPE_BLANK == serialNo.getCellType()) {
			return null;
		}

		String name = row.getCell(1).getStringCellValue();
		String[] versions = new String[]{"1.0"};
		Cell versionCell = row.getCell(2);
		if (versionCell != null && Cell.CELL_TYPE_BLANK != versionCell.getCellType()) {
			String version = getValue(versionCell);
			versions = StringUtils.split(version,DELIMITER);
		}
		
		Boolean req = false;
		Cell required = row.getCell(9);
        if (required != null && Cell.CELL_TYPE_BLANK != required.getCellType()) {
            req = convertBoolean(getValue(required));
        }
        
		String identifier = id1
				+ row.getCell(1).getStringCellValue().toLowerCase();
		String no = String.valueOf(identifier);

		List<Documentation> documentations = new ArrayList<Documentation>();
		Documentation documents ;
		Cell helptext = row.getCell(7);
		if (helptext != null && Cell.CELL_TYPE_BLANK != helptext.getCellType()) {
			documents = new Documentation();
			documents.setContent(helptext.getStringCellValue());
			documents.setType(DocumentationType.HELP_TEXT);
			documentations.add(documents);
		}
		
		Cell description = row.getCell(4);
        if (description != null && Cell.CELL_TYPE_BLANK != description.getCellType()) {
        	documents = new Documentation();
        	documents.setContent(description.getStringCellValue());
        	documents.setType(DocumentationType.DESCRIPTION);
        	documentations.add(documents);
        }
        String fileExt = "zip";
		Cell filenameCell = row.getCell(8);
		if (filenameCell != null
				&& Cell.CELL_TYPE_BLANK != filenameCell.getCellType()) {

			String filePath = filenameCell.getStringCellValue().trim();

			
			if (filePath.endsWith(".tar.gz")) {
				fileExt = "tar.gz";
			} else if (filePath.endsWith(".tar")) {
				fileExt = "tar";
			} else if (filePath.endsWith(".zip")) {
				fileExt = "zip";
			} else if (filePath.endsWith(".jar")) {
				fileExt = "jar";
			}

			System.out.println("Uploading jslibrary : "	+ filePath);
	//		publishJSLibrary(library, filePath, fileExt ,filePath);
		}
		List<Module> moduleVersions = createModules(name, versions, fileExt, no);
		moduleGroup.setModuleId(no);
		moduleGroup.setName(name);
		moduleGroup.setRequired(req);
		moduleGroup.setTechId(techId);
		moduleGroup.setType("js");
		moduleGroup.setDocs(documentations);
		return moduleGroup;
	}


	private List<Module> createModules(String name, String[] versions,
			String fileExt, String no) {
		Module module = new Module();
		for (String version : versions) {
			module.setName(name);
			module.setContentType(fileExt);
			String contentUrl = "/jslibraries/files/" + no +"/"+ version + "/"+ no + "-" + version + "." + fileExt;
			module.setContentURL(contentUrl);
		}
		return null;
	}

	private Boolean convertBoolean(String value) {
	    if ("Yes".equalsIgnoreCase(value)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

//    private void publishJSLibrary(Library library, String fileURL, String ext ,String filepath)
//			throws PhrescoException {
//		File root = contentsHomeMap.get("js-libraries");
//		File artifact = new File(root, filepath);
//		System.out.println("URL = " + artifact.getPath());
//		if (!artifact.exists()) {
//			System.out.println("Library not exist : " + library.getName()	+ " filePath: " + fileURL);
//			System.out.println("artifact.toString() " + artifact.toString());
//			return;
//		}
//
//		ArtifactInfo info = new ArtifactInfo("jslibraries." + ".files",
//				library.getId(), "", ext, library.getVersion());
//		manager.addArtifact(info, artifact);
//		System.out.println("library : " + library.getName() + " filePath: "
//				+ fileURL + " added succesfully");
//	}
//
//	private Library createJslibrary(String id, String mod, String version, Boolean req) {
//		Library library = new Library();
//		library.setId(id);
//		library.setName(mod);
//		library.setVersion(version);
//		library.setRequired(req);
//		return library;
//	}

	public static void main(String[] args) throws PhrescoException, IOException {
		//File inputFile = new File(
				//"D:\\work\\phresco\\source\\service\\trunk\\phresco-service-runner\\delivery\\tools\\files\\");
		File inputFile = new File("D:\\work\\phresco\\master\\service\\phresco-service-runner\\delivery\\tools\\files\\");

		//File outFile = new File(
				//"D:\\work\\phresco\\source\\service\\trunk\\phresco-service-runner\\delivery\\tools\\files\\");
		File outFile = new File("D:\\");
		File binariesFileDir = new File("D:\\work\\phresco\\Phresco-binaries\\");
		JsLibraryGenerator gen = new JsLibraryGenerator(inputFile, outFile, binariesFileDir);
		gen.publish();
	}

}