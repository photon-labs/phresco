/*
 * ###
 * Phresco Framework Implementation
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
package com.photon.phresco.framework.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.BuildInfo;
import com.photon.phresco.commons.CIBuild;
import com.photon.phresco.commons.CIJob;
import com.photon.phresco.commons.CIJobStatus;
import com.photon.phresco.commons.DownloadTypes;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.commons.model.User;
import com.photon.phresco.configuration.Configuration;
import com.photon.phresco.configuration.Environment;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.api.CIManager;
import com.photon.phresco.framework.api.Project;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.api.ValidationResult;
import com.photon.phresco.framework.api.Validator;
import com.photon.phresco.model.AdminConfigInfo;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.DownloadInfo;
import com.photon.phresco.model.DownloadPropertyInfo;
import com.photon.phresco.model.LogInfo;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.SettingsInfo;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.model.Technology;
import com.photon.phresco.model.UserInfo;
import com.photon.phresco.model.VideoInfo;
import com.photon.phresco.model.VideoType;
import com.photon.phresco.model.WebService;
import com.photon.phresco.service.client.api.ServiceClientConstant;
import com.photon.phresco.service.client.api.ServiceContext;
import com.photon.phresco.service.client.api.ServiceManager;
import com.photon.phresco.service.client.factory.ServiceClientFactory;
import com.photon.phresco.service.client.impl.RestClient;
import com.photon.phresco.util.ArchiveUtil;
import com.photon.phresco.util.ArchiveUtil.ArchiveType;
import com.photon.phresco.util.Constants;
import com.photon.phresco.util.Credentials;
import com.photon.phresco.util.ProjectUtils;
import com.photon.phresco.util.ServiceConstants;
import com.photon.phresco.util.TechnologyTypes;
import com.photon.phresco.util.Utility;
import com.phresco.pom.model.Model;
import com.phresco.pom.site.Reports;
import com.phresco.pom.util.PomProcessor;
import com.phresco.pom.util.SiteConfigurator;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

public class ProjectAdministratorImpl implements ProjectAdministrator, FrameworkConstants, Constants, ServiceClientConstant, ServiceConstants {

	private static final Logger S_LOGGER= Logger.getLogger(ProjectAdministratorImpl.class);
	private Map<String, List<ModuleGroup>> coreModulesMap = Collections.synchronizedMap(new HashMap<String, List<ModuleGroup>>(8));
	private Map<String, List<ModuleGroup>> customModulesMap = Collections.synchronizedMap(new HashMap<String, List<ModuleGroup>>(8));
	private List<AdminConfigInfo> adminConfigInfos = Collections.synchronizedList(new ArrayList<AdminConfigInfo>(5));
	private static Map<String, String> sqlFolderPathMap = new HashMap<String, String>();
	private static  Map<String, List<Reports>> siteReportMap = new HashMap<String, List<Reports>>(15);
	private Map<String, List<DownloadInfo>> downloadInfosMap = Collections.synchronizedMap(new HashMap<String, List<DownloadInfo>>(64));
	
	private static ServiceManager serviceManager = null;
    
    public ServiceManager getServiceManager() {
		return serviceManager;
	}

	private static void initializeSqlMap() {
		// TODO: This should come from database
		sqlFolderPathMap.put(TechnologyTypes.PHP, "/source/sql/");
		sqlFolderPathMap.put(TechnologyTypes.PHP_DRUPAL6, "/source/sql/");
		sqlFolderPathMap.put(TechnologyTypes.PHP_DRUPAL7, "/source/sql/");
		sqlFolderPathMap.put(TechnologyTypes.NODE_JS_WEBSERVICE, "/source/sql/");
		sqlFolderPathMap.put(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET, "/src/sql/");
		sqlFolderPathMap.put(TechnologyTypes.HTML5_MOBILE_WIDGET, "/src/sql/");
		sqlFolderPathMap.put(TechnologyTypes.HTML5_WIDGET, "/src/sql/");
		sqlFolderPathMap.put(TechnologyTypes.JAVA_WEBSERVICE, "/src/sql/");
		sqlFolderPathMap.put(TechnologyTypes.WORDPRESS, "/source/sql/");
	}

	/**
	 * Creates a project based on the given project information
	 *
	 * @return Project based on the given information
	 */
	public Project createProject(ProjectInfo info, File path,UserInfo userInfo) throws PhrescoException {

		S_LOGGER.debug("Entering Method ProjectAdministratorImpl.createProject(ProjectInfo info, File path)");
		S_LOGGER.debug("createProject() > info name : " + info.getName());

		File projectPath = new File(Utility.getProjectHome()+ File.separator+ info.getCode());
		String techId = info.getTechnology().getId();
		if (StringUtils.isEmpty(info.getVersion())) {
			info.setVersion(PROJECT_VERSION); // TODO: Needs to be fixed
		}
		ClientResponse response = PhrescoFrameworkFactory.getServiceManager().createProject(info,userInfo);

		S_LOGGER.debug("createProject response code " + response.getStatus());

		if (response.getStatus() == 200) {
			try {
				extractArchive(response, info);
				updateProjectPOM(info);
			} catch (FileNotFoundException e) {
				throw new PhrescoException(e); 
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		 }  else if(response.getStatus() == 401){
			 throw new PhrescoException("Session Expired ! Please Relogin.");
		} else {
			throw new PhrescoException("Project creation failed");
		}
		boolean flag1 = techId.equals(TechnologyTypes.JAVA_WEBSERVICE) || techId.equals(TechnologyTypes.JAVA_STANDALONE) || techId.equals(TechnologyTypes.HTML5_WIDGET) || 
		techId.equals(TechnologyTypes.HTML5_MOBILE_WIDGET)|| techId.equals(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET);
		if(flag1) {
			File pomPath = new File(projectPath,POM_FILE);
			ServerPluginUtil spUtil = new ServerPluginUtil();
			spUtil.addServerPlugin(info, pomPath);
		}
		boolean drupal = techId.equals(TechnologyTypes.PHP_DRUPAL7) || techId.equals(TechnologyTypes.PHP_DRUPAL6);
		try {
			if(drupal) {
				updateDrupalVersion(projectPath, info);
				excludeModule(info);
			}
		} catch (IOException e1) {
			throw new PhrescoException(e1);
		} catch (JAXBException e1) {
			throw new PhrescoException(e1);
		} catch (ParserConfigurationException e1) {
			throw new PhrescoException(e1);	
		}

		// Creating configuration file, after successfull creation of project
		try {
			createConfigurationXml(info.getCode());
		} catch (Exception e) {
			S_LOGGER.error("Entered into the catch block of Configuration creation failed Exception" + e.getLocalizedMessage());
			throw new PhrescoException("Configuration creation failed");
		}
		return new ProjectImpl(info);
	}

	private void extractArchive(ClientResponse response, ProjectInfo info) throws  IOException, PhrescoException {
		InputStream inputStream = response.getEntityInputStream();
		FileOutputStream fileOutputStream = null;
		String archiveHome = Utility.getArchiveHome();
		File archiveFile = new File(archiveHome + info.getCode() + ARCHIVE_FORMAT);
		fileOutputStream = new FileOutputStream(archiveFile);
		try {
			byte[] data = new byte[1024];
			int i = 0;
			while ((i = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, i);
			}
			fileOutputStream.flush();
			ArchiveUtil.extractArchive(archiveFile.getPath(), Utility.getProjectHome() + info.getCode(), ArchiveType.ZIP);
		} finally {
			Utility.closeStream(inputStream);
			Utility.closeStream(fileOutputStream);
		}
	}

	/**
	 * Updates a project based on the given project information
	 *
	 * @return Project based on the given information
	 */

	public Project updateProject(ProjectInfo delta, ProjectInfo projectInfo, File path,UserInfo userInfo) throws PhrescoException {

		S_LOGGER.debug("Entering Method ProjectAdministratorImpl.updateProject(ProjectInfo info, File path)");
		S_LOGGER.debug("updateProject() > info name : " + delta.getName());

		if (StringUtils.isEmpty(delta.getVersion())) {
			delta.setVersion(PROJECT_VERSION); // TODO: Needs to be fixed
		}

		ClientResponse response = null;
		String techId = delta.getTechnology().getId();
		if(techId.equals(TechnologyTypes.PHP_DRUPAL6)|| techId.equals(TechnologyTypes.PHP_DRUPAL7)) {
			excludeModule(delta);
		}
		boolean flag = !techId.equals(TechnologyTypes.JAVA_WEBSERVICE) && !techId.equals(TechnologyTypes.JAVA_STANDALONE) && !techId.equals(TechnologyTypes.ANDROID_NATIVE);
		ProjectInfo projectInfoClone = projectInfo.clone();
		updateDocument(projectInfo, path);
		response = PhrescoFrameworkFactory.getServiceManager().updateProject(delta,userInfo);
		 if(response.getStatus() == 401){
			 throw new PhrescoException("Session Expired ! Please Relogin.");
		 }
		 else if (flag) {
			if (response.getStatus() != 200) {
				throw new PhrescoException("Project updation failed");
			}
		}
		if (techId.equals(TechnologyTypes.JAVA_WEBSERVICE)) {
			createSqlFolder(delta, path);
		}
		updatePomProject(delta,projectInfoClone);
		try {
			if (flag) {
				extractArchive(response, delta);
			}
			ProjectUtils.updateProjectInfo(delta, path);
			updateProjectPOM(projectInfo);
		} catch (FileNotFoundException e) {
			throw new PhrescoException(e);
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
		return new ProjectImpl(delta);
	}

	/**
	 * Update PDF document with the selected Modules
	 * @param projectInfo
	 * @param path
	 * @throws PhrescoException
	 */
	private void updateDocument(ProjectInfo projectInfo, File path) throws PhrescoException {
		List<ModuleGroup> modules = projectInfo.getTechnology().getModules();
		List<ModuleGroup> jsLibraries = projectInfo.getTechnology().getJsLibraries();
		if(modules != null || jsLibraries != null) {
			ProjectInfo selectecdModule = SelectecdModule(projectInfo,path);
			ClientResponse updateDocumentResponse = PhrescoFrameworkFactory.getServiceManager().updateDocumentProject(selectecdModule);
			if (updateDocumentResponse.getStatus() != 200) {
				throw new PhrescoException("Project updation failed");
			}
			try {
				extractArchive(updateDocumentResponse, projectInfo);
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}
	
	public String getTechId(String projectCode) throws PhrescoException {
		return getProject(projectCode).getProjectInfo().getTechnology().getId();
	}

	/**
	 * Update dependency as selected Module in the pom file
	 * @param delta
	 * @param projectInfo
	 * @param projectInfoClone
	 * @throws PhrescoException
	 */
	private void updatePomProject(ProjectInfo delta,  ProjectInfo projectInfoClone) throws PhrescoException {
		String techId = delta.getTechnology().getId();
		File path = new File(Utility.getProjectHome() + File.separator + delta.getCode() + File.separator + POM_FILE);
		boolean flag1 = techId.equals(TechnologyTypes.JAVA_WEBSERVICE) || techId.equals(TechnologyTypes.JAVA_STANDALONE) || techId.equals(TechnologyTypes.HTML5_WIDGET) || 
		techId.equals(TechnologyTypes.HTML5_MOBILE_WIDGET)|| techId.equals(TechnologyTypes.HTML5_MULTICHANNEL_JQUERY_WIDGET);
		boolean flag2 = techId.equals(TechnologyTypes.ANDROID_HYBRID) || techId.equals(TechnologyTypes.ANDROID_NATIVE);
		if (flag1) {
			try {
				ServerPluginUtil spUtil = new ServerPluginUtil();
				spUtil.deletePluginFromPom(path);
				spUtil.addServerPlugin(delta, path);
				updatePomProject(delta);
			} catch (Exception e) {
				throw new PhrescoException(e);
			}
		} 
		if(flag2) {
			try { 
				if(projectInfoClone.getTechnology().getModules() != null) {
					updateAndroidPomProject(projectInfoClone);
				}
			} catch (JDOMException e) {
				throw new PhrescoException(e);
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	}

	/**
	 * update the projectinfo for all selected Module 
	 * @param projectInfo
	 * @param path
	 * @return
	 * @throws PhrescoException
	 */
	private ProjectInfo SelectecdModule(ProjectInfo projectInfo, File path) throws PhrescoException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			Gson gson = new Gson();
			ProjectInfo info = gson.fromJson(reader, ProjectInfo.class);
			List<ModuleGroup> ProjectInfomodules = info.getTechnology().getModules();
			List<ModuleGroup> projectInfojsLibraries = info.getTechnology().getJsLibraries();

			List<ModuleGroup> selectedInfomodules = projectInfo.getTechnology().getModules();
			List<ModuleGroup> selectedInfojsLibraries = projectInfo.getTechnology().getJsLibraries();

			if(ProjectInfomodules != null && !ProjectInfomodules.isEmpty() && selectedInfomodules != null) {
				selectedInfomodules.addAll(ProjectInfomodules);	
				projectInfo.getTechnology().setModules(selectedInfomodules);
			}
			if(projectInfojsLibraries != null && !projectInfojsLibraries.isEmpty() && selectedInfojsLibraries != null) {
				selectedInfojsLibraries.addAll(projectInfojsLibraries); 
				projectInfo.getTechnology().setJsLibraries(selectedInfojsLibraries);
			}
			if(selectedInfomodules == null && ProjectInfomodules != null && !ProjectInfomodules.isEmpty()) {
				projectInfo.getTechnology().setModules(ProjectInfomodules);
			}
			if(selectedInfojsLibraries == null && projectInfojsLibraries != null && !projectInfojsLibraries.isEmpty() ) {
				projectInfo.getTechnology().setJsLibraries(projectInfojsLibraries);
			}
		} 
		catch (Exception e) {
			throw  new PhrescoException(e);
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
		return projectInfo;
	}

	/**
	 * Exclude the Code Validation for core Module 
	 * @param info
	 * @throws PhrescoException
	 */
	private  void excludeModule(ProjectInfo info) throws PhrescoException {
		try {
			File projectPath = new File(Utility.getProjectHome(), info.getCode() + File.separator + POM_FILE);
			SAXBuilder builder = new SAXBuilder();
			Document doc = (Document) builder.build(projectPath);
			Element rootNode = doc.getRootElement();
			Element properties = rootNode.getChild("properties",rootNode.getNamespace());
			Namespace ns = rootNode.getNamespace();
			List<ModuleGroup> modules = info.getTechnology().getModules();
			if(CollectionUtils.isEmpty(modules)) {
				return;
			}
			Element sonarExclusion = properties.getChild("sonar.exclusions",ns);
			StringBuffer exclusionStringBuff = new StringBuffer();
			if(sonarExclusion != null) {
				exclusionStringBuff.append(',');
			}
			if(sonarExclusion == null) {
				sonarExclusion = new Element("sonar.exclusions", ns);
				properties.addContent(sonarExclusion);
			}
			for (ModuleGroup moduleGroup : modules) {
				if(moduleGroup.isCore()) {
					exclusionStringBuff.append("**/");
					exclusionStringBuff.append(moduleGroup.getName().toLowerCase());
					exclusionStringBuff.append("/**");
					exclusionStringBuff.append(",");
				}
			}
			String exclusionValue = exclusionStringBuff.toString();
			if(exclusionValue.lastIndexOf(',') > -1) {
				exclusionValue = exclusionValue.substring(0,exclusionValue.lastIndexOf(','));
			}
			sonarExclusion.addContent(exclusionValue);
			saveFile(projectPath, doc);
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	/**
	 * Exclude the core Module for Code Validation 
	 * @param projectPath
	 * @param doc
	 * @throws IOException
	 */
	public void saveFile(File projectPath, Document doc) throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(projectPath);
			if (projectPath.exists()) {
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				xmlOutput.output(doc, writer);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * @param path
	 * @param info
	 * @throws IOException
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 */
	private void updateDrupalVersion(File path, ProjectInfo info) throws IOException, JAXBException, ParserConfigurationException {
		File xmlFile = new File(path, POM_FILE);
		PomProcessor processor = new PomProcessor(xmlFile);
		List<String> name = info.getTechnology().getVersions();
		String selectedVersion = name.get(0);
		processor.setProperty(DRUPAL_VERSION, selectedVersion);
		processor.save();
	}

	public Project getProject(String projectCode) throws PhrescoException {

		S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getProject(String projectCode)");

		if (StringUtils.isEmpty(projectCode)) {

			S_LOGGER.debug("getProject() ProjectCode = "+projectCode);

			throw new PhrescoException(FrameworkImplConstants.ERROR_PROJECT_CODE_EMPTY);
		}

		List<Project> projects = discover(Collections.singletonList(new File(Utility.getProjectHome())));
		if (CollectionUtils.isEmpty(projects)) {
			return null;
		}

		for (Project project : projects) {
			if (project.getProjectInfo().getCode().equalsIgnoreCase(projectCode)) {
				return project;
			}
		}

		return null;
	}

	public  static void updatePomProject(ProjectInfo projectInfo) throws PhrescoException, JDOMException, IOException {
		File path = new File(Utility.getProjectHome() + File.separator + projectInfo.getCode() + File.separator + POM_FILE);
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = (Document) builder.build(path);
			Element rootNode = doc.getRootElement();
			Element dependencies = rootNode.getChild("dependencies",rootNode.getNamespace());
			Namespace ns = rootNode.getNamespace();
			List<ModuleGroup> modules = projectInfo.getTechnology().getModules();
			if(CollectionUtils.isEmpty(modules)){
				return;
			}
			for (ModuleGroup moduleGroup : modules) {
				Element dependency = new Element("dependency", ns);
				dependency.addContent(new Element("groupId", ns).setText(moduleGroup.getGroupId()));
				dependency.addContent(new Element("artifactId", ns).setText(moduleGroup.getArtifactId()));
				List<Module> versions = moduleGroup.getVersions();
				for (Module version : versions) {
					dependency.addContent(new Element("version", ns).setText(version.getVersion()));
				}
				dependencies.addContent(dependency);
			}
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(path));
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	/**
	 * TODO: need to change this to use Phresco POM processor module.
	 * @param projectInfo
	 * @throws PhrescoException
	 * @throws JDOMException
	 * @throws IOException
	 */
	public void updateAndroidPomProject(ProjectInfo projectInfo) throws PhrescoException, JDOMException, IOException {
		File path = new File(Utility.getProjectHome() + File.separator + projectInfo.getCode() + File.separator + POM_FILE);
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = (Document) builder.build(path);
			Element rootNode = doc.getRootElement();
			Element dependencies = rootNode.getChild("dependencies",rootNode.getNamespace());
			Namespace ns = rootNode.getNamespace();
			List<ModuleGroup> modules = projectInfo.getTechnology().getModules();
			if(CollectionUtils.isEmpty(modules)){
				return;
			}
			for (ModuleGroup moduleGroup : modules) {
				Element dependency = new Element("dependency", ns);
				dependency.addContent(new Element("groupId", ns).setText(getGroupId(projectInfo.getTechnology())));
				List<Module> versions = moduleGroup.getVersions();
				for (Module version : versions) {
					dependency.addContent(new Element("artifactId", ns).setText(moduleGroup.getId()));
					dependency.addContent(new Element("version", ns).setText(version.getVersion()));
				}
				dependencies.addContent(dependency);
			}
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(path));
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}

	private  String getGroupId(Technology technology) {
		return "modules." + technology.getId() + ".files";
	}

	@Override
	public List<ApplicationType> getApplicationTypes() throws PhrescoException {
		try{
			RestClient<ApplicationType> applicationTypeClient = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
			GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>(){};
			List<ApplicationType> applicationTypes = applicationTypeClient.get(genericType);
			return applicationTypes;
		} catch (ClientHandlerException ex) {
			ex.printStackTrace();
			S_LOGGER.error(ex.getLocalizedMessage());
			throw new PhrescoException(ex);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PhrescoException(e);
		}
	}

	@Override
	public ApplicationType getApplicationType(String name) throws PhrescoException {
		try {
			RestClient<ApplicationType> applicationTypeClient = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_APPTYPES);
			GenericType<List<ApplicationType>> genericType = new GenericType<List<ApplicationType>>(){};
			List<ApplicationType> applicationTypes = applicationTypeClient.get(genericType);
			if (CollectionUtils.isNotEmpty(applicationTypes)) {
				for (ApplicationType applicationType : applicationTypes) {
					if (applicationType.getName().equals(name)) {
		                return applicationType;
		            }
				}
			}
		} catch (ClientHandlerException ex) {
			S_LOGGER.error(ex.getLocalizedMessage());
			throw new PhrescoException(ex);
		}
		return null;
	}

	public Map<String, Technology> getAllTechnologies() throws PhrescoException {
		try {
			return PhrescoFrameworkFactory.getServiceManager().getAllTechnologies();
		} catch (ClientHandlerException ex) {
			S_LOGGER.error(ex.getLocalizedMessage());
			throw new PhrescoException(ex);
		}
	}

	public Technology getTechnology(String techId) throws PhrescoException {
		try {
			return getAllTechnologies().get(techId);
		} catch (ClientHandlerException ex) {
			S_LOGGER.error(ex.getLocalizedMessage());
			throw new PhrescoException(ex);
		}
	}

	/**
	 * Delete projects based on the given project codes
	 */
	 public void deleteProject(List<String> projectCodes) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteProject(List<String> projectCodes)");
		 S_LOGGER.debug("deleteProject() projectCodes = "+projectCodes);

		 if (CollectionUtils.isEmpty(projectCodes)) {
			 throw new PhrescoException(FrameworkImplConstants.ERROR_PROJECT_CODES_EMPTY);
		 }

		 File zipFile = null;

		 String projectHome = Utility.getProjectHome();
		 for (String projectCode : projectCodes) {
			 try {
				 FileUtils.deleteDirectory(new File(projectHome + projectCode));
				 zipFile = new File(Utility.getArchiveHome() + projectCode + ".zip");
				 zipFile.delete();
			 } catch (IOException e) {
				 S_LOGGER.error("deleteProject() error happened : " + e.getMessage());
				 throw new PhrescoException(e);
			 }
		 }
	 }

	 /**
	  * This will search the given path and return list of projects
	  *
	  * @return List of projects which compliance with the framework
	  */
	 public List<Project> discover(List<File> paths) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.discover(List<File> paths)");

		 //Use the FileNameFilter for filtering .phresco directories
		 //Read the .project file and construct the Project object.

		 S_LOGGER.debug("discover( )  paths = "+paths);

		 if(CollectionUtils.isEmpty(paths)) {
			 throw new PhrescoException(MSG_FILE_PATH_EMPTY);
		 }

		 List<Project> projects = new ArrayList<Project>();

		 for (File path : paths) {
			 File[] childFiles = path.listFiles();
			 for (File childFile : childFiles) {
				 File[] dotPhrescoFolders = childFile.listFiles(new PhrescoFileNameFilter(FOLDER_DOT_PHRESCO));
				 if(ArrayUtils.isEmpty(dotPhrescoFolders)) {
					 continue;
				 }

				 for (File dotPhrescoFolder : dotPhrescoFolders) {
					 File[] dotProjectFiles = dotPhrescoFolder.listFiles(new PhrescoFileNameFilter(PROJECT_INFO_FILE));
					 fillProjects(dotProjectFiles, projects);
				 }
			 }
		 }

		 Collections.sort(projects, new ProjectComparator());
		 return projects;
	 }

	 public User doLogin(Credentials credentials) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.doLogin(Credentials credentials)");

		 try {
			 String userName = credentials.getUsername();
			 String password = credentials.getPassword();
			 byte[] encodeBase64 = Base64.encodeBase64(password.getBytes());
			 String encodedPassword = new String(encodeBase64);
			 ServiceContext context = new ServiceContext();
			 FrameworkConfiguration configuration = PhrescoFrameworkFactory.getFrameworkConfig();
			 context.put(SERVICE_URL, configuration.getServerPath());
			 context.put(SERVICE_USERNAME, userName);
			 context.put(SERVICE_PASSWORD, encodedPassword);
			 serviceManager = ServiceClientFactory.getServiceManager(context);
		 } catch (Exception ex) {
			 ex.printStackTrace();
			 S_LOGGER.error(ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
		 return serviceManager.getUserInfo();
	 }

	 private void setDownloadInfoFromService(DownloadPropertyInfo downloadPropertyInfo) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getDownloadInfo()");
		 
		 downloadInfosMap.put(downloadPropertyInfo.getTechId(), PhrescoFrameworkFactory.getServiceManager().getDownloadsFromService(downloadPropertyInfo));
	 }

	 @Override
	 public List<DownloadInfo> getServerDownloadInfo(DownloadPropertyInfo downloadPropertyInfo) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getServerDownloadInfo()");
		 
		 if (CollectionUtils.isEmpty(downloadInfosMap.get(downloadPropertyInfo.getTechId()))) {
			 setDownloadInfoFromService(downloadPropertyInfo);
		 }
		 
		 List<DownloadInfo> serverDownloadInfos = new ArrayList<DownloadInfo>();
		 List<DownloadInfo> downloadInfos = downloadInfosMap.get(downloadPropertyInfo.getTechId());
		 if (CollectionUtils.isNotEmpty(downloadInfos)) {
			 for (DownloadInfo downloadInfo : downloadInfos) {
				 if (DownloadTypes.SERVER.equals(downloadInfo.getType())){
					 serverDownloadInfos.add(downloadInfo);
				 }
			 }
		 }
		 
		 return serverDownloadInfos;
	 }

	 @Override
	 public List<DownloadInfo> getDbDownloadInfo(DownloadPropertyInfo downloadPropertyInfo) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getDbDownloadInfo()");
		 
		 if (CollectionUtils.isEmpty(downloadInfosMap.get(downloadPropertyInfo.getTechId()))) {
			 setDownloadInfoFromService(downloadPropertyInfo);
		 }
		 
		 List<DownloadInfo> dbDownloadInfos = new ArrayList<DownloadInfo>();
		 List<DownloadInfo> downloadInfos = downloadInfosMap.get(downloadPropertyInfo.getTechId());
		 if (CollectionUtils.isNotEmpty(downloadInfos)) {
			 for (DownloadInfo downloadInfo : downloadInfos) {
				 if (DownloadTypes.DATABASE.equals(downloadInfo.getType())){
					 dbDownloadInfos.add(downloadInfo);
				 }
			 }
		 }
		 
		 return dbDownloadInfos;
	 }

	 @Override
	 public List<DownloadInfo> getEditorDownloadInfo(DownloadPropertyInfo downloadPropertyInfo) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getEditorDownloadInfo()");
		 
		 if (CollectionUtils.isEmpty(downloadInfosMap.get(downloadPropertyInfo.getTechId()))) {
			 setDownloadInfoFromService(downloadPropertyInfo);
		 }
		 
		 List<DownloadInfo> editorDownloadInfos = new ArrayList<DownloadInfo>();
		 List<DownloadInfo> downloadInfos = downloadInfosMap.get(downloadPropertyInfo.getTechId());
		 if (CollectionUtils.isNotEmpty(downloadInfos)) {
			 for (DownloadInfo downloadInfo : downloadInfos) {
				 if (DownloadTypes.EDITOR.equals(downloadInfo.getType())){
					 editorDownloadInfos.add(downloadInfo);
				 }
			 }
		 }
		 
		 return editorDownloadInfos;
	 }

	 /**
	  * This method is to fetch the settings template through REST service
	  * @return List of settings template stored in the server [Database, Server and Email]
	  */
	 public List<SettingsTemplate> getSettingsTemplates() throws PhrescoException {
		 try {
			 return PhrescoFrameworkFactory.getServiceManager().getSettingsTemplates();
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error(ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
	 }

	 /**
	  * Returns Settings template which matches the given type
	  * @return Settings template object
	  */
	 public SettingsTemplate getSettingsTemplate(String type) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getSettingsTemplate(String type)");

		 List<SettingsTemplate> settingsTemplates = getSettingsTemplates();
		 for (SettingsTemplate settingsTemplate : settingsTemplates) {
			 if (settingsTemplate.getType().equals(type)) {
				 return settingsTemplate;
			 }
		 }

		 return null;
	 }

	 public void createSetting(SettingsInfo info, String selectedEnvNames) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.createSetting(SettingsInfo info, String selectedEnvNames)");

		 if(info == null) throw new PhrescoException("Settings info should not be null or empty");

		 S_LOGGER.debug("createSetting()  Name = "+ info.getName());

		 try {
			 createSettingsInfo(info, selectedEnvNames, new File(Utility.getProjectHome() + SETTINGS_INFO_FILE_NAME));
		 } catch (Exception e) {

			 throw new PhrescoException(e);
		 }
	 }

	 public void updateSetting(String envName, String oldConfigName, SettingsInfo settingsInfo) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.updateSetting(String name, SettingsInfo info)");
		 File configFile = new File(Utility.getProjectHome() + SETTINGS_INFO_FILE_NAME);
		 updateConfiguration(envName, oldConfigName, settingsInfo, configFile);
	 }

	 public SettingsInfo getSettingsInfo(String name, String envName) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getSettingsInfo(String name)");
		 S_LOGGER.debug("getSettingsInfo() Name = " + name);  		


		 List<SettingsInfo> settingsInfos = getSettingsInfos(envName);
		 if (CollectionUtils.isEmpty(settingsInfos)) {
			 return null;
		 }

		 for (SettingsInfo settingsInfo : settingsInfos) {
			 if (settingsInfo.getName().equals(name)) {
				 return settingsInfo;
			 }
		 }

		 return null;
	 }

	 public List<SettingsInfo> getSettingsInfos(String envName) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getSettingsInfos()");

		 try {
			 File settingsFile = new File(Utility.getProjectHome() + SETTINGS_INFO_FILE_NAME);
			 if (settingsFile.exists()) {
				 ConfigurationReader reader = new ConfigurationReader(settingsFile);
				 List<Configuration> configurations = reader.getConfigByEnv(envName);
				 return getAsSettingsInfo(configurations);
			 }
			 return new ArrayList<SettingsInfo>(1);

		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 public List<SettingsInfo> getSettingsInfos(String type, String projectCode, String envName) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getSettingsInfos(String type, String projectCode, String envName)");

		 if (StringUtils.isEmpty(envName) || StringUtils.isEmpty(type)) {
			 throw new PhrescoException("Enviroment name or type is empty");
		 }

		 Project project = getProject(projectCode);
		 if (project == null) {
			 throw new PhrescoException("Project code should not be valid");
		 }
		 String techId = project.getProjectInfo().getTechnology().getId();
		 List<SettingsInfo> settingsInfos = filterSettingsInfo(getSettingsInfos(envName), type, techId);
		 if (CollectionUtils.isNotEmpty(settingsInfos)) {
			 return settingsInfos;
		 }
		 settingsInfos = configurationsByEnvName(envName, project);
		 return filterConfigurations(settingsInfos, type);
	 }

	 public List<SettingsInfo> getSettingsInfos(String envName, String type, List<String> appliesTo, String settingsName) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getSettingsInfos(String type, String projectCode, String envName)");

		 if (StringUtils.isEmpty(envName) || StringUtils.isEmpty(type)) {
			 throw new PhrescoException("Enviroment name or type is empty");
		 }

		 List<SettingsInfo> settingsInfos = filterConfigurations(getSettingsInfos(envName), type);
		 //TODO: For settings info check whether the one server for applied type cannot create it again.
		 /*List<SettingsInfo> filteredInfos = new ArrayList<SettingsInfo>(settingsInfos.size());
        for (String techId : appliesTo) {
            filteredInfos.addAll(filterSettingsInfo(settingsInfos, type, techId));
        }

        return filteredInfos;*/
		 return settingsInfos;
	 }


	 public SettingsInfo getSettingsInfo(String name, String type, String projectCode, String envName) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getSettingsInfo(String name, String type, String projectCode)");
		 S_LOGGER.debug("getSettingsInfo() Name = " + name);
		 S_LOGGER.debug("getSettingsInfo() Type = " + type);
		 S_LOGGER.debug("getSettingsInfo() ProjectCode = " + projectCode);


		 if (StringUtils.isEmpty(name) || StringUtils.isEmpty(type)) {
			 throw new PhrescoException("Settings/Configuration name or type is empty");
		 }

		 SettingsInfo settingsInfo = getSettingsInfo(name, envName);
		 if (settingsInfo != null) {
			 return settingsInfo;
		 }
		 Project project = getProject(projectCode);
		 if (project == null) {
			 throw new PhrescoException("Project should not be null");
		 }
		 List<SettingsInfo> configurations = configurationsByEnvName(envName, project);
		 configurations = filterConfigurations(configurations, type);
		 for (SettingsInfo configuration : configurations) {
			 if (configuration.getName().equals(name)) {
				 return configuration;
			 }
		 }

		 return null;
	 }

	 public List<SettingsInfo> getSettingsInfos() throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.configurations(Project project)");

		 File settingsFile = new File(Utility.getProjectHome() + SETTINGS_INFO_FILE_NAME);

		 if (settingsFile.exists()) {
			 return getAllSettingsInfos(settingsFile);
		 }
		 return new ArrayList<SettingsInfo>(1);
	 }

	 //TODO: Remove the below method once the plugins are adapted for configuration.xml
	 public SettingsInfo getSettingsInfo(String name, String type, String projectCode) throws PhrescoException {
		 return null;
	 }

	 public void deleteSettingsInfos(Map<String, List<String>> selectedConfigs) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteSettingsInfos(List<String> names)");
		 S_LOGGER.debug("deleteSettingsInfos() Names = "+selectedConfigs);
		 if (MapUtils.isEmpty(selectedConfigs)) {
			 throw new PhrescoException("Names should not be empty");
		 }

		 File configFile = new File(Utility.getProjectHome() + SETTINGS_INFO_FILE_NAME);
		 deleteConfigurations(selectedConfigs, configFile);
	 }

	 public List<SettingsInfo> configurations(Project project) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.configurations(Project project)");
		 S_LOGGER.debug(" configurations() ProjectCode= : " + project.getProjectInfo().getCode());

		 File createdConfigurationXml = createConfigurationXml(project.getProjectInfo().getCode());
		 return getAllSettingsInfos(createdConfigurationXml);
	 }


	 private File createConfigurationXml (String projectCode)  throws PhrescoException {
		 File configFile = new File(getConfigurationPath(projectCode).toString());
		 if (!configFile.exists()) {
			 List<Environment> envs = getEnvFromService();
			 createEnvironments(configFile, envs, true);
		 }
		 return configFile;
	 }

	 private List<SettingsInfo> getAllSettingsInfos(File configFile) throws PhrescoException {

		 try {
			 ConfigurationReader configReader = new ConfigurationReader(configFile);
			 return getAsSettingsInfo(configReader.getConfigurations());
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 private List<SettingsInfo> getAsSettingsInfo(List<Configuration> configurations) {
		 List<SettingsInfo> settingsInfos = new ArrayList<SettingsInfo>(configurations.size());
		 for (Configuration configuration : configurations) {
			 SettingsInfo settingsInfo = new SettingsInfo(configuration);
			 settingsInfos.add(settingsInfo);
		 }
		 return settingsInfos;
	 }


	 private List<SettingsInfo> getAllSettingsInfos(File configFile, String configName) throws PhrescoException {

		 try {
			 ConfigurationReader configReader = new ConfigurationReader(configFile);
			 return getAsSettingsInfo(configReader.getConfigurations(), configName );
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 private List<SettingsInfo> getAsSettingsInfo(List<Configuration> configurations, String configName) {
		 List<SettingsInfo> settingsInfos = new ArrayList<SettingsInfo>(configurations.size());
		 for (Configuration configuration : configurations) {
			 if(!configuration.getName().equals(configName)){
				 SettingsInfo settingsInfo = new SettingsInfo(configuration);
				 settingsInfos.add(settingsInfo);
			 }
		 }
		 return settingsInfos;
	 }

	 public SettingsInfo configuration(String name, String envName, Project project) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.configuration(String name, Project project)");
		 S_LOGGER.debug("configuration()  Name = "+ name);

		 if (StringUtils.isEmpty(name)) {
			 throw new PhrescoException("Configuration name should not be empty");
		 }

		 String configPath = getConfigurationPath(project.getProjectInfo().getCode()).toString();
		 try {
			 ConfigurationReader configReader = new ConfigurationReader(new File(configPath));
			 List<Configuration> configurations = configReader.getConfigByEnv(envName);
			 for (Configuration configuration : configurations) {
				 if (name.equals(configuration.getName())) {
					 return new SettingsInfo(configuration);
				 }
			 }
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }

		 return null;
	 }

	 public List<SettingsInfo> configurations(Project project, String envName, String type, String configName) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.configuration(String name, Project project)");

		 if (StringUtils.isEmpty(type)) {
			 throw new PhrescoException("Configuration name should not be empty");
		 }

		 try {
			 List<SettingsInfo> settingsInfos = configurationsByEnvName(envName, project);
			 return filterConfigurations(settingsInfos, type);
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }


	 public List<SettingsInfo> configurationsByEnvName(String envName, Project project) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.configurations(String type, Project project)");

		 try {
			 String configPath = getConfigurationPath(project.getProjectInfo().getCode()).toString();
			 ConfigurationReader configReader = new ConfigurationReader(new File (configPath));
			 return getAsSettingsInfo(configReader.getConfigByEnv(envName));
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 public List<SettingsInfo> configurationsByEnvName(String envName) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.configurations(String type, Project project)");
		 try {
			 String configPath = Utility.getProjectHome() + SETTINGS_INFO_FILE_NAME;
			 ConfigurationReader configReader = new ConfigurationReader(new File (configPath));
			 return getAsSettingsInfo(configReader.getConfigByEnv(envName));
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 public List<SettingsInfo> configurations(String type, Project project) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.configurations(String type, Project project)");

		 List<SettingsInfo> configurations = configurations(project);
		 if (CollectionUtils.isEmpty(configurations)) {
			 return null;
		 }

		 return filterConfigurations(configurations, type);
	 }

	 private List<SettingsInfo> filterConfigurations(List<SettingsInfo> configurations, String type) {
		 List<SettingsInfo> filterConfigs = new ArrayList<SettingsInfo>(configurations.size());
		 for (SettingsInfo configuration : configurations) {
			 if (configuration.getType().equals(type)) {
				 filterConfigs.add(configuration);
			 }	
		 }
		 return filterConfigs;
	 }

	 public void createConfiguration(SettingsInfo info, String selectedEnvNames, Project project) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.createConfiguration(SettingsInfo info, Project project)");
		 if(info == null) throw new PhrescoException("Settings info should not be null or empty");
		 S_LOGGER.debug("createConfiguration()  Name = "+ info.getName());

		 try {
			 String path = getConfigurationPath(project.getProjectInfo().getCode()).toString();
			 createSettingsInfo(info, selectedEnvNames, new File(path));
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 public void updateConfiguration(String envName, String oldConfigName, SettingsInfo settingsInfo, Project project) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.updateConfiguration(SettingsInfo info, Project project, String name)");
		 S_LOGGER.debug("updateConfiguration() ProjectInfo = "+ project.getProjectInfo());
		 S_LOGGER.debug("updateConfiguration() Name = "+ settingsInfo.getName());

		 File configFile = new File(getConfigurationPath(project.getProjectInfo().getCode()).toString());
		 updateConfiguration(envName, oldConfigName, settingsInfo, configFile);
	 }

	 private void updateConfiguration(String envName, String oldConfigName, SettingsInfo settingsInfo, File configFile) throws PhrescoException {
		 try {
			 ConfigurationReader configReader = new ConfigurationReader(configFile);
			 ConfigurationWriter configWriter = new ConfigurationWriter(configReader, false);
			 configWriter.updateConfiguration(envName, oldConfigName, settingsInfo);
			 configWriter.saveXml(configFile);
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 public void deleteConfigurations(Map<String, List<String>> selectedConfigs, Project project) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteConfigurations(List<String> names, Project project)");
		 S_LOGGER.debug("deleteConfigurations()  Names = "+ selectedConfigs);

		 if (MapUtils.isEmpty(selectedConfigs)) {
			 throw new PhrescoException("Names should not be empty");
		 }

		 File configFile = new File(getConfigurationPath(project.getProjectInfo().getCode()).toString());
		 deleteConfigurations(selectedConfigs, configFile);
	 }

	 private void deleteConfigurations(Map<String, List<String>> selectedConfigs, File configFile) throws PhrescoException{
		 try {
			 ConfigurationReader reader = new ConfigurationReader(configFile);
			 ConfigurationWriter writer = new ConfigurationWriter(reader, false);
			 writer.deleteConfigurations(selectedConfigs);
			 writer.saveXml(configFile);
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 private void fillProjects(File[] dotProjectFiles, List<Project> projects) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.fillProjects(File[] dotProjectFiles, List<Project> projects)");

		 if(ArrayUtils.isEmpty(dotProjectFiles)) {
			 return;
		 }

		 Gson gson = new Gson();
		 BufferedReader reader = null;

		 for (File dotProjectFile : dotProjectFiles) {
			 try {
				 reader = new BufferedReader(new FileReader(dotProjectFile));
				 ProjectInfo projectInfo = gson.fromJson(reader, ProjectInfo.class);
				 projects.add(new ProjectImpl(projectInfo));
			 } catch (FileNotFoundException e) {
				 throw new PhrescoException(e);
			 } finally {
				 Utility.closeStream(reader);
			 }
		 }
	 }

	 private void createSettingsInfo(SettingsInfo settingsInfo, String selectedEnvNames, File path) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.createSettingsInfo(SettingsInfo info, File path)");
		 S_LOGGER.debug("fillProjects() File Path= " +path.getPath());

		 try {
			 ConfigurationReader configReader = new ConfigurationReader(path);
			 ConfigurationWriter configWriter = new ConfigurationWriter(configReader, false);
			 configWriter.createConfiguration(selectedEnvNames, settingsInfo);
			 configWriter.saveXml(path);
		 } catch (Exception e) {

			 throw new PhrescoException(e);
		 } 

	 }

	 public void createEnvironments(Project project, List<Environment> selectedEnvs, boolean isNewFile) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.createEnvironments(List<String> envNames)");

		 String configPath = getConfigurationPath(project.getProjectInfo().getCode()).toString();
		 createEnvironments(new File(configPath), selectedEnvs, isNewFile);
	 }

	 public void createEnvironments(List<Environment> selectedEnvs) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.createEnvironments(List<String> envNames, Project project)");

		 File settingsFile = new File(Utility.getProjectHome() + SETTINGS_INFO_FILE_NAME);
		 createEnvironments(settingsFile, selectedEnvs, !settingsFile.exists());
	 }

	 private void createEnvironments(File configPath, List<Environment> selectedEnvs, boolean isNewFile) throws PhrescoException {
		 try {
			 ConfigurationReader reader = new ConfigurationReader(configPath);
			 ConfigurationWriter writer = new ConfigurationWriter(reader, isNewFile);
			 writer.createEnvironment(selectedEnvs);
			 writer.saveXml(configPath);
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 public void deleteEnvironments(List<String> envNames) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteEnvironments(List<String> envNames)");
		 File configXml = new File(Utility.getProjectHome() + SETTINGS_INFO_FILE_NAME);
		 deleteEnvironments(envNames, configXml);    	
	 }

	 public void deleteEnvironments(List<String> envNames, Project project) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteEnvironments(List<String> envNames, Project project)");
		 File configXml = new File(getConfigurationPath(project.getProjectInfo().getCode()).toString());
		 deleteEnvironments(envNames, configXml);
	 }

	 private void deleteEnvironments(List<String> envNames, File configXml) throws PhrescoException {
		 try {
			 ConfigurationReader reader = new ConfigurationReader(configXml);
			 ConfigurationWriter writer = new ConfigurationWriter(reader, false);
			 writer.deleteEnvironments(envNames);
			 writer.saveXml(configXml);
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 public List<SettingsInfo> readSettingsInfo(File path) throws IOException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.readSettingsInfo(File path)");
		 S_LOGGER.debug("readSettingsInfo() File path = "+path.getPath());
		 if (!path.exists()) {
			 S_LOGGER.error("readSettingsInfo() > " + FrameworkImplConstants.ERROR_FILE_PATH_INCORRECT + path);
			 return new ArrayList<SettingsInfo>(1);
		 }

		 BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
		 Gson gson = new Gson();
		 Type type = new TypeToken<List<SettingsInfo>>(){}.getType();

		 List<SettingsInfo> settingsInfos = gson.fromJson(bufferedReader, type);
		 Collections.sort(settingsInfos, new SettingsInfoComparator());
		 bufferedReader.close();
		 return settingsInfos;
	 }

	 public List<BuildInfo> getBuildInfos(Project project) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getBuildInfos(Project project)");
		 try {
			 return readBuildInfo(new File(Utility.getProjectHome() + project.getProjectInfo().getCode() + File.separator + BUILD_DIR + File.separator + BUILD_INFO_FILE_NAME));
		 } catch (IOException e) {
			 throw new PhrescoException(e);
		 }
	 }

	 public List<BuildInfo> readBuildInfo(File path) throws IOException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.readBuildInfo(File path)");
		 S_LOGGER.debug("getBuildInfos() File Path = "+path.getPath());

		 if (!path.exists()) {

			 S_LOGGER.error("readBuildInfo() > " + FrameworkImplConstants.ERROR_FILE_PATH_INCORRECT + path);

			 return new ArrayList<BuildInfo>(1);
		 }

		 BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
		 Gson gson = new Gson();
		 Type type = new TypeToken<List<BuildInfo>>(){}.getType();

		 List<BuildInfo> buildInfos = gson.fromJson(bufferedReader, type);
		 Collections.sort(buildInfos, new BuildInfoComparator());
		 bufferedReader.close();

		 return buildInfos;
	 }

	 private StringBuilder getConfigurationPath(String projectCode) {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getConfigurationPath(Project project)");
		 S_LOGGER.debug("removeSettingsInfos() ProjectCode = " + projectCode);


		 StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		 builder.append(projectCode);
		 builder.append(File.separator);
		 builder.append(FOLDER_DOT_PHRESCO);
		 builder.append(File.separator);
		 builder.append(CONFIGURATION_INFO_FILE_NAME);

		 return builder;
	 }


	 private List<SettingsInfo> filterSettingsInfo(List<SettingsInfo> infos, String type, String technolgoyType) {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.filterSettingsInfo(List<SettingsInfo> infos, String type)");
		 S_LOGGER.debug("filterSettingsInfo() Type = "+type);

		 List<SettingsInfo> filteredList = new ArrayList<SettingsInfo>();
		 for (SettingsInfo settingsInfo : infos) {
			 if (settingsInfo.getType().equals(type) && settingsInfo.getAppliesTo().contains(technolgoyType)) {
				 filteredList.add(settingsInfo);
			 }
		 }

		 Collections.sort(filteredList, new SettingsInfoComparator());
		 return filteredList;
	 }

	 private class PhrescoFileNameFilter implements FilenameFilter {
		 private String filter_;
		 public PhrescoFileNameFilter(String filter) {
			 filter_ = filter;
		 }

		 public boolean accept(File dir, String name) {
			 return name.endsWith(filter_);
		 }
	 }

	 @Override
	 public BuildInfo getBuildInfo(Project project, int buildNumber) throws PhrescoException {
		 List<BuildInfo> buildInfos = getBuildInfos(project);
		 if (CollectionUtils.isEmpty(buildInfos)) {
			 return null;
		 }

		 for (BuildInfo buildInfo : buildInfos) {
			 if (buildInfo.getBuildNo() == buildNumber) {
				 return buildInfo;
			 }
		 }

		 return null;
	 }

	 @Override
	 public List<BuildInfo> getBuildInfos(Project project, int[] buildNumbers) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getBuildInfos(Project project, int[] buildNumbers)");
		 S_LOGGER.debug("getBuildInfos() Project Information = "+project.getProjectInfo() );

		 List<BuildInfo> buildInfos = getBuildInfos(project);
		 if (CollectionUtils.isEmpty(buildInfos)) {
			 return Collections.emptyList();
		 }

		 List<BuildInfo> selectedInfos = new ArrayList<BuildInfo>(8);
		 BuildInfo buildInfo = null;
		 for (int i = 0; i < buildNumbers.length; i++) {
			 buildInfo = getBuildInfo(project, buildNumbers[i]);
			 if (buildInfo == null) {
				 continue;
			 }

			 selectedInfos.add(buildInfo);
		 }

		 return selectedInfos;
	 }

	 @Override
	 public void deleteBuildInfos(Project project, int[] buildNumbers) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteBuildInfos(Project project, int[] buildNumbers)");

		 List<BuildInfo> buildInfos = getBuildInfos(project);

		 S_LOGGER.debug("deleteBuildInfos() buildNumbers = "+buildNumbers);

		 List<BuildInfo> selectedInfos = getBuildInfos(project, buildNumbers);
		 if (CollectionUtils.isEmpty(selectedInfos)) {
			 return;
		 }

		 //Delete the build archives
		 try {
			 deleteBuildArchive(project, selectedInfos);
		 } catch (IOException e) {
			 throw new PhrescoException(e);
		 }

		 //Delete the entry from build.info
		 Iterator<BuildInfo> iterator = buildInfos.iterator();
		 for (BuildInfo selectedInfo : selectedInfos) {
			 while (iterator.hasNext()) {
				 BuildInfo buildInfo = iterator.next();
				 if (buildInfo.getBuildNo() == selectedInfo.getBuildNo()) {
					 iterator.remove();
					 break;
				 }
			 }
		 }

		 StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		 builder.append(project.getProjectInfo().getCode());
		 builder.append(File.separator);
		 builder.append(FrameworkConstants.BUILD_DIR);
		 builder.append(File.separator);
		 builder.append(FrameworkConstants.BUILD_INFO_FILE_NAME);
		 try {
			 writeBuildInfo(buildInfos, new File(builder.toString()));
		 } catch (IOException e) {
			 throw new PhrescoException(e);
		 }
	 }

	 private void deleteBuildArchive(Project project, List<BuildInfo> selectedInfos) throws IOException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteBuildArchive(Project project, List<BuildInfo> selectedInfos)");
		 File file = null;
		 String delFilename = null;
		 for (BuildInfo selectedInfo : selectedInfos) {
			 if (TechnologyTypes.IPHONES.contains(project.getProjectInfo().getTechnology().getId())) {
				 String deleivarables = selectedInfo.getDeliverables();
				 String buildNameSubstring = deleivarables.substring(deleivarables.lastIndexOf("/") + 1);
				 delFilename = buildNameSubstring;
				 // Delete build folder
				 deleteBuilFolder(project, delFilename.subSequence(0, delFilename.length() - 4).toString());
				 //Delete zip file
				 file = new File(getBuildInfoHome(project) + delFilename);
				 file.delete();
			 } else if (TechnologyTypes.ANDROIDS.contains(project.getProjectInfo().getTechnology().getId())) {
				 // Delete zip file
				 String deleivarables = selectedInfo.getDeliverables();
				 delFilename = deleivarables;
				 file = new File(getBuildInfoHome(project) + delFilename);
				 file.delete();	            
				 //Delete apk file
				 delFilename = selectedInfo.getBuildName();
				 file = new File(getBuildInfoHome(project) + delFilename);
				 file.delete();
			 } else {
				 //Delete zip file
				 delFilename = selectedInfo.getBuildName();
				 file = new File(getBuildInfoHome(project) + delFilename);
				 file.delete();
			 }
		 }
	 }

	 private void deleteBuilFolder(Project project, String buildFolderPath) throws IOException {
		 FileUtils.deleteDirectory(new File(getBuildInfoHome(project) + buildFolderPath));
	 }

	 private String getBuildInfoHome(Project project) {
		 StringBuilder builder = new StringBuilder();
		 builder.append(Utility.getProjectHome());
		 builder.append(project.getProjectInfo().getCode());
		 builder.append(File.separator);
		 builder.append(FrameworkConstants.BUILD_DIR);
		 builder.append(File.separator);
		 return builder.toString();
	 }

	 private void writeBuildInfo(List<BuildInfo> buildInfos, File path) throws IOException {
		 Gson gson = new Gson();
		 String buildInfoJson = gson.toJson(buildInfos);
		 writeJson(buildInfoJson, path);
	 }

	 private void writeJson(String json, File path) throws IOException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.writeJson(String json, File path)");
		 FileWriter writer = null;
		 try {
			 S_LOGGER.debug("writeJson()  File path = " +path.getPath());
			 writer = new FileWriter(path);
			 writer.write(json);
			 writer.flush();
		 } finally {
			 if(writer != null) {
				 try {
					 writer.close();
				 } catch (IOException e) {
					 S_LOGGER.warn("writeJson() > error inside finally");

				 }
			 }
		 }
	 }

	 @Override
	 public List<VideoInfo> getVideoInfos() throws PhrescoException {
		 try {
			 RestClient<VideoInfo> videoInfosClient = getServiceManager().getRestClient(REST_API_ADMIN + REST_API_VIDEOS);
			 GenericType<List<VideoInfo>> genericType = new GenericType<List<VideoInfo>>(){};
			 List<VideoInfo> videoInfos = videoInfosClient.get(genericType);
			 return videoInfos;
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error(ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
	 }

	 @Override
	 public List<VideoType> getVideoTypes(String name) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getVideoTypes(String name)");
		 List<VideoInfo> videoInfos = getVideoInfos();
		 for (VideoInfo videoInfo : videoInfos) {
			 S_LOGGER.debug("getVideoTypes() Name = "+name);
			 if(videoInfo.getName().equals(name)) {
				 return videoInfo.getVideoList();
			 }
		 }
		 return null;
	 }

	 @Override
	 public List<ModuleGroup> getCoreModules(String techId) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getCoreModules(Technology technology)");
		 S_LOGGER.debug("getCoreModules() TechnologyName = "+techId);
		 
		 try {
			 List<ModuleGroup> coreModules = coreModulesMap.get(techId);
			 if (CollectionUtils.isNotEmpty(coreModules)) {
				 return coreModules;
			 }
	
			 coreModules = new ArrayList<ModuleGroup>();
			 RestClient<ModuleGroup> moduleGroupClient = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_MODULESBYID);
			 moduleGroupClient.setPath(techId);
			 GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>(){};
			 List<ModuleGroup> modules = moduleGroupClient.get(genericType);
			 if (CollectionUtils.isNotEmpty(modules)) {
				 for (ModuleGroup module : modules) {
					 if (module.isCore()) {
						 coreModules.add(module);
					 }
				 }
			 }
	
			 Collections.sort(coreModules, new ModuleComparator());
			 coreModulesMap.put(techId, coreModules);
			 return coreModules;
		 } catch (Exception e) {
			 throw new PhrescoException(e); 
		 }
	 }

	 @Override
	 public List<ModuleGroup> getCustomModules(String techId) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getCustomModules(Technology technology)");
		 S_LOGGER.debug("getCustomModules() TechnologyId = "+techId);

		 try {
			 List<ModuleGroup> customModules = customModulesMap.get(techId);
			 if (CollectionUtils.isNotEmpty(customModules)) {
				 return customModules;
			 }
	
			 customModules = new ArrayList<ModuleGroup>();
			 RestClient<ModuleGroup> moduleGroupClient = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_MODULESBYID);
			 moduleGroupClient.setPath(techId);
			 GenericType<List<ModuleGroup>> genericType = new GenericType<List<ModuleGroup>>(){};
			 List<ModuleGroup> modules = moduleGroupClient.get(genericType);
			 if (CollectionUtils.isNotEmpty(modules)) {
				 for (ModuleGroup module : modules) {
					 if (!module.isCore()) {
						 customModules.add(module);
					 }
				 }
			 }
	
			 Collections.sort(customModules, new ModuleComparator());
			 customModulesMap.put(techId, customModules);
			 return customModules;
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 @Override
	 public void createJob(Project project, CIJob job) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.createJob(Project project, CIJob job)");
		 FileWriter writer = null;
		 try {
			 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			 CIJobStatus jobStatus = ciManager.createJob(job);
			 if (jobStatus.getCode() == -1) {
				 throw new PhrescoException(jobStatus.getMessage());
			 }

			 S_LOGGER.debug("ProjectInfo = " + project.getProjectInfo());

			 File ciJobFile = new File(getCIJobPath(project));
			 Gson gson = new Gson();
			 String jobJson = gson.toJson(job);
			 writer = new FileWriter(ciJobFile);
			 writer.write(jobJson);
			 writer.flush();
		 } catch (IOException e) {
			 throw new PhrescoException(e);
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error(ex.getLocalizedMessage());

			 throw new PhrescoException(ex);
		 } finally {
			 if (writer != null) {
				 try {
					 writer.close();
				 } catch (IOException e) {
					 S_LOGGER.error(e.getLocalizedMessage());

				 }
			 }
		 }
	 }

	 @Override
	 public void updateJob(Project project, CIJob job) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.updateJob(Project project, CIJob job)");

		 FileWriter writer = null;
		 try {
			 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			 CIJobStatus jobStatus = ciManager.updateJob(job);
			 if (jobStatus.getCode() == -1) {
				 throw new PhrescoException(jobStatus.getMessage());
			 }

			 S_LOGGER.debug("getCustomModules() ProjectInfo = "+project.getProjectInfo());

			 File ciJobFile = new File(getCIJobPath(project));
			 Gson gson = new Gson();
			 String jobJson = gson.toJson(job);
			 writer = new FileWriter(ciJobFile);
			 writer.write(jobJson);
			 writer.flush();
		 } catch (IOException e) {
			 throw new PhrescoException(e);
		 } catch (ClientHandlerException ex) {

			 S_LOGGER.error(ex.getLocalizedMessage());

			 throw new PhrescoException(ex);
		 } finally {
			 if (writer != null) {
				 try {
					 writer.close();
				 } catch (IOException e) {
					 S_LOGGER.error(e.getLocalizedMessage());

				 }
			 }
		 }
	 }

	 @Override
	 public CIJob getJob(Project project) throws PhrescoException {
		 Gson gson = new Gson();
		 try {
			 BufferedReader br = new BufferedReader(new FileReader(getCIJobPath(project)));
			 CIJob job = gson.fromJson(br, CIJob.class);
			 br.close();
			 return job;
		 } catch (FileNotFoundException e) {
			 return null;
			 //            throw new PhrescoException(e);
		 } catch (IOException e) {
			 throw new PhrescoException(e);
		 }
	 }

	 @Override
	 public CIJobStatus buildJob(Project project) throws PhrescoException {
		 try {
			 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			 CIJob ciJob = getJob(project);
			 return ciManager.buildJob(ciJob);
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error(ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
	 }

	 @Override
	 public List<CIBuild> getBuilds(Project project) throws PhrescoException {
		 try {
			 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			 CIJob ciJob = getJob(project);
			 return ciManager.getCIBuilds(ciJob);
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error(ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
	 }

	 @Override
	 public int getTotalBuilds(Project project) throws PhrescoException {
		 try {
			 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			 CIJob ciJob = getJob(project);
			 return ciManager.getTotalBuilds(ciJob);
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error(ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
	 }

	 private String getCIJobPath(Project project) {
		 StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		 builder.append(project.getProjectInfo().getCode());
		 builder.append(File.separator);
		 builder.append(FOLDER_DOT_PHRESCO);
		 builder.append(File.separator);
		 builder.append(CI_JOB_INFO_NAME);

		 return builder.toString();
	 }

	 @Override
	 public String sendReport(LogInfo loginfo) throws PhrescoException {

		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.sendReport(LogInfo loginfo)");
		 S_LOGGER.debug("Loginfo values : " + loginfo.toString());

		 try {
			 ClientResponse response = PhrescoFrameworkFactory.getServiceManager().sendReport(loginfo);
			 if (response.getStatus() != 204) {
				 //            		throw new PhrescoException("Error Report sending failed");
				 return "Report submition failed";
			 } else {
				 return "Report submitted successfully";
			 }
		 } catch (ClientHandlerException e) {
			 S_LOGGER.error(e.getLocalizedMessage());

			 throw new PhrescoException(e);
		 }
	 }

	 public void getJdkHomeXml() throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getJdkHomeXml");

		 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
		 ciManager.getJdkHomeXml();
	 }

	 public void getMavenHomeXml() throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getMavenHomeXml");
		 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
		 ciManager.getMavenHomeXml();
	 }


	 private void setAdminConfigInfosFrmService() throws PhrescoException {
		 if (CollectionUtils.isEmpty(adminConfigInfos)) {
			 adminConfigInfos = PhrescoFrameworkFactory.getServiceManager().getAdminConfig();
		 }
	 }

	 public String getJforumPath() throws PhrescoException {
		 if (CollectionUtils.isEmpty(adminConfigInfos)) {
			 setAdminConfigInfosFrmService();
		 }

		 for (AdminConfigInfo adminConfigInfo : adminConfigInfos) {
			 if (ADMIN_CONFIG_JFORUM_PATH.equals(adminConfigInfo.getKey())) {
				 return adminConfigInfo.getValue();
			 }
		 }
		 return null;
	 }

	 public List<ValidationResult> validate() throws PhrescoException {
		 //1.Check for phresco directory structure validation
		 //2.Check for Environment settings like Android Home, Node JS Home
		 List<ValidationResult> results = new ArrayList<ValidationResult>(64);
		 List<Validator> validators = PhrescoFrameworkFactory.getValidators(TechnologyTypes.ALL_TECHS);
		 for (Validator validator : validators) {
			 results.addAll(validator.validate(null));
		 }

		 return results;
	 }

	 public List<ValidationResult> validate(Project project) throws PhrescoException {
		 //1.Validate the directory structure based on the archetypes
		 //2.Validate for changes in the list of modules in projects
		 //	info and the actual list of modules present in the directory
		 List<ValidationResult> results = new ArrayList<ValidationResult>(64);
		 Technology technology = project.getProjectInfo().getTechnology();
		 List<Validator> validators = PhrescoFrameworkFactory.getValidators(technology.getId());

		 for (Validator validator : validators) {

			 results.addAll(validator.validate(project.getProjectInfo().getCode()));
		 }

		 return results;
	 }

	 public CIJobStatus deleteCI(Project project, List<String> builds) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteCI()");
		 try {
			 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			 CIJob ciJob = getJob(project);
			 CIJobStatus deleteCI = ciManager.deleteCI(ciJob, builds);
			 if(builds == null && deleteCI.getCode() != FrameworkConstants.JOB_STATUS_NOTOK) { // when job is deleted suceesfully , cijob info which is inside .phresco folder also should be deleted

				 S_LOGGER.debug("Job deleted successfully");
				 File ciJobInfo = new File(getCIJobPath(project));
				 boolean success = ciJobInfo.delete();
				 if (!success) {
					 S_LOGGER.debug("cijob.info File deletion failed");

				 } else {
					 S_LOGGER.debug("cijob.info File deletion success");
				 }
			 }
			 return deleteCI;
		 } catch (ClientHandlerException ex) {

			 S_LOGGER.error("Entered into catch block of ProjectAdministratorImpl.deleteCI()" + ex.getLocalizedMessage());

			 throw new PhrescoException(ex);
		 }
	 }

	 public int getProgressInBuild(Project project) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.isBuilding()");
		 try {
			 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			 CIJob ciJob = getJob(project);
			 int isBuilding = ciManager.getProgressInBuild(ciJob);
			 return isBuilding;
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error("Entered into catch block of ProjectAdministratorImpl.isBuilding()" + ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
	 }

	 public void getEmailExtPlugin() throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getEmailExtPlugin");
		 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
		 ciManager.getEmailExtPlugin();
	 }

	 public void deleteDoNotCheckin(Project project) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.deleteDoNotCheckin");

		 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
		 CIJob ciJob = getJob(project);
		 ciManager.deleteDoNotCheckin(ciJob);
	 }

	 public List<Environment> getEnvFromService() throws PhrescoException {
		 try {
			 return PhrescoFrameworkFactory.getServiceManager().getEnvInfoFromService();
		 } catch (ClientHandlerException ex) {
			 S_LOGGER.error(ex.getLocalizedMessage());
			 throw new PhrescoException(ex);
		 }
	 }

	 public List<Environment> getEnvironments(Project project) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getEnvironments(Project project)");
		 String configPath = getConfigurationPath(project.getProjectInfo().getCode()).toString();
		 return getEnvironments(new File(configPath));
	 }

	 public List<Environment> getEnvironments() throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getEnvironments()");
		 return getEnvironments(new File(Utility.getProjectHome() + SETTINGS_INFO_FILE_NAME));
	 }

	 private List<Environment> getEnvironments(File configFile) throws PhrescoException {
		 try {
			 if (configFile.exists()) {
				 ConfigurationReader reader = new ConfigurationReader(configFile);
				 List<Environment> environments = reader.getEnvironments();
				 return environments; 
			 }
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
		 return new ArrayList<Environment>(1);
	 }

	 public Collection<String> getEnvNames(Project project) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getEnvNames(Project project)");
		 String configPath = getConfigurationPath(project.getProjectInfo().getCode()).toString();
		 return getEnvNames(new File (configPath));
	 }

	 public Collection<String> getEnvNames() throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getEnvNames()");
		 return getEnvNames(new File(Utility.getProjectHome() + SETTINGS_INFO_FILE_NAME));
	 }

	 private Collection<String> getEnvNames(File configFile) throws PhrescoException {
		 try {
			 ConfigurationReader reader = new ConfigurationReader(configFile);
			 return reader.getEnvironmentNames();
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 public void updateTestConfiguration(Project project, String selectedEnvs, String browser, String resultConfigXml) throws PhrescoException {
		 try {
			 String projectCode = project.getProjectInfo().getCode();
			 List<SettingsInfo> settingsInfos = new ArrayList<SettingsInfo>(2);
			 settingsInfos.addAll(getSettingsInfos(Constants.SETTINGS_TEMPLATE_SERVER, projectCode, selectedEnvs));
			 ConfigurationReader configReader = new ConfigurationReader(new File(resultConfigXml));
			 ConfigurationWriter configWriter = new ConfigurationWriter(configReader, false);
			 for (SettingsInfo settingsInfo : settingsInfos) {
				 configWriter.updateTestConfiguration(settingsInfo, browser, resultConfigXml);
			 }
			 configWriter.saveXml(new File(resultConfigXml));

		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 @Override
	 public String getDefaultEnvName(String projectCode) {
		 return "Production";
	 }

	 private void updateProjectPOM(ProjectInfo projectInfo) throws PhrescoException {
		 try {
			 String path = Utility.getProjectHome() + projectInfo.getCode() + File.separator + POM_FILE;
			 PomProcessor processor = new PomProcessor(new File(path));
			 Model model = processor.getModel();
			 if (StringUtils.isNotEmpty(projectInfo.getVersion())) {
				 model.setVersion(projectInfo.getVersion());
			 }
			 if (StringUtils.isNotEmpty(projectInfo.getGroupId())) {
				 model.setGroupId(projectInfo.getGroupId());
			 }
			 if (StringUtils.isNotEmpty(projectInfo.getArtifactId())) {
				 model.setArtifactId(projectInfo.getArtifactId());
			 }
			 processor.save();
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }

	 /**
	/* Delete the Sql Folder 
	  */
	 public void deleteSqlFolder(List<String> dbList , ProjectInfo projectInfo) throws PhrescoException {
		 initializeSqlMap();
		 try {
			 File sqlPath = new File(Utility.getProjectHome() + File.separator
					 + projectInfo.getCode() + File.separator
					 + sqlFolderPathMap.get(projectInfo.getTechnology().getId()));
			 if (CollectionUtils.isNotEmpty(dbList)) {
				 for (String dbVersion : dbList) {
					 File dbVersionFolder = new File(sqlPath, dbVersion.toLowerCase());
					 FileUtils.deleteDirectory(dbVersionFolder);
				 }
			 }
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
	 }
	 
	 protected void createSqlFolder(ProjectInfo info, File path) throws PhrescoException {
			String databaseType = "";
			try {
				String parentFile = path.getParentFile().getParent();
				List<Database> databaseList = info.getTechnology().getDatabases();
				String techId = info.getTechnology().getId();
				if (databaseList == null || databaseList.size() == 0) {
					return;
				}
				File mysqlFolder = new File(parentFile, sqlFolderPathMap.get(techId) + Constants.DB_MYSQL);
				File mysqlVersionFolder = getMysqlVersionFolder(mysqlFolder);
				for (Database db : databaseList) {
					databaseType = db.getName().toLowerCase();
					List<String> versions = db.getVersions();
					for (String version : versions) {
						String sqlPath = databaseType + File.separator + version.trim();
						File sqlFolder = new File(parentFile, sqlFolderPathMap.get(techId) + sqlPath);
						sqlFolder.mkdirs();
						if (databaseType.equals(Constants.DB_MYSQL) && mysqlVersionFolder != null
								&& !(mysqlVersionFolder.getPath().equals(sqlFolder.getPath()))) {						
							FileUtils.copyDirectory(mysqlVersionFolder, sqlFolder);
						} else {
							File sqlFile = new File(sqlFolder, Constants.SITE_SQL);
							sqlFile.createNewFile();
						}
					}
				}
			} catch (IOException e) {
				throw new PhrescoException(e);
			}
		}
	 
		private File getMysqlVersionFolder(File mysqlFolder) {
			File[] mysqlFolderFiles = mysqlFolder.listFiles();
			if (mysqlFolderFiles != null && mysqlFolderFiles.length > 0) {
				return mysqlFolderFiles[0];
			}
			return null;
		}

	public List<Reports> getReports(ProjectInfo projectInfo) throws PhrescoException {
		try {
			List<Reports> reports = siteReportMap.get(projectInfo.getTechnology().getId());
			if (CollectionUtils.isEmpty(reports)) {
				reports = PhrescoFrameworkFactory.getServiceManager().getReports(projectInfo.getTechnology().getId());
				siteReportMap.put(projectInfo.getTechnology().getId(), reports);
			}
			
			return reports;
		} catch (Exception ex) {
			throw new PhrescoException(ex);
		}
	}
	
	public void updateRptPluginInPOM(ProjectInfo projectInfo, List<Reports> reportsToBeAdded, List<Reports> reportsToBeRemoved) throws PhrescoException {
		try {
			SiteConfigurator configurator = new SiteConfigurator();
			File file = new File(Utility.getProjectHome() + File.separator + projectInfo.getCode() + File.separator + POM_FILE);
			if (CollectionUtils.isNotEmpty(reportsToBeAdded)) {
				configurator.addReportPlugin(reportsToBeAdded, file);
			}
			if (CollectionUtils.isNotEmpty(reportsToBeRemoved)) {
				configurator.removeReportPlugin(reportsToBeRemoved, file);
			}
		} catch (Exception e) {
			throw new PhrescoException();
		}
	}
	
	public List<Server> getServers(String techId) throws PhrescoException {
		try {
			RestClient<Server> serverClient = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_SERVERBYID);
			serverClient.setPath(techId);
			GenericType<List<Server>> genericType = new GenericType<List<Server>>(){};
			List<Server> servers = serverClient.get(genericType);
			return servers;
		} catch (PhrescoException e) {
			e.printStackTrace();
			throw new PhrescoException();
		}
	}
	
	public List<Database> getDatabases(String techId) throws PhrescoException {
		try {
			RestClient<Database> dbClient = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_DATABASESBYID);
			dbClient.setPath(techId);
			GenericType<List<Database>> genericType = new GenericType<List<Database>>(){};
			List<Database> databases = dbClient.get(genericType);
			return databases;
		} catch (PhrescoException e) {
			e.printStackTrace();
			throw new PhrescoException();
		}
	}
	
	public List<WebService> getWebServices(String techId) throws PhrescoException {
		try {
			RestClient<WebService> webServiceClient = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_WEBSERVICESBYID);
			webServiceClient.setPath(techId);
			GenericType<List<WebService>> genericType = new GenericType<List<WebService>>(){};
			List<WebService> webServices = webServiceClient.get(genericType);
			return webServices;
		} catch (PhrescoException e) {
			e.printStackTrace();
			throw new PhrescoException();
		}
	}
	
	public List<Database> getDatabases() throws PhrescoException {
		 try {

			 return PhrescoFrameworkFactory.getServiceManager().getDatabases();
		 } catch (Exception ex) {
			 throw new PhrescoException(ex);
		 }
	 }

	 public List<Server> getServers() throws PhrescoException {
		 try {

			 return PhrescoFrameworkFactory.getServiceManager().getServers();
		 } catch (Exception ex) {
			 throw new PhrescoException(ex);
		 }
	 }
	 
	 public List<ProjectInfo> getPilots(String techId) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getPilots()");
		 
		 try {
			 RestClient<ProjectInfo> pilotClient = getServiceManager().getRestClient(REST_API_COMPONENT + REST_API_PILOTSBYID);
			 pilotClient.setPath(techId);
			 GenericType<List<ProjectInfo>> genericType = new GenericType<List<ProjectInfo>>(){};
			 pilotClient.getById(genericType);
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
		 return null;
	 }
	 
	 public BuildInfo getCIBuildInfo(CIJob job, int buildNumber) throws PhrescoException {
		 S_LOGGER.debug("Entering Method ProjectAdministratorImpl.getCIBuildInfo(CIJob job, int buildNumber)");
		 BuildInfo buildInfo = null;
		 try {
			 CIManager ciManager = PhrescoFrameworkFactory.getCIManager();
			 buildInfo = ciManager.getBuildInfo(job,buildNumber);
		 } catch (Exception e) {
			 throw new PhrescoException(e);
		 }
		 return buildInfo;
	 }
}