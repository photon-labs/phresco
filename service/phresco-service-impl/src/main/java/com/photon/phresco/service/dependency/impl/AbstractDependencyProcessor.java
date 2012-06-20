/*
 * ###
 * Phresco Service Implemenation
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
/*******************************************************************************
 * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.service.dependency.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.Database;
import com.photon.phresco.model.Module;
import com.photon.phresco.model.ModuleGroup;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.model.Technology;
import com.photon.phresco.service.api.DependencyProcessor;
import com.photon.phresco.service.api.RepositoryManager;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.util.TechnologyTypes;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;
import com.photon.phresco.util.Constants;
/**
 * Abstract dependency processor has methods to get the binaries from
 * dependencies.
 * 
 * @author arunachalam_l
 * 
 */
public abstract class AbstractDependencyProcessor implements DependencyProcessor {

	private static final Logger S_LOGGER = Logger.getLogger(AbstractDependencyProcessor.class);
	private static Boolean isDebugEnabled = S_LOGGER.isDebugEnabled();
	private static Map<String, String> sqlFolderPathMap = new HashMap<String, String>();

	private RepositoryManager repoManager = null;

	static {
		initDbPathMap();
	}

	/**
	 * @param dependencyManager
	 */
	public AbstractDependencyProcessor(RepositoryManager repoManager) {
		this.repoManager = repoManager;
	}

	/**
	 * @param path
	 * @param dependencyManager
	 * @param modules
	 * @throws PhrescoException
	 */
	protected void extractModules(File path, List<ModuleGroup> modules) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  AbstractDependencyProcessor.extractModules(File path, List<TupleBean> modules)");
		}
		if (CollectionUtils.isEmpty(modules)) {
			return;
		}

		for (ModuleGroup tupleBean : modules) {
			extractModule(path, tupleBean);
		}
	}

	/**
	 * 
	 * @param path
	 * @param module
	 * @throws PhrescoException
	 */
	protected void extractModule(File path, ModuleGroup module) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  AbstractDependencyProcessor.extractModules(File path, List<TupleBean> modules)");
			S_LOGGER.debug("extractModule() FilePath=" + path.getPath());
		}
		// Module module = repoManager.getModule(tupleBean.getId());

		// TODO:Handle all versions

		Module moduleVersion = module.getVersions().get(0);
		String contentURL = moduleVersion.getUrl();
		if (!StringUtils.isEmpty(contentURL)) {
			DependencyUtils.extractFiles(contentURL, path);
		}
	}

	protected RepositoryManager getRepositoryManager() {
		return repoManager;
	}

	private static void initDbPathMap() {
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

	@Override
	public void process(ProjectInfo info, File path) throws PhrescoException {
		if (isDebugEnabled) {
			S_LOGGER.debug("Entering Method  AbstractDependencyProcessor.process(ProjectInfo info, File path)");
			S_LOGGER.debug("process() FilePath=" + path.getPath());
			S_LOGGER.debug("process() ProjectCode=" + info.getCode());
		}

		File modulesPath = path;
		String modulePathKey = getModulePathKey();
		if (!StringUtils.isEmpty(modulePathKey)) {
			String modulesPathString = DependencyProcessorMessages.getString(modulePathKey);
			modulesPath = new File(path, modulesPathString);
		}
		Technology technology = info.getTechnology();
		extractModules(modulesPath, technology.getModules());
		// pilot projects
		if (StringUtils.isNotBlank(info.getPilotProjectName())) {
			List<ProjectInfo> pilotProjects = getRepositoryManager().getPilotProjects(technology.getId());
			if (CollectionUtils.isEmpty(pilotProjects)) {
				return;
			}

			for (ProjectInfo projectInfo : pilotProjects) {
				// extractModules(modulesPath,
				// projectInfo.getTechnology().getModules());
				// extractLibraries(modulesPath,
				// projectInfo.getTechnology().getLibraries());
				String urls[] = projectInfo.getPilotProjectUrls();
				if (urls != null) {
					for (String url : urls) {
						DependencyUtils.extractFiles(url, path);
					}
				}
			}
		}
	}

	protected abstract String getModulePathKey();

	protected void updatePOMWithModules(File path, List<com.photon.phresco.model.ModuleGroup> modules)
	throws PhrescoException, JAXBException, PhrescoPomException {
		try {
			if (isDebugEnabled) {
				S_LOGGER.debug("extractModules() path=" + path.getPath());
			}
			File pomFile = new File(path, "pom.xml");
			if (pomFile.exists()) {
				PomProcessor processor = new PomProcessor(pomFile);
				for (com.photon.phresco.model.ModuleGroup module : modules) {
					if (module != null) {
						processor.addDependency(module.getGroupId(), module.getArtifactId(), module.getVersions()
								.get(0).getVersion());
					}
				}
				processor.save();
			}
		} catch (IOException e) {
			throw new PhrescoException(e);
		}
	}
	
	protected void updatePOMWithJsLibs(File path, List<com.photon.phresco.model.ModuleGroup> jsLibs) throws PhrescoException{
        try {
            if (isDebugEnabled) {
                S_LOGGER.debug("extractModules() path=" + path.getPath());
            }
            File pomFile = new File(path, "pom.xml");
            if (pomFile.exists()) {
                PomProcessor processor = new PomProcessor(pomFile);
                for (com.photon.phresco.model.ModuleGroup jsLibrary : jsLibs) {
                    if (jsLibrary != null) {
                        String groupId = "jslibraries.files";
                        String artifactId = "jslib_" + jsLibrary.getName().toLowerCase();
                        processor.addDependency(groupId, artifactId, jsLibrary.getVersions()
                                .get(0).getVersion(), "", "js");
                    }
                }
                processor.save();
            }
        } catch (IOException e) {
            throw new PhrescoException(e);
        } catch (JAXBException e) {
            throw new PhrescoException(e);
        } catch (PhrescoPomException e) {
            throw new PhrescoException(e);
        }
    }
	
	protected void createSqlFolder(ProjectInfo info, File path) throws PhrescoException {
		String databaseType = "";
		try {
			List<Database> databaseList = info.getTechnology().getDatabases();
			String techId = info.getTechnology().getId();
			if (databaseList == null || databaseList.size() == 0) {
				return;
			}
			File mysqlFolder = new File(path, sqlFolderPathMap.get(techId) + Constants.DB_MYSQL);
			File mysqlVersionFolder = getMysqlVersionFolder(mysqlFolder);
			for (Database db : databaseList) {
				databaseType = db.getName().toLowerCase();
				List<String> versions = db.getVersions();
				for (String version : versions) {
					String sqlPath = databaseType + File.separator + version.trim();
					File sqlFolder = new File(path, sqlFolderPathMap.get(techId) + sqlPath);
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
}
