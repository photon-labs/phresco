/*
 * ###
 * Framework Web Archive
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
package com.photon.phresco.framework.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photon.phresco.commons.FrameworkConstants;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.framework.FrameworkConfiguration;
import com.photon.phresco.framework.PhrescoFrameworkFactory;
import com.photon.phresco.framework.actions.applications.Applications;
import com.photon.phresco.framework.api.ProjectAdministrator;
import com.photon.phresco.framework.commons.filter.FileListFilter;
import com.photon.phresco.framework.impl.ClientHelper;
import com.photon.phresco.model.ApplicationType;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Server;
import com.photon.phresco.model.WebService;
import com.photon.phresco.util.Utility;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.site.Reports;
import com.phresco.pom.util.PomProcessor;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class ApplicationsUtil implements FrameworkConstants {

	private static final Logger S_LOGGER = Logger.getLogger(Applications.class);
    private static Boolean debugEnabled  = S_LOGGER.isDebugEnabled();
    private static Map<String, List<ProjectInfo>> mapPilotProjectInfos = new HashMap<String, List<ProjectInfo>>(15);
	private static Map<String, Collection<String>> mapPilotNames = new HashMap<String, Collection<String>>(15);
	private static Map<String, Map<String, String>> mapPilotModuleIds = new HashMap<String, Map<String, String>>(15);
	private static Map<String, Map<String, String>> mapPilotJsLibs = new HashMap<String, Map<String, String>>(15);
	
    public static ModuleGroup getSelectedTuple(List<ModuleGroup> moduleGroups, String moduleId, String selectedVersion) {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationsUtil.getSelectedTuple(List<TupleBean> tupleBeans, String tupleBeanId)");
			S_LOGGER.debug("getSelectedTuple() TupleBeanId = " + moduleId);
		}
    	try {
            if (CollectionUtils.isEmpty(moduleGroups)) {
                return null;
            }

            for (ModuleGroup moduleGroup : moduleGroups) {
                if (moduleGroup.getId().equals(moduleId)) {
                	Module selectedModuleVer = moduleGroup.getVersion(selectedVersion);
                	if (selectedModuleVer != null) {
	                	List<Module> moduleVersions = new ArrayList<Module>(15);
	                	moduleVersions.add(selectedModuleVer);
	                	moduleGroup.setVersions(moduleVersions);
                	}
                	return moduleGroup;
                }
            }
    	} catch (Exception e) {
            if (debugEnabled) {
                S_LOGGER.error("Entered into catch block of  ApplicationsUtil.getSelectedTuple()" + e);
            }
            new LogErrorReport(e, "Getting Core and Custom Modules");
        }
        return null;
    }
    
    public static List<ModuleGroup> getSelectedTuples(HttpServletRequest request, List<ModuleGroup> moduleGroups, String moduleIds[]) {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationsUtil.getSelectedTuples(List<TupleBean> tupleBeans, String typleBeanIds[])");
		}
    	try {
            if (CollectionUtils.isEmpty(moduleGroups) || moduleIds == null) {
                return Collections.emptyList();
            }
            List<ModuleGroup> selectedBeans = new ArrayList<ModuleGroup>(moduleGroups.size());
            for (String moduleId : moduleIds) {
            	String selectedVersion = request.getParameter(moduleId);
            	ModuleGroup moduleGroup = getSelectedTuple(moduleGroups, moduleId, selectedVersion);
            	if (moduleGroup != null) {
            		selectedBeans.add(moduleGroup);
            	}
            }
            return selectedBeans;
    	}  catch (Exception e) {
            if (debugEnabled) {
                S_LOGGER.error("Entered into catch block of  ApplicationsUtil.getSelectedTuples()" + e);
            }
            new LogErrorReport(e, "Getting Core and Custom Modules");
        }
        return null;
    }
    
    private static List<ProjectInfo> getPilots(String technologyId) {
    	if (debugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationsUtil.getPilots(String technologyId)");
			S_LOGGER.debug("getPilots() TechnologyId = "+technologyId);
		}
        try {
            FrameworkConfiguration configuration = null;
            configuration = PhrescoFrameworkFactory.getFrameworkConfig();
            Client client = ClientHelper.createClient();

            WebResource resource = client.resource(configuration.getServerPath()
                    + FrameworkConstants.REST_PILOT_PATH);
            resource.accept(MediaType.APPLICATION_OCTET_STREAM);

            GenericType<List<ProjectInfo>> genericType = new GenericType<List<ProjectInfo>>() {};

            List<ProjectInfo> pilots = resource.type(MediaType.APPLICATION_JSON).
                    post(genericType, technologyId);

            return pilots;

        } catch (ClientHandlerException e) {
            if (debugEnabled) {
                S_LOGGER.error("Entered into catch block of ApplicationsUtil.getPilots()" + e);
            }
            new LogErrorReport(e, "Getting pilot projects");
        } catch (Exception e) {
            if (debugEnabled) {
                S_LOGGER.error("Entered into catch block of ApplicationsUtil.getPilots()" + e);
            }
            new LogErrorReport(e, "Getting pilot projects");
        }
        return null;
    }
    
    public static Collection<String> getPilotNames(String technologyId) {
    	List<ProjectInfo> pilots = mapPilotProjectInfos.get(technologyId);
    	Collection<String> pilotNames = mapPilotNames.get(technologyId);
    	if (pilotNames != null) {
    		return pilotNames;
    	}
    	if (pilots == null) {
    		pilots = getPilots(technologyId);
    		mapPilotProjectInfos.put(technologyId, pilots);
    	}
    	
    	pilotNames = new ArrayList<String>(15);
    	if (CollectionUtils.isNotEmpty(pilots)) {
    		for (ProjectInfo projectInfo : pilots) {
    			pilotNames.add(projectInfo.getName());
    		}
    		mapPilotNames.put(technologyId, pilotNames);
    	}
    	return pilotNames;
    }
    
    public static Map<String, String> getPilotModuleIds(String technologyId) {
    	Map<String, String> mapPilotModules = mapPilotModuleIds.get(technologyId);
    	if (mapPilotModules != null) {
    		return mapPilotModules;
    	}
    	List<ProjectInfo> pilots = mapPilotProjectInfos.get(technologyId);
    	if (pilots == null) {
    		pilots = getPilots(technologyId);
    	}
    	mapPilotModules = new HashMap<String, String>(15);
    	if (CollectionUtils.isNotEmpty(pilots)) {
    		for (ProjectInfo projectInfo : pilots) {
    			List<ModuleGroup> pilotModules = projectInfo.getTechnology().getModules();
    			if (CollectionUtils.isNotEmpty(pilotModules)) {
    				for (ModuleGroup pilotModule : pilotModules) {
    					if (CollectionUtils.isNotEmpty(pilotModule.getVersions())) {
    						mapPilotModules.put(pilotModule.getId(), pilotModule.getVersions().get(0).getVersion());
    					}
    				}
    			}
    		}
    	}
    	mapPilotModuleIds.put(technologyId, mapPilotModules);
    	return mapPilotModules;
    }
    
    public static Map<String, String> getPilotJsLibIds(String technologyId) {
    	Map<String, String> mapPilotModules = mapPilotJsLibs.get(technologyId);
    	if (mapPilotModules != null) {
    		return mapPilotModules;
    	}
    	List<ProjectInfo> pilots = mapPilotProjectInfos.get(technologyId);
    	if (pilots == null) {
    		pilots = getPilots(technologyId);
    	}
    	mapPilotModules = new HashMap<String, String>(15);
    	if (CollectionUtils.isNotEmpty(pilots)) {
    		for (ProjectInfo projectInfo : pilots) {
    			List<ModuleGroup> pilotModules = projectInfo.getTechnology().getJsLibraries();
    			if (CollectionUtils.isNotEmpty(pilotModules)) {
    				for (ModuleGroup pilotModule : pilotModules) {
    					if (CollectionUtils.isNotEmpty(pilotModule.getVersions())) {
    						mapPilotModules.put(pilotModule.getId(), pilotModule.getVersions().get(0).getVersion());
    					}
    				}
    			}
    		}
    	}
    	mapPilotJsLibs.put(technologyId, mapPilotModules);
    	return mapPilotModules;
    }
    
    public static ProjectInfo getPilotProjectInfo(String technologyId) {
    	List<ProjectInfo> pilots = mapPilotProjectInfos.get(technologyId);
    	ProjectInfo pilotProjectInfo = null;
    	if (pilots != null) {
    		for (ProjectInfo projectInfo : pilots) {
    			pilotProjectInfo = projectInfo;
			}
    		return pilotProjectInfo;
    	}
    	pilots = getPilots(technologyId);
    	mapPilotProjectInfos.put(technologyId, pilots);
    	for (ProjectInfo projectInfo : pilots) {
    		pilotProjectInfo = projectInfo;
    	}
    	return pilotProjectInfo;
    }
    
    public static ApplicationType getApplicationType(HttpServletRequest request, String appTypeName) throws PhrescoException {
    	ApplicationType applicationtype = null;
    	try{
            String appType = request.getParameter(REQ_APPLICATION_TYPE);
        	if (debugEnabled) {
    			S_LOGGER.debug("Selected application type" + appType);
    		}
            ProjectAdministrator administrator = PhrescoFrameworkFactory.getProjectAdministrator();
            applicationtype = administrator.getApplicationType(appType);
    	} catch(Exception e) {
            if (debugEnabled) {
                S_LOGGER.error("Entered into catch block of  Applications.getApplicationType()" + FrameworkUtil.getStackTraceAsString(e));
            }
            new LogErrorReport(e, "Getting application types");
    	}
    	return applicationtype;
    }
    
    private static Collection<String> dependentVersions = new ArrayList<String>(1);
    public static Collection<String> getDependentVersions() {
    	return dependentVersions;
    }
    
    public static Collection<String> getIds(List<ModuleGroup> tupleBeans) {
        if (S_LOGGER.isDebugEnabled()) S_LOGGER.debug("Entered getIds");
        try {
            Collection<String> ids = new ArrayList<String>(10);
            if(tupleBeans != null) {
                for (ModuleGroup tupleBean : tupleBeans) {
                    ids.add(tupleBean.getId());
                    dependentVersions.add(tupleBean.getVersions().get(0).getVersion());
                }
            }

            return ids;
        } catch(Exception e) {
        	if (debugEnabled) {
                S_LOGGER.error("Entered into catch block of Applications.getJsLibrariesIds()"+ FrameworkUtil.getStackTraceAsString(e));
    		}
        	new LogErrorReport(e, "Getting Ids");
    	}
        return null;
    }
    
    public static Map<String, String> getIdAndVersionAsMap(HttpServletRequest request, String[] ids) {
    	Map<String, String> map = new HashMap<String, String>(15);
    	for (String id : ids) {
    		map.put(id, request.getParameter(id));
    	}
    	return map;
    }
    
    public static Map<String, String> stringToMap(String str) {
    	str = str.substring(1).substring(0, str.length() - 2).trim();
		Map<String, String> map = new HashMap<String, String>(5);
		String[] split = str.split(", ");
		for (String item : split) {
			String[] keyValue = item.split("=");
			map.put(keyValue[0], keyValue[1]);
		}
		return map;
    }
    
    public static Map<String, String> getMapFromModuleGroups(List<ModuleGroup> moduleGroups) {
    	Map<String, String> map = new HashMap<String, String>(15);
    	for (ModuleGroup moduleGroup : moduleGroups) {
			map.put(moduleGroup.getId(), moduleGroup.getVersions().get(0).getVersion());
		}
    	return map;
    }
    
    public static String getCsvFromStringArray(String[] selectedIds) {
    	if (selectedIds == null) {
    		return null;
    	}
    	String csv = "";
    	for (String id : selectedIds) {
			csv = csv + id + ",";
		}
    	csv = csv.substring(0, csv.length() - 1); 
    	return csv;
    }
    
	public static Document getDocument(File file) throws ParserConfigurationException, SAXException, IOException {
		InputStream fis = null;
        DocumentBuilder builder = null;
        try {
            fis = new FileInputStream(file);
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(fis);
            return doc;
            
        } finally {
        	if(fis != null) {
        		fis.close();
            }
        }
	}
	
	public static List<Integer> getArrayListFromStrArr(String[] strArr) {
		List<Integer> list = new ArrayList();
		if (strArr != null && strArr.length > 0) {
			for (int i = 0; i < strArr.length; i++) {
				list.add(Integer.parseInt(strArr[i]));
			}
		}
		return list;
	}

	public static List<Server> getSelectedServers(List<Server> servers, List<String> selectedServer) {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationsUtil.getSelectedServers(List<Server> servers, List<Integer> selectedServerIds[])");
		}
		try {
			if (CollectionUtils.isEmpty(servers) || selectedServer == null) {
				return Collections.emptyList();
			}
			List<Server> selectedServers = new ArrayList<Server>(servers.size());
			for (String tempServer : selectedServer) {
				String[] split = tempServer.split("#VSEP#");
				List<String> versions = new ArrayList<String>(Arrays.asList(split[1].split(",")));
				String name = split[0].trim();
				for (Server server : servers) {
					if(name.equals(server.getName())) {
						Server newServer = new Server();
						newServer.setId(server.getId());
						newServer.setName(server.getName());
						newServer.setDescription(server.getDescription());
						newServer.setVersions(versions);
						selectedServers.add(newServer);
					}
				}
			}
			return selectedServers;
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of ApplicationsUtil.getSelectedServers()" + e);
			}
			new LogErrorReport(e, "Getting servers");
		}
		return null;
	}

	public static List<Database> getSelectedDatabases(List<Database> databases, List<String> selectedDatabase) {
		if (debugEnabled) {
		S_LOGGER.debug("Entering Method ApplicationsUtil.getSelectedDatabases(List<Database> databases, List<Integer> selectedDatabaseIds)");
		}
		try {
			if (CollectionUtils.isEmpty(databases) || selectedDatabase == null) {
				return Collections.emptyList();
			}
			List<Database> selectedDatabases = new ArrayList<Database>(databases.size());
			for (String tempDatabase : selectedDatabase) {
				String[] split = tempDatabase.split("#VSEP#");
				List<String> versions = new ArrayList<String>(Arrays.asList(split[1].split(",")));
				String name = split[0].trim();
				for (Database database : databases) {
					if(name.equals(database.getName())) {
						Database newDatabase = new Database();
						newDatabase.setId(database.getId());
						newDatabase.setName(database.getName());
						newDatabase.setDescription(database.getDescription());
						newDatabase.setVersions(versions);
						selectedDatabases.add(newDatabase);
					}
				}
			}
			return selectedDatabases;
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of ApplicationsUtil.getSelectedDatabases()" + e);
			}
			new LogErrorReport(e, "Getting databases");
		}
		return null;
	}
	
	public static List<WebService> getSelectedWebservices(List<WebService> webservices, List<Integer> selectedWebserviceIds) {
		if (debugEnabled) {
			S_LOGGER.debug("Entering Method ApplicationsUtil.getSelectedWebservices(List<WebService> webservices, List<Integer> selectedWebserviceIds)");
		}
		try {
			if (CollectionUtils.isEmpty(webservices) || selectedWebserviceIds == null) {
				return Collections.emptyList();
			}
			List<WebService> selectedWebservices = new ArrayList<WebService>(webservices.size());
			for (WebService webservice : webservices) {
				if (selectedWebserviceIds.contains(webservice.getId())) {
					selectedWebservices.add(webservice);
				}
			}
			return selectedWebservices;
		} catch (Exception e) {
			if (debugEnabled) {
				S_LOGGER.error("Entered into catch block of  ApplicationsUtil.getSelectedWebservices()" + e);
			}
			new LogErrorReport(e, "Getting webservices");
		}
		return null;
	}
	
	public static String getConfigInfoPath(String projectCode) {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append(projectCode);
		builder.append(File.separator);
		builder.append(".phresco");
		builder.append(File.separator);
		builder.append("config.xml");
		return builder.toString();
	}
	
	public static String getSettingsInfoPath() {
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
		builder.append("settings.xml");
		return builder.toString();
	}
	
	public static List<PBXNativeTarget> getXcodeConfiguration(String projectCode) throws PhrescoException, JAXBException, IOException, PhrescoPomException {
		S_LOGGER.debug("Iphone technology target retrivel initiated");
		StringBuilder builder = new StringBuilder(Utility.getProjectHome());
        builder.append(projectCode);
        builder.append(File.separatorChar);
        builder.append(POM_XML);
		File pomPath = new File(builder.toString());
		PomProcessor pomProcessor = new PomProcessor(pomPath);
		StringBuilder projPath = new StringBuilder(Utility.getProjectHome());
        projPath.append(projectCode);
        projPath.append(pomProcessor.getSourceDirectory());
        File file = new File(projPath.toString());
        FilenameFilter filter = new FileListFilter("", IPHONE_XCODE_PROJ_EXTN);
        File[] listFiles = file.listFiles(filter);
        projPath.append(File.separator);
        // Get firest xcode proj file name
        projPath.append(listFiles[0].getName());
        S_LOGGER.debug("Iphone technology listFile name" + listFiles[0].getName());
        String pbxprojLocation = projPath.toString();
        
		// plutil location in mac
		File plutilCommandLine = new File("/usr/bin/plutil");
		List<PBXNativeTarget> targets = null;
		S_LOGGER.debug("Before entering plutilCommandLine ");
		if (!plutilCommandLine.exists()) {
			S_LOGGER.debug("Invalid path for plutil");
            throw new PhrescoException("Invalid path for plutil");
        }
		File xcodeprojJson = new File(pbxprojLocation, "xcodeInfo.json");
		S_LOGGER.debug("Before plutil try!!!");
        try {
        	String[] commands = {"plutil", "-convert", "json", "-o", xcodeprojJson.getAbsolutePath(), pbxprojLocation + "/project.pbxproj"};
            ProcessBuilder probuilder = new ProcessBuilder(commands);
            probuilder.directory(new File(pbxprojLocation));
            probuilder.start();
        } catch (Exception e) {
        	S_LOGGER.error("Error While Executing" + e);
            throw new PhrescoException("Error while executing ");
        }
        S_LOGGER.error("Before While loop");
        while(!xcodeprojJson.exists()) {
        	
        }
        S_LOGGER.error("After While loop Completed");
        if (xcodeprojJson.exists()) {
        	S_LOGGER.error("File exists");
            XcodeprojParser parser = new XcodeprojParser(xcodeprojJson);
            try {
                PBXProject project = parser.parseXcodeFile();
                if (project != null) {
                	targets = project.getTargets();
                }
            } catch (Exception e) {
                throw new PhrescoException(e);
            }
        }
        S_LOGGER.error("File reading completed");
        xcodeprojJson.delete();
        S_LOGGER.debug("Going to return from applications util");
		return targets;
    }
	
	public static void storeAsJSON(File file, List<Reports> reports) throws PhrescoException {
		try {
			Gson gson = new Gson();
			String json = gson.toJson(reports);
            FileOutputStream fos = new FileOutputStream(file, false);
            fos.write(json.getBytes());
            fos.close();
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
	}
	
	public static List<Reports> readReportConfig(File file) throws PhrescoException {
		try {
			BufferedReader reader = null;
			if (file.exists()) {
				Gson gson = new Gson();
				reader = new BufferedReader(new FileReader(file));
				Type type = new TypeToken<List<Reports>>(){}.getType();
				List<Reports> reports = gson.fromJson(reader, type);
				reader.close();
				
				return reports;
			}
		} catch (Exception e) {
			throw new PhrescoException(e);
		}
		
		return null;
	}
}