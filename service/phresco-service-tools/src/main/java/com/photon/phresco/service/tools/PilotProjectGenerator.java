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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.codehaus.plexus.util.StringUtils;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.api.PhrescoServerFactory;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.data.api.PhrescoDataManager;
import com.photon.phresco.service.model.PilotTechModuleVersions;
import com.photon.phresco.service.model.PilotTechModules;
import com.photon.phresco.service.model.PilotTechnology;
import com.photon.phresco.util.TechnologyTypes;

public class PilotProjectGenerator {
	private static final String SHEET_NAME_MODULE = "Modules";
	private static final String SHEET_NAME_PILOTS = "Pilots";
	private String SHEET_NAME_JSLIBRARY = "JSLibraries";
	private static final int NO_OF_ROWS_TO_SKIP = 1;
	private static final String DELIMITER = ",";
	private static final String ID = "mod_";
	private static final String ID1 = "js_";


	static final Map<String, String> INPUT_EXCEL_MAP = new HashMap<String, String>(
			16);
	static final Map<String, String[]> NAME_AND_DEP_MAP = new HashMap<String, String[]>(
			16);
	static final Map<Double, String> SNO_AND_NAME_MAP = new HashMap<Double, String>(
			16);
	static final Map<Double, String> SNO_AND_NAME = new HashMap<Double, String>(
			16);
	static final Map<Double, String> SNO_AND_VERSION = new HashMap<Double, String>(
			16);
	static final Map<Double, String> JSSNO_AND_NAME_MAP = new HashMap<Double, String>(
			16);
	static final Map<Double, String> JSSNO_AND_NAME = new HashMap<Double, String>(
			16);
	static final Map<Double, String> JSSNO_AND_VERSION = new HashMap<Double, String>(
			16);
	private static final String STR_UNDER_SCORE = "_";

	RepositoryManager repManager = null;
	private File inputRootDir;
	private File outputRootDir;
	private PhrescoDataManager dataManager = null;
	private int pilotInfoTracker =1;
	private int pilotTechlogyTracker = 1;
	private int pilotTechModulesTracker = 1;
	private int pilotTechModuleVersionTracker = 1;
	//private int techlogyTracker = 1;
	//private int techlogyTracker = 1;
	//private int techlogyTracker = 1;


	static {
		initInputExcelMap();
	}

	private PilotProjectGenerator(File inputRootDir, File outputRootDir) throws PhrescoException {
		this.inputRootDir = inputRootDir;
		this.outputRootDir = outputRootDir;
		PhrescoServerFactory.initialize();
		dataManager = PhrescoServerFactory.getPhrescoDataManager();
	}

	private static void initInputExcelMap() {
//		 inputExcelMap.put(TechnologyTypes.PHP,"PHTN_PHRESCO_PHP.xls");
//		 inputExcelMap.put(TechnologyTypes.JAVA_WEBSERVICE,"PHTN_PHRESCO_Java-WebService.xls");
//		 inputExcelMap.put(TechnologyTypes.PHP_DRUPAL7,"PHTN_PHRESCO_Drupal7.xls");
//		 inputExcelMap.put(TechnologyTypes.SHAREPOINT,"PHTN_PHRESCO_Sharepoint.xls");
//		 inputExcelMap.put(TechnologyTypes.ANDROID_NATIVE,"PHTN_PHRESCO_Andriod-Native.xls");
//		 inputExcelMap.put(TechnologyTypes.IPHONE_NATIVE,"PHTN_PHRESCO_iPhone-Native.xls");
		INPUT_EXCEL_MAP.put(TechnologyTypes.NODE_JS_WEBSERVICE,"PHTN_PHRESCO_NodeJS-WebService.xls");
//		 inputExcelMap.put(TechnologyTypes.ANDROID_HYBRID,"PHTN_PHRESCO_Andriod-Hybrid.xls");
//		 inputExcelMap.put(TechnologyTypes.IPHONE_NATIVE,"PHTN_PHRESCO_iPhone-Web.xls");
//		 inputExcelMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET,"PHTN_PHRESCO_HTML5-Widget.xls");
//		 inputExcelMap.put(TechnologyTypes.HTML5_WIDGET,"PHTN_PHRESCO_HTML5-Widget.xls");
	}

	private  File getExcelFile(String techType) throws PhrescoException {
		String fileName = INPUT_EXCEL_MAP.get(techType);
		if (fileName == null) {
			throw new PhrescoException("No file defined for " + techType);
		}
		return new File(inputRootDir, fileName);
	}

	private void generatepilot(HSSFWorkbook workBook, String tech)
			throws PhrescoException {
		System.out.println("Generating modules for " + tech);
		createPilots(tech, workBook);
	}

	private void createPilots(String tech, HSSFWorkbook workBook)
			throws PhrescoException {
		HSSFSheet sheet = workBook.getSheet(SHEET_NAME_PILOTS);
		Iterator<Row> rowsIter = sheet.rowIterator();
		// Skipping first row
		for (int i = 0; i < NO_OF_ROWS_TO_SKIP; i++) {
			rowsIter.next();
		}
		while (rowsIter.hasNext()) {
			Row row = rowsIter.next();
			createPilots(tech, row);
		}
	}

	private String getValue(Cell cell) {
		if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
			return cell.getStringCellValue();
		}
		if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			return String.valueOf(cell.getNumericCellValue());
		}
		return null;
	}

	private void createPilots(String tech, Row row) throws PhrescoException {
		//dataManager = PhrescoServerFactory.getPhrescoDataManager();
		ProjectInfo info = new ProjectInfo();
		List <ProjectInfo> projectInfos = new ArrayList <ProjectInfo> ();
		Cell namevalue = row.getCell(1);
		String name = null;
		if (namevalue != null
				&& Cell.CELL_TYPE_BLANK != namevalue.getCellType()) {
			name = getValue(namevalue);
		}
		Cell desc = row.getCell(2);
		String description = null;
		if (desc != null && Cell.CELL_TYPE_BLANK != desc.getCellType()) {
			 description = getValue(desc);
		}
		info.setName(name);
		info.setDescription(description);
		info.setCode("PHTN_PILOT_" + name);
		Technology technology = new Technology();
		technology.setId(tech);
		technology.setName(name);

		List<ModuleGroup> modules = new ArrayList<ModuleGroup>();
		String[] depModuleIds = null;
		Cell depModule = row.getCell(3);
		if (depModule != null
				&& Cell.CELL_TYPE_BLANK != depModule.getCellType()) {
			String depModules = getValue(depModule);
			depModuleIds = StringUtils.split(depModules, DELIMITER);
			//Add data persistence - dependent modules field in Module object created in TechnologyGenerator.java
			for (String s2:depModuleIds) {
			//System.out.println ("dependent modules-->"+s2);
			}
			//ENDS
		}
		if(depModuleIds!= null){
		for (String depid : depModuleIds) {
			Double depModId = Double.parseDouble(depid);
			ModuleGroup moduleGroup = createModuleGroup(
					SNO_AND_NAME_MAP.get(depModId), SNO_AND_NAME.get(depModId),
					SNO_AND_VERSION.get(depModId));
			//System.out.println ("--MG id--"+moduleGroup.getId()+"--MG Artifact id--"+moduleGroup.getArtifactId()+"--MG GroupId--"+moduleGroup.getGroupId()+"--MG Name--"+moduleGroup.getName()+"--MG Type--"+moduleGroup.getType()+"--MG Vendor--"+moduleGroup.getVendor()+"--MG docs--"+moduleGroup.getDocs()+"--MG Versions--"+moduleGroup.getVersions());

			modules.add(moduleGroup);
			//System.out.println ("module Group-->"+moduleGroup.getId());
		}


		}
		//System.out.println ("module Group size-->"+modules.size());
		//Data Persistence - ModuleGroup
		//dataManager.addModuleGroup(modules);
		//ENDS
		technology.setModules(modules);
		String[] pilotUrlIds = null;
		Cell url = row.getCell(4);
		if (url == null) {
			info.setTechnology(technology);
			writeToPilots(info, tech);
			int i=0;
			//System.out.println ("projectInfo objects name-->"+"--"+info.getId()+"--"+info.getName()+"--"+info.getPilotProjectUrls()+""+info.getCode());
			System.out.println ("No JS LIB-->");
		} else if (url != null && Cell.CELL_TYPE_BLANK != url.getCellType()) {
			String pilotUrl = getValue(url);
			pilotUrlIds = StringUtils.split(pilotUrl, DELIMITER);

			List<ModuleGroup> jslibrary = new ArrayList<ModuleGroup>();
			for (String jsLibId : pilotUrlIds) {
				Double jsdepModId = Double.parseDouble(jsLibId);
				ModuleGroup moduleGroup = createModuleGroup(
						JSSNO_AND_NAME_MAP.get(jsdepModId),
						JSSNO_AND_NAME.get(jsdepModId),
						JSSNO_AND_VERSION.get(jsdepModId));
				//System.out.println ("JS LIB-->"+moduleGroup.getId());
				jslibrary.add(moduleGroup);
			}
			technology.setJsLibraries(jslibrary);
			info.setTechnology(technology);
			System.out.println ("-size of js library-"+jslibrary.size());
			for (ModuleGroup mg:jslibrary){
			System.out.println ("--MG id--"+mg.getId()+"--MG Artifact id--"+mg.getArtifactId()+"--MG GroupId--"+mg.getGroupId()+"--MG Name--"+mg.getName()+"--MG Type--"+mg.getType()+"--MG Vendor--"+mg.getVendor()+"--MG docs--"+mg.getDocs()+"--MG Versions--"+mg.getVersions());
			}
			//Add data persistence - JSLibraries (ModuleGroup)
			//dataManager.addModuleGroup(jslibrary);
			//ENDS
			writeToPilots(info, tech);
			//int i=0;
			//System.out.println ("projectInfo objects-->"+i++);

		}
		//System.out.println ("projectInfo objects name-->"+"--"+info.getId()+"--"+info.getName()+"--"+info.getPilotProjectUrls()+""+info.getCode());
		projectInfos.add(info);
		info.setId(info.getName());

		//add persistence to data PilotTechnology,

		Technology techTemp = info.getTechnology();
		List<PilotTechnology> pilotTechnologyList = new ArrayList<PilotTechnology> ();

		//System.out.println ("--Pilot Tech app id--"+techTemp.getAppTypeId()+"--Pilot Tech app id--"+techTemp.getId()+"--Pilot Tech Name--"+techTemp.getName());
		PilotTechnology pilotTech = createPilotTechnology (techTemp,pilotTechlogyTracker,pilotInfoTracker);
		//System.out.println ("--Pilot Tech app id--"+pilotTech.getId()+"--Pilot Tech Name--"+pilotTech.getName()+pilotTech.getProjectInfoid()+"--Pilot Tech id--"+pilotTech.getTechid());
		pilotTechnologyList.add(pilotTech);
//		dataManager.addPilotTechnology (pilotTechnologyList);
		pilotTechlogyTracker++;
		pilotInfoTracker++;

		List <ModuleGroup> jsLibList = techTemp.getJsLibraries();
		List <ModuleGroup> moduleList = techTemp.getModules();
		List <PilotTechModules> pilotTechModuleList = new ArrayList <PilotTechModules> ();
					for (ModuleGroup mgtemp:moduleList){
						PilotTechModules pilotTechModules = new PilotTechModules ();
						pilotTechModules = populatePilotTechModules (mgtemp,pilotTechModulesTracker,pilotTechlogyTracker);
						//System.out.println ("--Techmodules---"+mgtemp);
						List <Module> techModuleVersionList = mgtemp.getVersions();
						//System.out.println ("size of techModuleVersionList --"+pilotTechModulesTracker+"--"+techModuleVersionList.size());
						storeTechModuleVersions (techModuleVersionList,pilotTechModulesTracker);
						//System.out.println ("--PilotTechMod---"+pilotTechModules.getId()+"--moduleGroupId---"+pilotTechModules.getMgid()+"--mgName---"+pilotTechModules.getName()+"--Mgtechid---"+pilotTechModules.getPilotTechid());
						pilotTechModuleList.add(pilotTechModules);
						pilotTechModulesTracker++;
					}
//					dataManager.addPilotTechModules (pilotTechModuleList);

					pilotTechlogyTracker++;
		//ENDS
		storeProjectInfo (info,pilotInfoTracker);
		if (jsLibList!=null){ //Since jsLib size is zero persistense login not added
		System.out.println ("--JS lib list size--"+jsLibList.size());
		}
		else {
			System.out.println ("--JS Lib size is zero--");
		}
		//dataManager.addProjectInfo(info);
		//Add data persistence - ProjectInfo -PilotProject (ModuleGroup)
		//dataManager.addModuleGroup(jslibrary);
		//ENDS

	}

	private void storeProjectInfo(ProjectInfo info, int pilotInfoTracker2) throws PhrescoException {
		// TODO Auto-generated method stub
		List <ProjectInfo> pilotProjectInfo = new ArrayList <ProjectInfo> ();
		info.setPilotid(pilotInfoTracker2);
		System.out.println ("--Proj app--"+info.getApplication()+"--Proj code--"+info.getCode()+"--Proj contentURL--"+info.getContentURLId()+"--Proj Desc--"+info.getDescription()+"--Proj id--"+info.getId()+"--Proj Name--"+info.getName()+"--Proj Pilotid--"+info.getPilotid()+"--Proj techid--"+info.getTechID()+"--Proj versio--"+info.getVersion()+"--Proj urls--"+info.getPilotProjectUrls()+"--Proj tech--"+info.getTechnology());
		pilotProjectInfo.add(info);
//		dataManager.addProjectInfo(pilotProjectInfo);

	}

	private PilotTechnology createPilotTechnology(Technology techTemp,int pilotTechlogyTracker2, int pilotInfoTracker2) {
		// TODO Auto-generated method stub
		PilotTechnology pilotTechnology = new PilotTechnology ();
		pilotTechnology.setId(pilotTechlogyTracker2);
		pilotTechnology.setName(techTemp.getName());
		pilotTechnology.setTechid(techTemp.getId());
		pilotTechnology.setProjectInfoid(pilotInfoTracker2);

		return pilotTechnology;
	}

	private void storeTechModuleVersions(List<Module> techModuleVersionList,int pilotTechModulesTracker2) throws PhrescoException {
		// TODO Auto-generated method stub

		PilotTechModuleVersions pilotTechModuleVersions = new PilotTechModuleVersions ();
		List <PilotTechModuleVersions> pilotTechModuleVersionsList = new ArrayList <PilotTechModuleVersions> ();
		for (Module techModule:techModuleVersionList){
			pilotTechModuleVersions.setPilotTechModulesid(pilotTechModulesTracker2);
			pilotTechModuleVersions.setVersion(techModule.getVersion());
			pilotTechModuleVersions.setId(pilotTechModuleVersionTracker);
			pilotTechModuleVersionsList.add(pilotTechModuleVersions);
			pilotTechModuleVersionTracker++;
			//System.out.println ("--Pilot Tech Module Versions id --"+pilotTechModuleVersions.getId()+"--Pilot Tech Module Versions TECH MODULE ID --"+pilotTechModuleVersions.getPilotTechModulesid()+"--Pilot Tech Module Versions VERSION STR --"+pilotTechModuleVersions.getVersion());

		}
//		dataManager.addPilotTechModuleVersionsList (pilotTechModuleVersionsList);

	}

	private PilotTechModules populatePilotTechModules(ModuleGroup mgtemp,int pilotTechModulesTracker2, int pilotTechlogyTracker2) {
		// TODO Auto-generated method stub
		PilotTechModules pilotTechModules = new PilotTechModules ();
		pilotTechModules.setId(pilotTechModulesTracker2);
		pilotTechModules.setMgid(mgtemp.getId());
		pilotTechModules.setName(mgtemp.getName());
		pilotTechModules.setPilotTechid(pilotTechlogyTracker2);
		return pilotTechModules;
	}

	private ModuleGroup createModuleGroup(String id, String name, String version) {
		ModuleGroup group = new ModuleGroup();
		group.setId(id);
		group.setName(name);
		Module module = new Module();
		module.setVersion(version);
		group.getVersions().add(module);
		return group;
	}

	private void writeToPilots(ProjectInfo info,String tech) throws PhrescoException {
		File file = new File(outputRootDir,   tech+"pilots.json");
		System.out.println("File path = " + file.getPath());
		try {
			Gson gson = new Gson();
			String json = gson.toJson(info);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(json);
			writer.close();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}

	}

	public final void generateAll() throws PhrescoException {
		for (String tech : INPUT_EXCEL_MAP.keySet()) {
			generateJSLibrary(tech);
			generate(tech);
		}
	}

	public void generate(String tech) throws PhrescoException {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(getExcelFile(tech));
			System.out.println("Processing excel file for "
					+ getExcelFile(tech));
			HSSFWorkbook workBook = new HSSFWorkbook(fs);
			generateModule(workBook, tech);
			generatepilot(workBook, tech);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void generateJSLibrary(String tech) throws PhrescoException {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(new File(inputRootDir.getPath(),
					"PHTN_PHRESCO_JS-Libraries.xls"));
			System.out.println("Processing excel file for "
					+ getExcelFile(tech));
			HSSFWorkbook workBook = new HSSFWorkbook(fs);
			generateJSLibrary(workBook, tech);
		} catch (IOException e) {
			throw new PhrescoException(e);
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void generateJSLibrary(HSSFWorkbook workBook, String tech) {
		HSSFSheet sheet = workBook.getSheet(SHEET_NAME_JSLIBRARY);
		Iterator<Row> rowsIter = sheet.rowIterator();
		for (int i = 0; i < NO_OF_ROWS_TO_SKIP; i++) {
			rowsIter.next();
		}
		while (rowsIter.hasNext()) {
			Row row = rowsIter.next();
			createJSLibrary(tech, row);
		}

	}

	private void createJSLibrary(String tech, Row row) {
		Cell srno = row.getCell(0);
		Double no = null;
		if(srno != null && Cell.CELL_TYPE_BLANK != srno.getCellType()){
		 no = srno.getNumericCellValue();
		}
		Cell names = row.getCell(1);
		String name = null;
		if(names != null && Cell.CELL_TYPE_BLANK != names.getCellType()){
			 name = getValue(names);
			}
				Cell versions = row.getCell(2);
		String version = getValue(versions);
		String identifier = ID1 + name + STR_UNDER_SCORE + version;
		JSSNO_AND_NAME_MAP.put(no, identifier);
		JSSNO_AND_NAME.put(no, name);
		JSSNO_AND_VERSION.put(no, version);
	}

	private void generateModule(HSSFWorkbook workBook, String tech)
			throws PhrescoException {
		HSSFSheet sheet = workBook.getSheet(SHEET_NAME_MODULE);
		Iterator<Row> rowsIter = sheet.rowIterator();
		for (int i = 0; i < NO_OF_ROWS_TO_SKIP; i++) {
			rowsIter.next();
		}
		while (rowsIter.hasNext()) {
			Row row = rowsIter.next();
			createModule(tech, row);
		}
	}

	private void createModule(String tech, Row row) {
		Cell srno = row.getCell(0);
		Double no = null;
		if (srno != null && Cell.CELL_TYPE_BLANK != srno.getCellType()) {
			no = srno.getNumericCellValue();
		}
		Cell names = row.getCell(1);
		String name = null;
		if (names != null && Cell.CELL_TYPE_BLANK != names.getCellType()) {
			name = getValue(names);
		}
		Cell versions = row.getCell(2);
		String version = null;
		if (versions != null && Cell.CELL_TYPE_BLANK != versions.getCellType()) {
			version = getValue(versions);
		}
		String identifier = ID + name + STR_UNDER_SCORE + version;
		SNO_AND_NAME_MAP.put(no, identifier);
		SNO_AND_NAME.put(no, name);
		SNO_AND_VERSION.put(no, version);
	}

	public static void main(String[] args) throws PhrescoException {
		//"D:\\work\\projects\\phresco\\service\\trunk\\phresco-service-runner\\delivery\\tools\\");
		File toolsHome = new File(
				"D:\\work\\projects\\phresco\\source\\trunk\\service\\trunk\\phresco-service-runner\\delivery\\tools");
		         //D:\\work\\projects\\phresco\\source\\trunk\\service\\trunk\\phresco-service-runner\\delivery\\tools\\files"
		File inputRootDir = new File(toolsHome, "files");
		File outputRootDir = new File(toolsHome, "repo");
		PilotProjectGenerator generator = new PilotProjectGenerator(
				inputRootDir, outputRootDir);
		generator.generateAll();
	}

}
